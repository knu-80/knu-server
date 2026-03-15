package kr.co.knuserver.application.booth;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.ZSetOperations;

public class BoothRanking {

    private final Map<Long, Long> likeCountMap;

    public BoothRanking(Set<ZSetOperations.TypedTuple<String>> tuples) {
        this.likeCountMap = tuples.stream()
            .filter(t -> t.getValue() != null)
            .collect(Collectors.toMap(
                t -> Long.parseLong(t.getValue()),
                t -> t.getScore() == null ? 0L : t.getScore().longValue()
            ));
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
