package kr.co.knuserver.application.pubWaiting.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kr.co.knuserver.application.pubWaiting.PubWaitingServiceInterface;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class ConcurrentMapWaitingService implements PubWaitingServiceInterface {

    private final ConcurrentHashMap<Long, WaitingQueue> waitingMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Long> memberBoothMap = new ConcurrentHashMap<>(); // memberId: pubBoothId

    private WaitingQueue getQueue(Long pubBoothId) {
        return waitingMap.computeIfAbsent(pubBoothId, k -> new WaitingQueue());
    }

    @Override
    public void register(Long pubBoothId, Long memberId, Long pubWaitingId) {
        if (memberBoothMap.putIfAbsent(memberId, pubBoothId) != null) {
            throw new BusinessException(BusinessErrorCode.ALREADY_IN_WAITING);
        }
        getQueue(pubBoothId).register(pubWaitingId);
    }

    @Override
    public boolean cancel(Long boothId, Long memberId, Long pubWaitingId) {
        WaitingQueue queue = waitingMap.get(boothId);
        if (queue == null) return false;

        boolean removed = queue.cancel(pubWaitingId);
        if (removed) {
            memberBoothMap.remove(memberId);
        }

        return removed;
    }

    @Override
    public int getWaitingSize(Long boothId) {
        WaitingQueue queue = waitingMap.get(boothId);
        return queue == null ? 0 : queue.size();
    }

    @Override
    public List<Long> getAllWaitingIds(Long boothId) {
        WaitingQueue queue = waitingMap.get(boothId);
        if (queue == null) return Collections.emptyList();
        return queue.getWaitingIds();
    }
}