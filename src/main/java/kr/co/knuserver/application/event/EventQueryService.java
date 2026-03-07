package kr.co.knuserver.application.event;

import java.util.List;
import kr.co.knuserver.domain.event.entity.EventType;
import kr.co.knuserver.domain.event.repository.EventRepository;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EventQueryService {
    private final EventRepository eventRepository;

    // 이벤트 리스트 조회(type에 따라)
    public List<EventResponseDto> getEventListByEventType(EventType eventType){
        return eventRepository.findAllByEventTypeAndIsActiveTrue(eventType)
            .stream()
            .map(EventResponseDto::fromEntity)
            .toList();
    }

}
