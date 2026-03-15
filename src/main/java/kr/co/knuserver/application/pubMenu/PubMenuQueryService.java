package kr.co.knuserver.application.pubMenu;

import java.util.List;
import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubMenu.repository.PubMenuRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubMenuQueryService {

    private final PubMenuRepository pubMenuRepository;

    public PubMenu findPubMenuById(Long id) {
        return pubMenuRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_MENU_NOT_FOUND));
    }

    public List<PubMenu> findAllPubMenuByIds(List<Long> ids) {
        return pubMenuRepository.findAllById(ids);
    }
}
