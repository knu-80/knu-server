package kr.co.knuserver.presentation.pubTableSession.dto;

import jakarta.validation.constraints.NotNull;

public record PubTableSessionStartRequest(
        @NotNull Long pubTableId,
        @NotNull Long pubWaitingId
) {
}
