package kr.co.knuserver.application.pubOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.knuserver.application.pubMenu.PubMenuQueryService;
import kr.co.knuserver.application.pubTableSession.PubTableSessionQueryService;
import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubOrder.entity.PubOrder;
import kr.co.knuserver.domain.pubOrder.repository.PubOrderRepository;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubOrder.dto.OrderMenus;
import kr.co.knuserver.presentation.pubOrder.dto.OrderedMenus;
import kr.co.knuserver.presentation.pubOrder.dto.PubOrderRequestDto;
import kr.co.knuserver.presentation.pubOrder.dto.PubOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PubOrderCommandService {

    private final PubOrderRepository pubOrderRepository;
    private final PubOrderQueryService pubOrderQueryService;
    private final PubTableSessionQueryService pubTableSessionQueryService;
    private final PubMenuQueryService pubMenuQueryService;

    public PubOrderResponseDto createOrder(PubOrderRequestDto request) {
        PubTableSession pubTableSession = pubTableSessionQueryService.getPubTableSessionById(request.pubTableSessionId());

        List<Long> menuIds = request.orderMenus().stream()
                .map(OrderMenus::menuId)
                .distinct()
                .toList();
        List<PubMenu> foundMenus = pubMenuQueryService.findAllPubMenuByIds(menuIds);

        if (foundMenus.size() != menuIds.size()) {
            throw new BusinessException(BusinessErrorCode.PUB_MENU_NOT_FOUND);
        }

        Map<Long, PubMenu> pubMenuMap = foundMenus.stream()
                .collect(Collectors.toMap(PubMenu::getId, Function.identity()));

        List<PubOrder> pubOrders = new ArrayList<>();
        for (OrderMenus orderMenus : request.orderMenus()) {
            pubOrders.add(PubOrder.createPubOrder(pubTableSession.getId(), orderMenus.menuId(), orderMenus.quantity()));
        }
        List<PubOrder> savedPubOrders = pubOrderRepository.saveAll(pubOrders);

        List<OrderedMenus> orderedMenus = savedPubOrders.stream().map((pubOrder -> {
            PubMenu pubMenu = pubMenuMap.get(pubOrder.getPubMenuId());
            return OrderedMenus.fromEntity(pubOrder, pubMenu);
        })).toList();
        return PubOrderResponseDto.fromEntity(request.pubTableSessionId(), orderedMenus);
    }

    public void deleteOrder(Long pubOrderId) {
        PubOrder pubOrder = pubOrderQueryService.findByOrderId(pubOrderId);
        pubOrderRepository.delete(pubOrder);
    }
}
