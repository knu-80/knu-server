package kr.co.knuserver.domain.pubOrder.repository;

import kr.co.knuserver.domain.pubOrder.entity.PubOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubOrderRepository extends JpaRepository<PubOrder, Long> {
}
