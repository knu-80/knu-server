package kr.co.knuserver.domain.pubBooth;

import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pub_booth")
public class PubBooth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_booth_id")
    private Long id;

    private String boothName;
    private String clubName;
    private String description;
    private String accountNum;
    private Long memberId;

    @Builder
    public PubBooth(String boothName, String clubName, String description, String accountNum, Long memberId) {
        this.boothName = boothName;
        this.clubName = clubName;
        this.description = description;
        this.accountNum = accountNum;
        this.memberId = memberId;
    }
}
