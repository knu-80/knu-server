package kr.co.knuserver.application.pubWaiting;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class WaitingQueue {

    private final ConcurrentSkipListSet<WaitingNode> orderedSet = new ConcurrentSkipListSet<>();
    private final ConcurrentHashMap<Long, WaitingNode> indexMap = new ConcurrentHashMap<>();

    private final AtomicLong sequence = new AtomicLong();

    public void register(Long pubWaitingId) {
        WaitingNode node = new WaitingNode(pubWaitingId, sequence.getAndIncrement());

        if (indexMap.putIfAbsent(pubWaitingId, node) == null) {
            orderedSet.add(node);
        }
    }

    public boolean cancel(Long pubWaitingId) {
        WaitingNode node = indexMap.remove(pubWaitingId);
        if (node == null) return false;
        return orderedSet.remove(node);
    }

    public int size() {
        return orderedSet.size();
    }

    public List<Long> getWaitingIds() {
        return orderedSet.stream()
                .map(WaitingNode::getPubWaitingId)
                .collect(Collectors.toList());
    }
}


@Getter
@RequiredArgsConstructor
class WaitingNode implements Comparable<WaitingNode> {

    private final Long pubWaitingId;
    private final long sequence;

    @Override
    public int compareTo(WaitingNode o) {
        return Long.compare(this.sequence, o.sequence);
    }
}