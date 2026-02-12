package kr.co.knuserver.domain.Event.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DurationVO {

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    public static DurationVO createDurationVO(LocalDateTime startAt, LocalDateTime endAt) {
        validateTime(startAt, endAt);
        return new DurationVO(startAt, endAt);
    }

    private static void validateTime(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("행사 시작 시각은 종료 시각보다 빨라야 합니다.");
        }
    }

    /**
     * 주어진 기간보다 이후인지 확인합니다.
     *
     * @param duration 비교할 기간
     * @return 이후라면 true, 아니라면 false
     */
    public boolean isBefore(final DurationVO duration) {
        return endAt.isBefore(duration.startAt);
    }

    /**
     * 주어진 기간보다 이전인지 확인합니다.
     *
     * @param duration 비교할 기간
     * @return 이전이라면 true, 아니라면 false
     */
    public boolean isAfter(final DurationVO duration) {
        return startAt.isAfter(duration.endAt);
    }


    /**
     * 기간이 끝났는지 확인합니다.
     *
     * @param clock 현재 시간
     * @return 기간이 끝났다면 true, 아니라면 false
     */
    public boolean isEnded(final Clock clock) {
        final LocalDateTime now = LocalDateTime.now(clock);
        return now.isAfter(endAt);
    }

    /**
     * 시작일과 종료일의 차이를 구한다.
     *
     * @return 시작일과 종료일의 차이 일수
     */
    public Long days() {
        return ChronoUnit.DAYS.between(startAt, endAt);
    }

    /**
     * 주어진 날짜가 기간 내에 있는지 확인합니다.
     *
     * @param date 확인할 날짜
     * @return 기간 내에 있다면 true, 아니라면 false
     */
    public boolean isBetween(final LocalDateTime date) {
        return (date.isEqual(startAt) || date.isAfter(startAt)) &&
            (date.isBefore(endAt) || date.isEqual(endAt));
    }
}
