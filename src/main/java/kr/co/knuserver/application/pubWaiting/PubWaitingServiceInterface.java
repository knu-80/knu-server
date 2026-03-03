package kr.co.knuserver.application.pubWaiting;

import java.util.List;

public interface PubWaitingServiceInterface {
    void register(Long pubBoothId, Long memberId, Long pubWaitingId);
    boolean cancel(Long boothId, Long memberId, Long pubWaitingId);
    int getWaitingSize(Long pubBoothId);
    List<Long> getAllWaitingIds(Long pubBoothId);
}
