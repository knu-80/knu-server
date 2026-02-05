package kr.co.knuserver.domain.pubTable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "pub_table")
public class PubTable extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_table_id")
    private Long id;

    @Column(nullable = false)
    private int tableNum;

    // TODO PubBooth 연결하기
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "pub_booth_id")
    // private PubBooth pubBooth;

    public static PubTable createPubTable(int tableNum) {
        // TODO PubBooth 연결하기
        return PubTable.builder().tableNum(tableNum).build();
    }
}
