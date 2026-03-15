package kr.co.knuserver.application.performance;

import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.domain.performance.entity.Performance;
import kr.co.knuserver.domain.performance.repository.PerformanceRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.performance.dto.PerformanceCreateRequestDto;
import kr.co.knuserver.presentation.performance.dto.PerformanceUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerformanceCommandService {

    private final PerformanceRepository performanceRepository;
    private final BoothRepository boothRepository;

    @Transactional
    public void registerPerformance(PerformanceCreateRequestDto request) {
        boothRepository.findById(request.boothId())
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));

        Performance performance = Performance.create(request.boothId(), request.session(), request.startAt(), request.endAt());
        performanceRepository.save(performance);
    }

    @Transactional
    public void updatePerformance(Long id, PerformanceUpdateRequestDto request) {
        Performance performance = performanceRepository.findById(id)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.PERFORMANCE_NOT_FOUND));

        performance.update(request.startAt(), request.endAt());
    }

    @Transactional
    public void deletePerformance(Long id) {
        Performance performance = performanceRepository.findById(id)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.PERFORMANCE_NOT_FOUND));

        performanceRepository.delete(performance);
    }
}
