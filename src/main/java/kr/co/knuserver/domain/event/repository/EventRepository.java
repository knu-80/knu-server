package kr.co.knuserver.domain.event.repository;

import java.util.List;
import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    // 주어진 EventType 중 isActive==true인 event들만 리스트로 조회
    List<Event> findAllByEventTypeAndIsActiveTrue(EventType eventType);
}
