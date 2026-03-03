package kr.co.knuserver.presentation.pubWaiting.dto;

import jakarta.validation.constraints.NotNull;

public record PubWaitingCancelRequestDto(
        @NotNull Long pubBoothId,
        @NotNull Long memberId,
        @NotNull Long pubWaitingId
) {
}
