package kr.co.knuserver.domain.pubMenu.dto;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PubMenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public static PubMenuResponseDto from(PubMenu pubMenu) {
        return PubMenuResponseDto.builder()
                .id(pubMenu.getId())
                .name(pubMenu.getName())
                .price(pubMenu.getPrice())
                .imageUrl(pubMenu.getImageUrl())
                .build();
    }
}
