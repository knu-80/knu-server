package kr.co.knuserver.application.pubWaiting;

import kr.co.knuserver.application.pubBooth.PubBoothQueryService;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.repository.PubWaitingRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PubWaitingCommandService {
    private final ConcurrentMapWaitingService waitingService;
    private final PubWaitingRepository pubWaitingRepository;
    private final PubBoothQueryService pubBoothQueryService;

    public void register(PubWaitingRegisterRequestDto request) {
        pubBoothQueryService.findPubBoothById(request.pubBoothId());
        PubWaiting pubWaiting = PubWaiting.createPubWaiting(
                request.phone(), request.guestCount(), request.memberId(), request.pubBoothId()
        );

        Long pubWaitingId = pubWaitingRepository.saveAndFlush(pubWaiting).getId();
        waitingService.register(request.pubBoothId(), request.memberId(), pubWaitingId);
    }

    public boolean cancel(Long pubWaitingId) {
        PubWaiting pubWaiting = pubWaitingRepository.findById(pubWaitingId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND));

        pubWaiting.cancel();
        return waitingService.cancel(pubWaiting.getPubBoothId(), pubWaiting.getMemberId(), pubWaitingId);
    }

    public void enter(Long pubWaitingId) {
        PubWaiting pubWaiting = pubWaitingRepository.findById(pubWaitingId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND));

        pubWaiting.enter();
        waitingService.cancel(pubWaiting.getPubBoothId(), pubWaiting.getMemberId(), pubWaitingId);
    }
}

