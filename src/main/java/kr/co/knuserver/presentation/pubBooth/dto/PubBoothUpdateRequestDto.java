package kr.co.knuserver.presentation.pubBooth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PubBoothUpdateRequestDto {
    private String boothName;
    private String clubName;
    private String description;
    private String accountNum;
    private Long memberId;
}
