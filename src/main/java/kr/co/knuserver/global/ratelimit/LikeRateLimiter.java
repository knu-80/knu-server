package kr.co.knuserver.global.ratelimit;

import java.time.Duration;
import kr.co.knuserver.global.config.LikeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeRateLimiter {

    private static final String KEY = "like:rate:%s:%s";

    private final StringRedisTemplate redisTemplate;
    private final LikeProperties likeProperties;

    public boolean isAllowed(String clientIp, String deviceId) {
        String key = KEY.formatted(clientIp, deviceId);
        long maxLikes = likeProperties.rateLimit().maxLikes();
        Duration window = Duration.ofSeconds(likeProperties.rateLimit().ttlSeconds());

        String countStr = redisTemplate.opsForValue().get(key);
        long currentCount = countStr == null ? 0 : Long.parseLong(countStr);

        if (currentCount >= maxLikes) {
            return false;
        }

        Long newCount = redisTemplate.opsForValue().increment(key);
        if (newCount == 1) {
            redisTemplate.expire(key, window);
        }

        return true;
    }
}
