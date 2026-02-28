package kr.co.knuserver.domain.notice.entity;

import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notice_image")
public class NoticeImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_image_id")
    private Long id;

    @Column(name = "notice_id", nullable = false)
    private Long noticeId;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    public static NoticeImage of(Long noticeId, String imageUrl) {
        return NoticeImage.builder()
                .noticeId(noticeId)
                .imageUrl(imageUrl)
                .build();
    }
}
