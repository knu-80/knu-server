package kr.co.knuserver.domain.performance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.co.knuserver.domain.common.vo.DurationVO;
import kr.co.knuserver.global.base.BaseTimeEntity;
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
@Table(name = "performance")
public class Performance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booth_id", nullable = false)
    private Long boothId;

    @Column(name = "session", nullable = false)
    private Integer session;

    @Embedded
    private DurationVO performanceDuration;

    public static Performance create(Long boothId, Integer session, LocalDateTime startAt, LocalDateTime endAt) {
        DurationVO duration = DurationVO.createDurationVO(startAt, endAt);
        return Performance.builder()
            .boothId(boothId)
            .session(session)
            .performanceDuration(duration)
            .build();
    }

    public void update(LocalDateTime startAt, LocalDateTime endAt) {
        LocalDateTime updatedStartAt = startAt != null ? startAt : this.performanceDuration.getStartAt();
        LocalDateTime updatedEndAt = endAt != null ? endAt : this.performanceDuration.getEndAt();
        this.performanceDuration = DurationVO.createDurationVO(updatedStartAt, updatedEndAt);
    }
}
