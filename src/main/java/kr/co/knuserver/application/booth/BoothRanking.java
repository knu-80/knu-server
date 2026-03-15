package kr.co.knuserver.application.booth;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;

@Slf4j
public class BoothRanking {

    private final Map<Long, Long> likeCountMap;

    public BoothRanking(Set<ZSetOperations.TypedTuple<String>> tuples) {
        this.likeCountMap = tuples.stream()
            .filter(t -> t.getValue() != null)
            .flatMap(t -> parseBoothId(t.getValue())
                .map(id -> Map.entry(id, t.getScore() == null ? 0L : t.getScore().longValue()))
                .stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Optional<Long> parseBoothId(String value) {
        try {
            return Optional.of(Long.parseLong(value));
        } catch (NumberFormatException e) {
            log.error("[BoothRanking] Redis member 파싱 실패 value={}", value, e);
            return Optional.empty();
        }
    }

    public boolean isEmpty() {
        return likeCountMap.isEmpty();
    }

    public Set<Long> boothIds() {
        return likeCountMap.keySet();
    }

    public long getLikeCount(Long boothId) {
        return likeCountMap.getOrDefault(boothId, 0L);
    }
}
