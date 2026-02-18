package kr.co.knuserver.domain.pubTableSession.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
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
@Table(name = "pub_table_session")
public class PubTableSession extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_table_session_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    @Column(nullable = false)
    private int guestCount;

    @Column(name = "pub_table_id", nullable = false)
    private Long pubTableId;

    public static PubTableSession createPubTableSession(int guestCount, Long pubTableId) {
        return PubTableSession.builder()
                .guestCount(guestCount)
                .entryTime(LocalDateTime.now())
                .pubTableId(pubTableId)
                .build();
    }

    public void guestExit() {
        this.exitTime = LocalDateTime.now();
    }
}
