package kr.co.knuserver.domain.pubOrder.repository;

import java.util.List;
import kr.co.knuserver.domain.pubOrder.entity.PubOrder;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubOrderRepository extends JpaRepository<PubOrder, Long> {
    List<PubOrder> findAllByPubTableSessionId(Long pubTableSessionId);
}
