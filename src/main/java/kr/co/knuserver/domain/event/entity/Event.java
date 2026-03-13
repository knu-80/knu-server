package kr.co.knuserver.domain.event.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.co.knuserver.global.base.BaseTimeEntity;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "event")
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String location;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Embedded
    private DurationVO eventDuration;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public static Event createEvent(String title, String description, String location, String imageUrl, EventType type,
        LocalDateTime startAt, LocalDateTime endAt) {

        validate(title, description, location, type);
        DurationVO eventDuration = DurationVO.createDurationVO(startAt, endAt);

        return Event.builder()
            .title(title)
            .description(description)
            .location(location)
            .imageUrl(imageUrl)
            .eventType(type)
            .eventDuration(eventDuration)
            .isActive(true)
            .build();
    }

    public void updateEvent(EventRequestDto request, String imageUrl){
        if(request.title() !=null){
            if(!request.title().isBlank()) {
                this.title = request.title();
            }
        }
        if (request.description() != null) {
            if(!request.description().isBlank()) {
                this.description = request.description();
            }
        }
        if (request.location() != null) {
            this.location = request.location();
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
        if (request.eventType() != null) {
            this.eventType = request.eventType();
        }

        LocalDateTime updatedStartAt = this.eventDuration.getStartAt();
        LocalDateTime updatedEndAt = this.eventDuration.getEndAt();

        if (request.startAt() != null) {
            updatedStartAt = request.startAt();
        }
        if (request.endAt() != null) {
            updatedEndAt = request.endAt();
        }

        this.eventDuration = DurationVO.createDurationVO(updatedStartAt, updatedEndAt);

        if (request.isActive() != null) {
            this.isActive = request.isActive();
        }
    }

    private static void validate(String title, String description, String location, EventType type){

        if(title==null || title.isBlank()){
            throw new BusinessException("이벤트명은 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }

        if(description==null || description.isBlank()){
            throw new BusinessException("이벤트 설명은 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }

        if(location==null || location.isBlank()){
            throw new BusinessException("이벤트 장소는 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }

        if(type==null){
            throw new BusinessException("이벤트 타입은 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }
    }
}
