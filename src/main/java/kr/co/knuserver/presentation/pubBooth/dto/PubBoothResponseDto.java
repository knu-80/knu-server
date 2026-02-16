package kr.co.knuserver.presentation.pubBooth.dto;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PubBoothResponseDto {
    private Long id;
    private String boothName;
    private String clubName;
    private String description;
    private String accountNum;
    private Long memberId;

    public static PubBoothResponseDto from(PubBooth pubBooth) {
        return PubBoothResponseDto.builder()
                .id(pubBooth.getId())
                .boothName(pubBooth.getBoothName())
                .clubName(pubBooth.getClubName())
                .description(pubBooth.getDescription())
                .accountNum(pubBooth.getAccountNum())
                .memberId(pubBooth.getMemberId())
                .build();
    }
}
