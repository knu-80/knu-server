package kr.co.knuserver.application.pubTableSession;

import kr.co.knuserver.application.pubTable.PubTableQueryService;
import kr.co.knuserver.application.pubWaiting.PubWaitingCommandService;
import kr.co.knuserver.application.pubWaiting.PubWaitingQueryService;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.domain.pubTableSession.repository.PubTableSessionRepository;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaitingStatus;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionEndRequestDto;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionStartRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PubTableSessionCommandService {

    private final PubTableSessionRepository pubTableSessionRepository;
    private final PubTableSessionQueryService pubTableSessionQueryService;
    private final PubTableQueryService pubTableQueryService;
    private final PubWaitingQueryService pubWaitingQueryService;
    private final PubWaitingCommandService pubWaitingCommandService;

    public void startSession(PubTableSessionStartRequestDto request) {
        PubTable pubTable = pubTableQueryService.getPubTableById(request.pubTableId());

        PubTableSession currentSession = pubTableSessionQueryService.getCurrentPubTableSession(pubTable.getId());
        if (currentSession != null) {
            throw new BusinessException(BusinessErrorCode.PUB_SESSION_ALREADY_EXISTS);
        }

        PubWaiting pubWaiting = pubWaitingQueryService.getPubWaitingById(request.pubWaitingId());

        PubWaitingStatus waitingStatus = pubWaiting.getStatus();
        if (waitingStatus != PubWaitingStatus.WAITING && waitingStatus != PubWaitingStatus.CALL) {
            throw new BusinessException(BusinessErrorCode.NOT_IN_WAITING_PROCESS);
        }

        PubTableSession pubTableSession = PubTableSession.createPubTableSession(pubWaiting.getGuestCount(), pubTable.getId());
        pubTableSessionRepository.save(pubTableSession);
        pubWaitingCommandService.enter(request.pubWaitingId());
    }

    public void endSession(PubTableSessionEndRequestDto request) {
        PubTableSession pubTableSession = pubTableSessionQueryService.getPubTableSessionById(request.pubTableSessionId());
        pubTableSession.guestExit();
    }
}
