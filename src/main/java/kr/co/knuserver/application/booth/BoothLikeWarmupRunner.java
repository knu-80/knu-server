package kr.co.knuserver.application.booth;

import jakarta.annotation.PostConstruct;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoothLikeWarmupRunner {

    private static final String RANKING_KEY = "like:ranking";

    private final BoothRepository boothRepository;
    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public void warmup() {
        try {
            log.debug("[LikeWarmup] 부스 좋아요 캐시 초기화 시작");

            boothRepository.findByIsActiveTrue()
                .forEach(booth -> redisTemplate.opsForZSet()
                    .addIfAbsent(RANKING_KEY, String.valueOf(booth.getId()), 0));

            log.debug("[LikeWarmup] 부스 좋아요 캐시 초기화 완료");
        } catch (Exception e) {
            log.error("[LikeWarmup] 캐시 초기화 실패 - Redis 연결 확인 필요", e);
        }
    }
}
