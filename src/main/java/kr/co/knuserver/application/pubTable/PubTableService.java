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
@org.springframework.transaction.annotation.Transactional
public class PubTableService {

    private final PubTableRepository pubTableRepository;
    private final PubTableSessionService pubTableSessionService;

    @Transactional
    public PubTableResponse createPubTable(PubTableRequest request) {
        // TODO PubBooth가 존재하는지 확인 및 관리자인지 확인
        if (pubTableRepository.existsByTableNumAndPubBoothId(request.tableNum(), request.pubBoothId())) {
            throw new BusinessException(BusinessErrorCode.ALREADY_EXISTS);
        }
        PubTable pubTable = request.toEntity();
        pubTableRepository.save(pubTable);
        return PubTableResponse.fromEntity(pubTable);
    }

    public List<PubTableResponse> getAllPubTables(Long pubBoothId) {
        // TODO PubBooth가 존재하는지 확인 및 관리자인지 확인
        List<PubTable> pubTables = pubTableRepository.findAllByPubBoothId(pubBoothId);
        return pubTables.stream().map((pubTable) -> {
                    PubTableSession pubTableSession = pubTableSessionService.getCurrentPubTableSession(pubTable.getId());
                    return PubTableResponse.fromEntity(pubTable, pubTableSession);
                }
                ).toList();
    }

    @Transactional
    public PubTableResponse updatePubTable(PubTableRequest request, Long pubTableId) {
        PubTable pubTable = pubTableRepository.findById(pubTableId).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_TABLE_NOT_FOUND)
        );
        // TODO PubBooth의 관리자인지 확인
        pubTable.updatePubTable(request);
        return PubTableResponse.fromEntity(pubTable);
    }

    @Transactional
    public void deletePubTable(Long pubTableId) {
        PubTable pubTable = pubTableRepository.findById(pubTableId).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_TABLE_NOT_FOUND)
        );
        // TODO PubBooth의 관리자인지 확인
        pubTableRepository.delete(pubTable);
    }
}
