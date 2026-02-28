package kr.co.knuserver.application.pubTable;

import java.util.List;
import kr.co.knuserver.application.pubTableSession.PubTableSessionQueryService;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTable.repository.PubTableRepository;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubTable.dto.PubTableRequestDto;
import kr.co.knuserver.presentation.pubTable.dto.PubTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PubTableCommandService {

    private final PubTableRepository pubTableRepository;
    private final PubTableQueryService pubTableQueryService;

    public PubTableResponseDto createPubTable(PubTableRequestDto request) {
        pubTableQueryService.validateDuplicateTable(request.tableNum(), request.pubBoothId());

        try {
            PubTable pubTable = request.toEntity();
            pubTableRepository.saveAndFlush(pubTable);

            return PubTableResponseDto.fromEntity(pubTable);

        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(BusinessErrorCode.ALREADY_EXISTS);
        }
    }

    public PubTableResponseDto updatePubTable(PubTableRequestDto request, Long pubTableId) {
        PubTable pubTable = pubTableQueryService.getPubTableById(pubTableId);

        boolean changed =
                pubTable.getTableNum() != request.tableNum()
                        || !pubTable.getPubBoothId().equals(request.pubBoothId());
        if (changed) {
            pubTableQueryService.validateDuplicateTable(request.tableNum(), request.pubBoothId());
        }
        try {
            pubTable.updatePubTable(request.tableNum(), request.pubBoothId());
            pubTableRepository.flush();
            return PubTableResponseDto.fromEntity(pubTable);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(BusinessErrorCode.ALREADY_EXISTS);
        }
    }

    public void deletePubTable(Long pubTableId) {
        PubTable pubTable = pubTableQueryService.getPubTableById(pubTableId);
        pubTableRepository.delete(pubTable);
    }
}
