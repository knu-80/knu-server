package kr.co.knuserver.application.pubMenu;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import kr.co.knuserver.domain.pubMenu.repository.PubMenuRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuCreateRequestDto;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PubMenuCommandService {

    private final PubMenuRepository pubMenuRepository;
    private final PubMenuQueryService pubMenuQueryService; // Inject QueryService to find entities

    public Long create(PubMenuCreateRequestDto requestDto) {
        PubMenu pubMenu = requestDto.toEntity();
        PubMenu savedPubMenu = pubMenuRepository.save(pubMenu);
        return savedPubMenu.getId();
    }

    public void update(Long id, PubMenuUpdateRequestDto requestDto) {
        PubMenu pubMenu = pubMenuQueryService.findPubMenuById(id); // Use QueryService to find
        requestDto.updateEntity(pubMenu);
    }

    public void delete(Long id) {
        PubMenu pubMenu = pubMenuQueryService.findPubMenuById(id); // Use QueryService to find
        pubMenuRepository.delete(pubMenu);
    }
}
