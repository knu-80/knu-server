package kr.co.knuserver.application.pubOrder;

import java.util.List;
import kr.co.knuserver.application.pubMenu.PubMenuQueryService;
import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubOrder.entity.PubOrder;
import kr.co.knuserver.domain.pubOrder.repository.PubOrderRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubOrder.dto.OrderedMenus;
import kr.co.knuserver.presentation.pubOrder.dto.PubOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubOrderQueryService {

    private final PubOrderRepository pubOrderRepository;
    private final PubMenuQueryService pubMenuQueryService;

    public PubOrder findByOrderId(Long orderId) {
        return pubOrderRepository.findById(orderId).orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_ORDER_NOT_FOUND));
    }

    public PubOrderResponseDto getAllByPubTableSessionId(Long pubTableSessionId) {
        List<PubOrder> pubOrderList = pubOrderRepository.findAllByPubTableSessionId(pubTableSessionId);
        List<OrderedMenus> orderedMenus = pubOrderList.stream().map((pubOrder -> {
            PubMenu pubMenu = pubMenuQueryService.findPubMenuById(pubOrder.getPubMenuId());
            return OrderedMenus.fromEntity(pubOrder, pubMenu);
        })).toList();
        return PubOrderResponseDto.fromEntity(pubTableSessionId, orderedMenus);
    }
}
