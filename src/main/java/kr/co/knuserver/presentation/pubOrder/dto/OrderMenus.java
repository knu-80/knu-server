package kr.co.knuserver.presentation.pubOrder.dto;

import jakarta.validation.constraints.NotNull;

public record OrderMenus(
        @NotNull Long menuId,
        @NotNull Integer quantity
) {
}
