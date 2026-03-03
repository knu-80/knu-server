package kr.co.knuserver.application.pubWaiting;

import kr.co.knuserver.application.pubBooth.PubBoothQueryService;
import kr.co.knuserver.application.pubWaiting.concurrent.ConcurrentMapWaitingService;
import kr.co.knuserver.application.pubWaiting.redis.RedisWaitingService;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.repository.PubWaitingRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingCancelRequestDto;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PubWaitingCommandService {
    private final ConcurrentMapWaitingService waitingService;
//    private final RedisWaitingService waitingService;
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

    public boolean cancel(PubWaitingCancelRequestDto request) {
        PubWaiting pubWaiting = pubWaitingRepository.findById(request.pubWaitingId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND));

        pubWaiting.cancel();
        return waitingService.cancel(request.pubBoothId(), request.memberId(), request.pubWaitingId());
    }
}

