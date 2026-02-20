package kr.co.knuserver.presentation.event.dto;

import java.time.LocalDateTime;
import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.entity.EventType;

public record EventRequestDto(
    String title,
    String description,
    EventType eventType,
    String imageUrl,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Boolean isActive
) {
    public static Event toEntity(EventRequestDto request) {
        return Event.createEvent(
            request.title(),
            request.description(),
            request.eventType(),
            request.imageUrl(),
            request.startAt(),
            request.endAt()
        );
    }
}
