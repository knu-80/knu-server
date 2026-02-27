package kr.co.knuserver.application.event;

import java.util.List;
import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.entity.EventType;
import kr.co.knuserver.domain.event.repository.EventRepository;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventReader eventReader;

    // 이벤트 생성
    public EventResponseDto registerEvent(EventRequestDto request){
        Event event = eventRepository.save(EventRequestDto.toEntity(request));
        return EventResponseDto.fromEntity(event);
    }

    // 이벤트 단건 조회
    public EventResponseDto getEvent(Long eventId){
        Event event = eventReader.getEventOrThrow(eventId);
        return EventResponseDto.fromEntity(event);
    }

    // 이벤트 리스트 조회(type에 따라)
    public List<EventResponseDto> getEventListByEventType(EventType eventType){
        return eventRepository.findAllByEventTypeAndIsActiveTrue(eventType)
            .stream()
            .map(EventResponseDto::fromEntity)
            .toList();
    }

    // 이벤트 수정
    public EventResponseDto updateEvent(Long eventId, EventRequestDto request){
        Event event = eventReader.getEventOrThrow(eventId);
        event.updateEvent(request);
        return EventResponseDto.fromEntity(event);
    }

    // 이벤트 삭제
    public void deleteEvent(Long eventId){
        Event event= eventReader.getEventOrThrow(eventId);
        eventRepository.delete(event);
    }

}
