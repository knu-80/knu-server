package kr.co.knuserver.domain.pubWaiting.repository;

import java.util.List;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubWaitingRepository extends JpaRepository<PubWaiting, Long> {
    PubWaiting findByMemberIdAndStatus(Long memberId, PubWaitingStatus status);

    List<PubWaiting> findByPubBoothIdAndStatus(Long pubBoothId, PubWaitingStatus pubWaitingStatus);
}
