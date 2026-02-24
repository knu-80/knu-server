package kr.co.knuserver.domain.booth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.knuserver.global.base.BaseTimeEntity;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "booth")
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
public class Booth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "booth_number", nullable = false)
    private Integer boothNumber;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoothDivision division;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "apply_link", columnDefinition = "TEXT")
    private String applyLink;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public static Booth createBooth(Long memberId, Integer boothNumber, String name,
        BoothDivision division, String description,
        String applyLink, String imageUrl) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("부스 이름은 필수입니다.");
        }

        return Booth.builder()
            .memberId(memberId)
            .boothNumber(boothNumber)
            .name(name)
            .division(division)
            .description(description)
            .applyLink(applyLink)
            .isActive(true)
            .imageUrl(imageUrl)
            .build();
    }

    public void updateFromDto(BoothUpdateRequestDto request) {
        if (request.memberId() != null) {
            this.memberId = request.memberId();
        }
        if (request.boothNumber() != null) {
            this.boothNumber = request.boothNumber();
        }
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.division() != null) {
            this.division = request.division();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.applyLink() != null) {
            this.applyLink = request.applyLink();
        }
        if (request.imageUrl() != null) {
            this.imageUrl = request.imageUrl();
        }
        if (request.isActive() != null) {
            this.isActive = request.isActive();
        }
    }
}
