package kr.co.knuserver.application.performance;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.domain.performance.entity.Performance;
import kr.co.knuserver.domain.performance.repository.PerformanceRepository;
import kr.co.knuserver.presentation.performance.dto.PerformanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PerformanceQueryService {

    private final PerformanceRepository performanceRepository;
    private final BoothRepository boothRepository;

    public List<PerformanceResponseDto> getPerformances() {
        List<Performance> performances = performanceRepository.findAllWithActiveBooth();

        Set<Long> boothIds = performances.stream()
            .map(Performance::getBoothId)
            .collect(Collectors.toSet());

        Map<Long, Booth> boothMap = boothRepository.findAllById(boothIds).stream()
            .collect(Collectors.toMap(Booth::getId, Function.identity()));

        return performances.stream()
            .map(performance -> PerformanceResponseDto.of(performance, boothMap.get(performance.getBoothId())))
            .toList();
    }
}
