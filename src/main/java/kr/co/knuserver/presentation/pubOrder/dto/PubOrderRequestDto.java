package kr.co.knuserver.presentation.pubOrder.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PubOrderRequestDto(
        @NotNull Long pubTableSessionId,
        @NotEmpty List<@Valid OrderMenus> orderMenus
) {
}
