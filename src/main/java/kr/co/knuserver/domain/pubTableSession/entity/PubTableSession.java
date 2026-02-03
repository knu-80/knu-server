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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pub_table_id")
    private PubTable pubTable;

    @Builder
    public PubTableSession(int guestCount,  PubTable pubTable) {
        this.entryTime = LocalDateTime.now();
        this.guestCount = guestCount;
        this.pubTable = pubTable;
    }

    public void guestExit() {
        this.exitTime = LocalDateTime.now();
    }
}
