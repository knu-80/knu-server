package kr.co.knuserver.presentation.pubBooth.dto;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
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

    public void updateEntity(PubBooth pubBooth) {
        pubBooth.update(boothName, clubName, description, accountNum, memberId);
    }
}
