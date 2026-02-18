package kr.co.knuserver.presentation.pubTableSession.dto;

import jakarta.validation.constraints.NotNull;

public record PubTableSessionStartRequestDto(
        @NotNull Long pubTableId,
        @NotNull Long pubWaitingId
) {
}
