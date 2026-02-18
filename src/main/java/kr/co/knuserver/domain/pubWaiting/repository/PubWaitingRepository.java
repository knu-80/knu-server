package kr.co.knuserver.domain.pubWaiting.repository;

import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubWaitingRepository extends JpaRepository<PubWaiting, Long> {
}
