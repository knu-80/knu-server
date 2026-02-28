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
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
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

        validate(memberId, boothNumber, name, division);

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

    private static void validate(Long memberId, Integer boothNumber, String name,
        BoothDivision division) {
        if (memberId == null) {
            throw new BusinessException("부스 운영자 member ID는 필수입니다.",
                BusinessErrorCode.MISSING_INPUT_VALUE);
        }

        if (memberId <= 0) {
            throw new BusinessException("member ID는 양수만 입력 가능합니다.",
                BusinessErrorCode.INVALID_INPUT_VALUE);
        }

        if (boothNumber == null) {
            throw new BusinessException("부스 번호는 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }

        if (boothNumber <= 0) {
            throw new BusinessException("boothNumber는 양수만 입력 가능합니다.",
                BusinessErrorCode.INVALID_INPUT_VALUE);
        }

        if (name == null || name.isBlank()) {
            throw new BusinessException("부스 이름은 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }

        if (name.length() > 50) {
            throw new BusinessException("부스 이름은 50자를 초과할 수 없습니다.",
                BusinessErrorCode.INVALID_INPUT_VALUE);
        }

        if (division == null) {
            throw new BusinessException("부스 분과는 필수입니다.", BusinessErrorCode.MISSING_INPUT_VALUE);
        }
    }

    public void updateFromDto(BoothUpdateRequestDto request) {
        if (request.memberId() != null) {
            if (request.memberId() <= 0) {
                throw new BusinessException("member ID는 양수만 입력 가능합니다.",
                    BusinessErrorCode.INVALID_INPUT_VALUE);
            }
            this.memberId = request.memberId();
        }

        if (request.boothNumber() != null) {
            if (request.boothNumber() <= 0) {
                throw new BusinessException("boothNumber는 양수만 입력 가능합니다.",
                    BusinessErrorCode.INVALID_INPUT_VALUE);
            }
            this.boothNumber = request.boothNumber();
        }

        if (request.name() != null) {
            if (request.name().isBlank()) {
                throw new BusinessException("부스 이름은 비어있을 수 없습니다.",
                    BusinessErrorCode.INVALID_INPUT_VALUE);
            }
            if (request.name().length() > 50) {
                throw new BusinessException("부스 이름은 50자를 초과할 수 없습니다.",
                    BusinessErrorCode.INVALID_INPUT_VALUE);
            }
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
