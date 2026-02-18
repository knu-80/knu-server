package kr.co.knuserver.application.pubTable;

import java.util.List;
import kr.co.knuserver.application.pubTableSession.PubTableSessionService;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTable.repository.PubTableRepository;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubTable.dto.PubTableRequest;
import kr.co.knuserver.presentation.pubTable.dto.PubTableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PubTableService {

    private final PubTableRepository pubTableRepository;
    private final PubTableSessionService pubTableSessionService;

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

    @Transactional
    public PubTableResponse createPubTable(PubTableRequest request) {
        validateDuplicateTable(request.tableNum(), request.pubBoothId());

        PubTable pubTable = request.toEntity();
        pubTableRepository.save(pubTable);
        return PubTableResponse.fromEntity(pubTable);
    }

    public List<PubTableResponse> getAllPubTables(Long pubBoothId) {
        List<PubTable> pubTables = getAllPubTablesByBoothId(pubBoothId);
        return pubTables.stream().map((pubTable) -> {
                    PubTableSession pubTableSession = pubTableSessionService.getCurrentPubTableSession(pubTable.getId());
                    return PubTableResponse.fromEntity(pubTable, pubTableSession);
                }
                ).toList();
    }

    @Transactional
    public PubTableResponse updatePubTable(PubTableRequest request, Long pubTableId) {
        validateDuplicateTable(request.tableNum(), request.pubBoothId());

        PubTable pubTable = getPubTableById(pubTableId);
        pubTable.updatePubTable(request);
        return PubTableResponse.fromEntity(pubTable);
    }

    @Transactional
    public void deletePubTable(Long pubTableId) {
        PubTable pubTable = getPubTableById(pubTableId);
        pubTableRepository.delete(pubTable);
    }
}
