package kr.co.knuserver.presentation.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.entity.EventType;

public record EventRequestDto(
    @NotBlank(message = "이벤트명은 공백일 수 없습니다.")
    String title,

    @NotBlank(message = "이벤트 설명은 공백일 수 없습니다.")
    String description,

    @NotBlank(message = "이벤트 장소는 공백일 수 없습니다.")
    String location,

    @NotNull(message = "이벤트 타입은 필수값입니다.")
    EventType eventType,

    @NotNull(message = "이벤트 시작 시각은 필수값입니다.")
    LocalDateTime startAt,

    @NotNull(message = "이벤트 종료 시각은 필수값입니다.")
    LocalDateTime endAt,

    @NotNull(message = "이벤트 활성화 여부는 필수값입니다.")
    Boolean isActive
) {
    public static Event toEntity(EventRequestDto request, String imageUrl) {
        return Event.createEvent(
            request.title(),
            request.description(),
            request.location(),
            imageUrl,
            request.eventType(),
            request.startAt(),
            request.endAt()
        );
    }
}
