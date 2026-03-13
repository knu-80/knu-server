package kr.co.knuserver.presentation.pubOrder.dto;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubOrder.entity.PubOrder;
import lombok.Builder;

@Builder
public record OrderedMenus(
        Long orderId,
        Long menuId,
        String name,
        int price,
        Integer quantity
) {
    public static OrderedMenus fromEntity(PubOrder pubOrder, PubMenu pubMenu) {
        return OrderedMenus.builder()
                .orderId(pubOrder.getId())
                .menuId(pubMenu.getId())
                .name(pubMenu.getName())
                .price(pubMenu.getPrice())
                .quantity(pubOrder.getQuantity())
                .build();
    }
}