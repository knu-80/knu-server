package kr.co.knuserver.application.pubBooth;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import kr.co.knuserver.domain.pubBooth.repository.PubBoothRepository;
import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubMenu.repository.PubMenuRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubBooth.dto.PubBoothCreateRequestDto;
import kr.co.knuserver.presentation.pubBooth.dto.PubBoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PubBoothCommandService {

    private final PubBoothRepository pubBoothRepository;
    private final PubMenuRepository pubMenuRepository;

    public Long create(PubBoothCreateRequestDto requestDto) {
        PubBooth pubBooth = requestDto.toEntity();
        PubBooth savedPubBooth = pubBoothRepository.save(pubBooth);
        return savedPubBooth.getId();
    }

    public void update(Long id, PubBoothUpdateRequestDto requestDto) {
        PubBooth pubBooth = findPubBoothById(id);
        requestDto.updateEntity(pubBooth);
    }

    public void delete(Long id) {
        PubBooth pubBooth = findPubBoothById(id);
        List<PubMenu> pubMenus = pubMenuRepository.findByPubBoothId(id);
        pubMenuRepository.deleteAll(pubMenus);
        pubBoothRepository.delete(pubBooth);
    }

    private PubBooth findPubBoothById(Long id) {
        return pubBoothRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_BOOTH_NOT_FOUND));
    }
}
