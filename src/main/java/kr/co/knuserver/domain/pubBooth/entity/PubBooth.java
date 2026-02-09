package kr.co.knuserver.domain.pubBooth.entity;

import jakarta.persistence.*;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "pub_booth")
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
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

    public static PubBooth createPubBooth(String boothName, String clubName, String description, String accountNum, Long memberId) {
        return PubBooth.builder()
                .boothName(boothName)
                .clubName(clubName)
                .description(description)
                .accountNum(accountNum)
                .memberId(memberId)
                .build();
    }
}
