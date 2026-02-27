package kr.co.knuserver.domain.booth.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoothDivision {
    ACADEMIC_DIVISION("학술분과"),
    CULTURE_ART_DIVISION("문예분과"),
    SPORTS_DIVISION("체육분과"),
    SOCIAL_DIVISION("사회분과"),
    RELIGIOUS_DIVISION("종교분과"),
    MANAGEMENT("총동아리연합회"),
    EXTERNAL_SUPPORT("외부부스");


    private final String description;
}