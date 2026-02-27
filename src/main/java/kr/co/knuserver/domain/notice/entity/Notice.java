package kr.co.knuserver.domain.notice.entity;

import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notice")
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeType type;

    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    private LostFoundDetail lostFoundDetail;

    public static Notice createNotice(String title, String content, NoticeType type, Long memberId, LostFoundDetail lostFoundDetail) {
        return Notice.builder()
                .title(title)
                .content(content)
                .type(type)
                .memberId(memberId)
                .lostFoundDetail(lostFoundDetail)
                .build();
    }

    public void updateNotice(String title, String content, LostFoundDetail lostFoundDetail) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
        if (lostFoundDetail != null) {
            if (this.type != NoticeType.LOST_FOUND) {
                throw new BusinessException(BusinessErrorCode.INVALID_INPUT_VALUE);
            }
            this.lostFoundDetail = lostFoundDetail;
        }
    }
}
