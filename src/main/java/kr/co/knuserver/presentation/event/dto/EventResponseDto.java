package kr.co.knuserver.presentation.event.dto;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.entity.EventType;
import lombok.Builder;

@Builder
public record EventResponseDto(
    Long id,
    String title,
    String description,
    String location,
    EventType eventType,
    String imageUrl,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Boolean isActive
) {

    public static EventResponseDto fromEntity(Event entity) {
        return EventResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .location(entity.getLocation())
            .eventType(entity.getEventType())
            .imageUrl(entity.getImageUrl())
            .startAt(entity.getEventDuration().getStartAt())
            .endAt(entity.getEventDuration().getEndAt())
            .isActive(entity.getIsActive())
            .build();
    }
}
