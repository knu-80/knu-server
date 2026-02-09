package kr.co.knuserver.domain.RecruitmentBooth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "recruitment_booth")
public class RecruitmentBooth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_booth_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "booth_number", nullable = false)
    private Integer boothNumber;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentBoothDivision division;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "apply_link", columnDefinition = "TEXT")
    private String applyLink;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public static RecruitmentBooth createRecruitmentBooth(Integer boothNumber, String name,
        RecruitmentBoothDivision division, String description,
        String applyLink, Boolean isActive, String imageUrl) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("부스 이름은 필수입니다.");
        }

        return RecruitmentBooth.builder()
            .boothNumber(boothNumber)
            .name(name)
            .division(division)
            .description(description)
            .applyLink(applyLink)
            .isActive(true)
            .imageUrl(imageUrl)
            .build();
    }
}
