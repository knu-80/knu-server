package kr.co.knuserver.application.pubTableSession;

import kr.co.knuserver.application.pubTable.PubTableService;
import kr.co.knuserver.application.pubWaiting.PubWaitingService;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTable.repository.PubTableRepository;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.domain.pubTableSession.repository.PubTableSessionRepository;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaitingStatus;
import kr.co.knuserver.domain.pubWaiting.repository.PubWaitingRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionEndRequestDto;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionStartRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubTableSessionService {

    private final PubTableSessionRepository pubTableSessionRepository;
    private final PubTableService pubTableService;
    private final PubWaitingService pubWaitingService;

    public PubTableSession getPubTableSessionById(Long pubTableSessionId) {
        // TODO 해당 부스의 소유자인지 체크
        return pubTableSessionRepository.findById(pubTableSessionId).orElseThrow(
                ()  -> new BusinessException(BusinessErrorCode.PUB_TABLE_SESSION_NOT_FOUND)
        );
    }

    public PubTableSession getCurrentPubTableSession(Long pubTableId) {
        return pubTableSessionRepository.findByPubTableIdAndExitTimeIsNull(pubTableId);
    }

    @Transactional
    public void startSession(PubTableSessionStartRequestDto request) {
        PubTable pubTable = pubTableService.getPubTableById(request.pubTableId());
        PubWaiting pubWaiting = pubWaitingService.getPubWaitingById(request.pubWaitingId());

        PubWaitingStatus waitingStatus = pubWaiting.getStatus();
        if (waitingStatus != PubWaitingStatus.WAITING && waitingStatus != PubWaitingStatus.CALL) {
            throw new BusinessException(BusinessErrorCode.NOT_IN_WAITING_PROCESS);
        }

        PubTableSession pubTableSession = PubTableSession.createPubTableSession(pubWaiting.getGuestCount(), pubTable.getId());
        pubTableSessionRepository.save(pubTableSession);
        pubWaiting.entered();
    }

    @Transactional
    public void endSession(PubTableSessionEndRequestDto request) {
        PubTableSession pubTableSession = getPubTableSessionById(request.pubTableSessionId());
        pubTableSession.guestExit();
    }
}
