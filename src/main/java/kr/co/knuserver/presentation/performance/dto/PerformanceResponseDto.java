package kr.co.knuserver.presentation.performance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.performance.entity.Performance;

public record PerformanceResponseDto(
    Long id,
    LocalDate date,
    Integer session,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Long boothId,
    String boothName
) {
    public static PerformanceResponseDto of(Performance performance, Booth booth) {
        return new PerformanceResponseDto(
            performance.getId(),
            performance.getPerformanceDuration().getStartAt().toLocalDate(),
            performance.getSession(),
            performance.getPerformanceDuration().getStartAt(),
            performance.getPerformanceDuration().getEndAt(),
            booth.getId(),
            booth.getName()
        );
    }
}
