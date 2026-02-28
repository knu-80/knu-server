package kr.co.knuserver.application.pubTable;

import java.util.List;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTable.repository.PubTableRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubTableQueryService {

    private final PubTableRepository pubTableRepository;

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
}
