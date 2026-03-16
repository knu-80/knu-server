package kr.co.knuserver.application.booth;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Set;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoothLikeService {

    private static final String RATE_LIMIT_KEY = "like:rate:%s:%s:%d";
    private static final String RANKING_KEY = "like:ranking";

    @Value("${like.rate-limit.ttl-seconds}")
    private long rateLimitTtlSeconds;

    @Value("${like.double-event.enabled:false}")
    private boolean doubleEventEnabled;

    @Value("${like.double-event.start-time:13:00}")
    private String doubleEventStart;

    @Value("${like.double-event.end-time:15:00}")
    private String doubleEventEnd;

    private final StringRedisTemplate redisTemplate;
    private final BoothRepository boothRepository;

    public long like(Long boothId, String deviceId, String clientIp) {
        Booth booth = boothRepository.findById(boothId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));
        if (booth.getDivision() == BoothDivision.EXTERNAL_SUPPORT) {
            throw new BusinessException(BusinessErrorCode.LIKE_NOT_ALLOWED);
        }

        try {
            checkRateLimit(clientIp, deviceId, boothId);
            int multiplier = getLikeMultiplier();
            Double score = redisTemplate.opsForZSet().incrementScore(RANKING_KEY, String.valueOf(boothId), multiplier);
            log.debug("[Like] boothId={} multiplier={} score={}", boothId, multiplier, score);
            return score == null ? 0 : score.longValue();
        } catch (BusinessException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("[Like] Redis 연결 실패 boothId={}", boothId, e);
            throw new BusinessException(BusinessErrorCode.REDIS_UNAVAILABLE);
        }
    }

    public long getLikeCount(Long boothId) {
        try {
            Double score = redisTemplate.opsForZSet().score(RANKING_KEY, String.valueOf(boothId));
            if (score == null) {
                return 0L;
            }
            return score.longValue();
        } catch (DataAccessException e) {
            log.warn("[LikeCount] Redis 조회 실패 boothId={}, 0 반환", boothId, e);
            return 0L;
        }
    }

    public Set<ZSetOperations.TypedTuple<String>> getRanking() {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, -1);
        } catch (DataAccessException e) {
            log.warn("[Ranking] Redis 조회 실패, 빈 셋 반환", e);
            return Collections.emptySet();
        }
    }

    public Set<ZSetOperations.TypedTuple<String>> getTopRanking(int limit) {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, limit - 1);
        } catch (DataAccessException e) {
            log.warn("[Ranking] Redis 조회 실패, 빈 셋 반환", e);
            return Collections.emptySet();
        }
    }

    private int getLikeMultiplier() {
        if (!doubleEventEnabled) {
            return 1;
        }
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Seoul"));
        LocalTime start = LocalTime.parse(doubleEventStart);
        LocalTime end = LocalTime.parse(doubleEventEnd);
        boolean isEventTime = !now.isBefore(start) && now.isBefore(end);
        if (isEventTime) {
            log.debug("[DoubleEvent] 2배 이벤트 적용 중 now={}", now);
        }
        return isEventTime ? 2 : 1;
    }

    private void checkRateLimit(String clientIp, String deviceId, Long boothId) {
        String rateLimitKey = RATE_LIMIT_KEY.formatted(clientIp, deviceId, boothId);
        log.debug("[RateLimit] key={}", rateLimitKey);
        boolean allowed = Boolean.TRUE.equals(
            redisTemplate.opsForValue().setIfAbsent(rateLimitKey, "1", Duration.ofSeconds(rateLimitTtlSeconds))
        );
        log.debug("[RateLimit] allowed={}", allowed);
        if (!allowed) {
            throw new BusinessException(BusinessErrorCode.TOO_MANY_REQUESTS);
        }
    }
}
