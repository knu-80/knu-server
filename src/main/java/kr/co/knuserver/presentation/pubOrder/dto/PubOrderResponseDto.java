package kr.co.knuserver.presentation.pubOrder.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.knuserver.domain.pubOrder.entity.PubOrder;
import lombok.Builder;

@Builder
public record PubOrderResponseDto(
        @NotNull Long pubTableSessionId,
        @NotNull List<OrderedMenus> orderedMenus
) {
    public static PubOrderResponseDto fromEntity(Long pubTableSessionId, List<OrderedMenus> orderedMenus) {
        return PubOrderResponseDto.builder()
                .pubTableSessionId(pubTableSessionId)
                .orderedMenus(orderedMenus)
                .build();
    }
}
