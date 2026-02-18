package kr.co.knuserver.application.pubWaiting;

import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.repository.PubWaitingRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PubWaitingService {
    private final PubWaitingRepository pubWaitingRepository;

    public PubWaiting getPubWaitingById(Long pubWaitingId) {
        // TODO 해당 부스의 소유자인지 체크
        return pubWaitingRepository.findById(pubWaitingId).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND)
        );
    }
}
