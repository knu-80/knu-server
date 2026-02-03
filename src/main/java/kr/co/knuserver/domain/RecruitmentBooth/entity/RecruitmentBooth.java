package kr.co.knuserver.domain.RecruitmentBooth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recruitment_booth")
public class RecruitmentBooth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_booth_id")
    private Long id;

// TODO
//    Member 도메인 정의되면 추가할 예정
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

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

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Member 도메인 추가 시에, parameter 수정 예정
    @Builder
    public RecruitmentBooth(Integer boothNumber, String name,
        RecruitmentBoothDivision division, String description,
        String applyLink, Boolean isActive, String imageUrl) {

        this.boothNumber = boothNumber;
        this.name = name;
        this.division = division;
        this.description = description;
        this.applyLink = applyLink;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
