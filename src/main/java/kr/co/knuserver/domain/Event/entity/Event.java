package kr.co.knuserver.domain.Event.entity;


import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;


    @Builder(access = AccessLevel.PRIVATE)
    private Event(String title, String description, EventType type,
        String imageUrl, LocalDateTime startAt, LocalDateTime endAt, Boolean isActive) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.imageUrl = imageUrl;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isActive = isActive;
    }

    public static Event create(String title, String description, EventType type,
        String imageUrl, LocalDateTime startAt, LocalDateTime endAt, Boolean isActive){

        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("행사 종료 시간은 시작 시간보다 빨라야 합니다.");
        }

        return Event.builder()
            .title(title)
            .description(description)
            .type(type)
            .imageUrl(imageUrl)
            .startAt(startAt)
            .endAt(endAt)
            .isActive(true)
            .build();
    }
}
