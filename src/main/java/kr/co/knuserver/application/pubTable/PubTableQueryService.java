package kr.co.knuserver.application.pubTable;

import java.util.List;
import kr.co.knuserver.application.pubTableSession.PubTableSessionQueryService;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTable.repository.PubTableRepository;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubTable.dto.PubTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubTableQueryService {

    private final PubTableRepository pubTableRepository;
    private final PubTableSessionQueryService pubTableSessionQueryService;

    public void validateDuplicateTable(int tableNum, Long pubBoothId) {
        // TODO 해당 부스의 소유자인지 체크
        if (pubTableRepository.existsByTableNumAndPubBoothId(tableNum, pubBoothId)) {
            throw new BusinessException(BusinessErrorCode.ALREADY_EXISTS);
        }
    }

    public PubTable getPubTableById(Long pubTableId) {
        // TODO 해당 부스의 소유자인지 체크
        return pubTableRepository.findById(pubTableId).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_TABLE_NOT_FOUND)
        );
    }

    public List<PubTable> getAllPubTablesByBoothId(Long pubBoothId) {
        // TODO 해당 부스의 소유자인지 체크
        return pubTableRepository.findAllByPubBoothId(pubBoothId);
    }

    public List<PubTableResponseDto> getAllPubTables(Long pubBoothId) {
        List<PubTable> pubTables = getAllPubTablesByBoothId(pubBoothId);
        return pubTables.stream().map((pubTable) -> {
                    PubTableSession pubTableSession = pubTableSessionQueryService.getCurrentPubTableSession(pubTable.getId());
                    return PubTableResponseDto.fromEntity(pubTable, pubTableSession);
                }
        ).toList();
    }
}
