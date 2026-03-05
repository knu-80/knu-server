package kr.co.knuserver.application.pubWaiting;

import java.util.List;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.repository.PubWaitingRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PubWaitingQueryService {
    private final RedisWaitingService waitingService;
    private final PubWaitingRepository pubWaitingRepository;

    public PubWaiting getPubWaitingById(Long pubWaitingId) {
        // TODO 해당 부스의 소유자인지 체크
        return pubWaitingRepository.findById(pubWaitingId).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND)
        );
    }

    public int getWaitingSize(Long pubBoothId) {
        return waitingService.getWaitingSize(pubBoothId);
    }

    public List<PubWaitingResponseDto> getWaitingList(Long pubBoothId) {
        List<Long> waitingIdList = waitingService.getAllWaitingIds(pubBoothId);
        return waitingIdList.stream().map((id) -> {
            PubWaiting pubWaiting = pubWaitingRepository.findById(id).orElseThrow(
                    () -> new BusinessException(BusinessErrorCode.PUB_WAITING_NOT_FOUND)
            );
            return PubWaitingResponseDto.fromEntity(pubWaiting);
        }).toList();
    }
}
