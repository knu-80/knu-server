package kr.co.knuserver.domain.pubTable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "pub_table", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_booth_table_num",
                columnNames = {"pub_booth_id", "table_num"}
        )
})
public class PubTable extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_table_id")
    private Long id;

    @Column(nullable = false)
    private int tableNum;

    @Column(name = "pub_booth_id", nullable = false)
    private Long pubBoothId;

    public static PubTable createPubTable(int tableNum, Long pubBoothId) {
        return PubTable.builder()
                .tableNum(tableNum)
                .pubBoothId(pubBoothId)
                .build();
    }

    public void updatePubTable(int tableNum,  Long pubBoothId) {
        this.tableNum = tableNum;
        this.pubBoothId = pubBoothId;
    }
}
