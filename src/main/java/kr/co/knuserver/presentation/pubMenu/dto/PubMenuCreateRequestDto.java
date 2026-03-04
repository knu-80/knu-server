package kr.co.knuserver.presentation.pubMenu.dto;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PubMenuCreateRequestDto {
    private String name;
    private int price;
    private String imageUrl;
    private Long pubBoothId;

    public PubMenu toEntity() {
        return PubMenu.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .pubBoothId(pubBoothId)
                .build();
    }
}
