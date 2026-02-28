package kr.co.knuserver.domain.pubTableSession.repository;

import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubTableSessionRepository extends JpaRepository<PubTableSession, Long> {
    PubTableSession findByPubTableIdAndExitTimeIsNull(Long pubTableId);
}
