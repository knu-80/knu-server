package kr.co.knuserver.presentation.pubWaiting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PubWaitingRegisterRequestDto(
        @NotNull Long pubBoothId,
        @NotNull Long memberId,
        @NotBlank String phone,
        int guestCount
) {
}
