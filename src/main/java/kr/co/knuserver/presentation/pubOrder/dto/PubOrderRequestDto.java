package kr.co.knuserver.presentation.pubOrder.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PubOrderRequestDto(
        @NotNull Long pubTableSessionId,
        @NotNull List<OrderMenus> orderMenus
) {
}
