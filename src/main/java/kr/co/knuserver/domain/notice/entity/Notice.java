package kr.co.knuserver.domain.notice.entity;

import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice")
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

    @Builder
    public Notice(Long id, String title, String content, NoticeType type, Long memberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.memberId = memberId;
    }

    public static Notice createNotice(String title, String content, NoticeType type, Long memberId) {
        return Notice.builder()
                .title(title)
                .content(content)
                .type(type)
                .memberId(memberId)
                .build();
    }
}