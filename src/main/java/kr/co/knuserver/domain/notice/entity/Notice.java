package kr.co.knuserver.domain.notice.entity;

import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

//  TODO: Member 도메인 정의되면 추가할 예정
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;


//  TODO: Member 도메인 정의되면 파라미터 추가할 예정
    public static Notice createNotice(String title, String content, NoticeType type) {
        return Notice.builder()
                .title(title)
                .content(content)
                .type(type)
                .build();
    }
}