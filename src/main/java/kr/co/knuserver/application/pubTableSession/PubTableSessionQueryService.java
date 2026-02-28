package kr.co.knuserver.application.pubTableSession;

import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.domain.pubTableSession.repository.PubTableSessionRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubTableSessionQueryService {

    private final PubTableSessionRepository pubTableSessionRepository;

    public PubTableSession getPubTableSessionById(Long pubTableSessionId) {
        // TODO 해당 부스의 소유자인지 체크
        return pubTableSessionRepository.findById(pubTableSessionId).orElseThrow(
                ()  -> new BusinessException(BusinessErrorCode.PUB_TABLE_SESSION_NOT_FOUND)
        );
    }

    public PubTableSession getCurrentPubTableSession(Long pubTableId) {
        return pubTableSessionRepository.findByPubTableIdAndExitTimeIsNull(pubTableId);
    }
}
