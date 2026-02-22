package kr.co.knuserver.presentation.pubBooth.dto;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PubBoothCreateRequestDto {
    private String boothName;
    private String clubName;
    private String description;
    private String accountNum;
    private Long memberId;

    public PubBooth toEntity() {
        return PubBooth.builder()
                .boothName(this.boothName)
                .clubName(this.clubName)
                .description(this.description)
                .accountNum(this.accountNum)
                .memberId(this.memberId)
                .build();
    }
}
