package kr.co.knuserver.presentation.pubMenu.dto;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PubMenuUpdateRequestDto {
    private String name;
    private int price;
    private String imageUrl;

    public void updateEntity(PubMenu pubMenu) {
        pubMenu.update(name, price, imageUrl);
    }
}
