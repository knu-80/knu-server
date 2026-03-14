package kr.co.knuserver.application.event;

import java.util.List;
import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.entity.EventType;
import kr.co.knuserver.domain.event.repository.EventRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
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

    public EventResponseDto getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.EVENT_NOT_FOUND));
        return EventResponseDto.fromEntity(event);
    }

    public List<EventResponseDto> getEventListByEventType(EventType eventType) {
        List<Event> events = eventRepository.findAllByEventTypeAndIsActiveTrueOrderByIdAsc(eventType);

        return events.stream()
            .map(EventResponseDto::fromEntity)
            .toList();
    }
}
