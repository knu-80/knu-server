package kr.co.knuserver.presentation.performance.dto;

import java.time.LocalDateTime;

public record PerformanceUpdateRequestDto(
    LocalDateTime startAt,
    LocalDateTime endAt
) {}
