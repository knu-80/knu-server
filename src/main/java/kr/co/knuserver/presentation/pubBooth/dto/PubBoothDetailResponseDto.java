package kr.co.knuserver.presentation.pubBooth.dto;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import kr.co.knuserver.domain.pubMenu.dto.PubMenuResponseDto;
import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PubBoothDetailResponseDto {
    private Long id;
    private String boothName;
    private String clubName;
    private String description;
    private String accountNum;
    private Long memberId;
    private List<PubMenuResponseDto> pubMenus;

    public static PubBoothDetailResponseDto from(PubBooth pubBooth, List<PubMenu> pubMenus) {
        return PubBoothDetailResponseDto.builder()
                .id(pubBooth.getId())
                .boothName(pubBooth.getBoothName())
                .clubName(pubBooth.getClubName())
                .description(pubBooth.getDescription())
                .accountNum(pubBooth.getAccountNum())
                .memberId(pubBooth.getMemberId())
                .pubMenus(pubMenus.stream().map(PubMenuResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
