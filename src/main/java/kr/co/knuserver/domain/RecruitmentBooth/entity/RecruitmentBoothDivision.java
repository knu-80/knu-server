package kr.co.knuserver.domain.RecruitmentBooth.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentBoothDivision {
    ACADEMIC_DIVISION("학술분과"),
    CULTURE_ART_DIVISION("문예분과"),
    SPORTS_DIVISION("체육분과"),
    SOCIAL_DIVISION("사회분과"),
    RELIGIOUS_DIVISION("종교분과");

    private final String description;
}