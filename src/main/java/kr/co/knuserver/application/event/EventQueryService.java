package kr.co.knuserver.application.event;

import java.util.List;
import kr.co.knuserver.domain.event.entity.Event;
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

    private final EventReader eventReader;
    private final EventRepository eventRepository;

    // 이벤트 단건 조회
    public EventResponseDto getEvent(Long eventId) {
        Event event = eventReader.getEventOrThrow(eventId);
        return EventResponseDto.fromEntity(event);
    }

    // 이벤트 리스트 조회(type에 따라)
    public List<EventResponseDto> getEventListByEventType(EventType eventType) {
        List<Event> events = eventRepository.findAllByEventTypeAndIsActiveTrue(eventType);

        return events.stream()
            .map(EventResponseDto::fromEntity)
            .toList();
    }
}
