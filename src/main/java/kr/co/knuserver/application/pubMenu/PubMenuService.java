package kr.co.knuserver.application.pubMenu;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubMenu.repository.PubMenuRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuCreateRequestDto;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubMenuService {

    private final PubMenuRepository pubMenuRepository;

    public PubMenu findPubMenuById(Long id) {
        return pubMenuRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_MENU_NOT_FOUND));
    }

    @Transactional
    public Long create(PubMenuCreateRequestDto requestDto) {
        PubMenu pubMenu = requestDto.toEntity();
        PubMenu savedPubMenu = pubMenuRepository.save(pubMenu);
        return savedPubMenu.getId();
    }

    @Transactional
    public void update(Long id, PubMenuUpdateRequestDto requestDto) {
        PubMenu pubMenu = findPubMenuById(id);
        requestDto.updateEntity(pubMenu);
    }

    @Transactional
    public void delete(Long id) {
        PubMenu pubMenu = findPubMenuById(id);
        pubMenuRepository.delete(pubMenu);
    }
}
