package kr.co.knuserver.presentation.pubTableSession.dto;

import jakarta.validation.constraints.NotNull;

public record PubTableSessionEndRequestDto(
        @NotNull Long pubTableSessionId
) {
}
