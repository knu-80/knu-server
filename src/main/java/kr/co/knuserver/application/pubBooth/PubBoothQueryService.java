package kr.co.knuserver.application.pubBooth;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import kr.co.knuserver.domain.pubBooth.repository.PubBoothRepository;
import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubMenu.repository.PubMenuRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubBooth.dto.PubBoothDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubBoothQueryService {

    private final PubBoothRepository pubBoothRepository;
    private final PubMenuRepository pubMenuRepository;

    public List<PubBooth> findAll() {
        return pubBoothRepository.findAll();
    }

    public PubBoothDetailResponseDto findById(Long id) {
        PubBooth pubBooth = findPubBoothById(id);
        List<PubMenu> pubMenus = pubMenuRepository.findByPubBoothId(id);
        return PubBoothDetailResponseDto.from(pubBooth, pubMenus);
    }

    public PubBooth findPubBoothById(Long id) {
        return pubBoothRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_BOOTH_NOT_FOUND));
    }
}
