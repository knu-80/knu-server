package kr.co.knuserver.application.booth;

import java.time.Duration;
import java.util.Set;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final StringRedisTemplate redisTemplate;

    public long like(Long boothId, String deviceId, String clientIp) {
        checkRateLimit(clientIp, deviceId, boothId);

        Double score = redisTemplate.opsForZSet().incrementScore(RANKING_KEY, String.valueOf(boothId), 1);
        return score == null ? 0 : score.longValue();
    }

    public long getLikeCount(Long boothId) {
        Double score = redisTemplate.opsForZSet().score(RANKING_KEY, String.valueOf(boothId));
        return score == null ? 0 : score.longValue();
    }

    public Set<ZSetOperations.TypedTuple<String>> getRanking() {
        return redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, -1);
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
