package kr.co.knuserver.presentation.performance.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PerformanceCreateRequestDto(
    @NotNull(message = "부스 ID는 필수입니다.")
    Long boothId,

    @NotNull(message = "세션 번호는 필수입니다.")
    Integer session,

    @NotNull(message = "공연 시작 시각은 필수입니다.")
    LocalDateTime startAt,

    @NotNull(message = "공연 종료 시각은 필수입니다.")
    LocalDateTime endAt
) {}
