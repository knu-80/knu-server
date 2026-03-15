package kr.co.knuserver.domain.pubOrder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
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
@Table(name = "pub_order")
public class PubOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_order_id")
    private Long id;

    @Column(name = "pub_table_session_id", nullable = false)
    private Long pubTableSessionId;

    @Column(name = "pub_menu_id", nullable = false)
    private Long pubMenuId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public static PubOrder createPubOrder(Long pubSessionId, Long pubMenuId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("주문 수량은 양수만 입력 가능합니다.", BusinessErrorCode.INVALID_INPUT_VALUE);
        }

        return PubOrder.builder()
                .pubTableSessionId(pubSessionId)
                .pubMenuId(pubMenuId)
                .quantity(quantity)
                .build();
    }
}
