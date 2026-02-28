package kr.co.knuserver.domain.pubWaiting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "pub_waiting")
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
public class PubWaiting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_waiting_id")
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private int guestCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PubWaitingStatus status;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "pub_booth_id", nullable = false)
    private Long pubBoothId;

    public static PubWaiting createPubWaiting(String phone, int guestCount, Long memberId, Long pubBoothId) {
        return PubWaiting.builder()
                .phone(phone)
                .guestCount(guestCount)
                .memberId(memberId)
                .pubBoothId(pubBoothId)
                .build();
    }

    public void entered() {
        this.status = PubWaitingStatus.ENTERED;
    }
}
