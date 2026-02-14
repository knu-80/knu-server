package kr.co.knuserver.application.pubTableSession;

import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTable.repository.PubTableRepository;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.domain.pubTableSession.repository.PubTableSessionRepository;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaitingStatus;
import kr.co.knuserver.domain.pubWaiting.repository.PubWaitingRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionEndRequest;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionStartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PubTableSessionService {

    private final PubTableSessionRepository pubTableSessionRepository;
    private final PubTableRepository pubTableRepository;
    private final PubWaitingRepository pubWaitingRepository;

    public void startSession(PubTableSessionStartRequest request) {
        PubTable pubTable = pubTableRepository.findById(request.pubTableId()).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_TABLE_NOT_FOUND)
        );
        PubWaiting pubWaiting = pubWaitingRepository.findById(request.pubWaitingId()).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND)
        );

        PubWaitingStatus waitingStatus = pubWaiting.getStatus();

        if (waitingStatus != PubWaitingStatus.WAITING && waitingStatus != PubWaitingStatus.CALL) {
            throw new BusinessException(BusinessErrorCode.NOT_IN_WAITING_PROCESS);
        }

        PubTableSession pubTableSession = PubTableSession.createPubTableSession(pubWaiting.getGuestCount(), pubTable.getId());
        pubTableSessionRepository.save(pubTableSession);
        pubWaiting.entered();
    }

    public void endSession(PubTableSessionEndRequest request) {
        PubTableSession pubTableSession = pubTableSessionRepository.findById(request.pubTableSessionId()).orElseThrow(
                ()  -> new BusinessException(BusinessErrorCode.PUB_TABLE_SESSION_NOT_FOUND)
        );
        pubTableSession.guestExit();
    }

    public PubTableSession getCurrentPubTableSession(Long pubTableId) {
        return pubTableSessionRepository.findByPubTableIdAndExitTimeIsNull(pubTableId);
    }
}
