package kr.co.knuserver.application.pubWaiting;

import java.util.List;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisWaitingService implements PubWaitingServiceInterface {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> registerScript;
    private final DefaultRedisScript<Long> cancelScript;

    private static final String QUEUE_PREFIX = "waiting:";
    private static final String MEMBER_PREFIX = "waiting:member:";

    private String getKey(Long boothId) { return QUEUE_PREFIX + boothId; }
    private String getMemberKey(Long memberId) { return MEMBER_PREFIX + memberId; }

    @Override
    public void register(Long boothId, Long memberId, Long pubWaitingId) {
        Long result = redisTemplate.execute(
                registerScript,
                List.of(getKey(boothId), getMemberKey(memberId)),
                boothId.toString(),
                pubWaitingId.toString(),
                String.valueOf(System.currentTimeMillis())
        );

        if (result == null || result == 0L) {
            throw new BusinessException(BusinessErrorCode.ALREADY_IN_WAITING);
        }
    }

    @Override
    public boolean cancel(Long boothId, Long memberId, Long pubWaitingId) {
        Long result = redisTemplate.execute(
                cancelScript,
                List.of(getKey(boothId), getMemberKey(memberId)),
                pubWaitingId.toString()
        );
        return result != null && result == 1L;
    }

    @Override
    public int getWaitingSize(Long pubBoothId) {
        Long size = redisTemplate.opsForZSet().size(getKey(pubBoothId));
        return size == null ? 0 : size.intValue();
    }

    @Override
    public List<Long> getAllWaitingIds(Long pubBoothId) {
        Set<String> ids = redisTemplate.opsForZSet().range(getKey(pubBoothId), 0, -1);

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return ids.stream()
                .map(Long::valueOf)
                .toList();
    }
}