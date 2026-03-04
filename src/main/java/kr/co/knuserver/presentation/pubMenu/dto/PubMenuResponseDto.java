package kr.co.knuserver.presentation.pubMenu.dto;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PubMenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private Long pubBoothId;

    public static PubMenuResponseDto from(PubMenu pubMenu) {
        return PubMenuResponseDto.builder()
                .id(pubMenu.getId())
                .name(pubMenu.getName())
                .price(pubMenu.getPrice())
                .imageUrl(pubMenu.getImageUrl())
                .pubBoothId(pubMenu.getPubBoothId())
                .build();
    }
}
