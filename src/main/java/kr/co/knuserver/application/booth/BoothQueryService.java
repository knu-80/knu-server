package kr.co.knuserver.application.booth;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothImage;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BoothQueryService {

    private final BoothRepository boothRepository;
    private final BoothImageRepository boothImageRepository;

    public BoothInfoResponseDto getBooth(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));
        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();
        return BoothInfoResponseDto.fromEntity(booth, imageUrls);
    }

    public List<BoothInfoResponseDto> searchBoothsByKeyword(String keyword) {
        List<Booth> booths;

        if (keyword == null || keyword.isBlank()) {
            booths = boothRepository.findByIsActiveTrue();
        }
        else {
            booths = boothRepository.searchByKeyword(keyword);
        }

        List<Long> boothIds = booths.stream().map(Booth::getId).toList();
        Map<Long, List<String>> imageUrlsMap = boothImageRepository.findAllByBoothIdIn(boothIds).stream()
            .collect(Collectors.groupingBy(
                BoothImage::getBoothId,
                Collectors.mapping(BoothImage::getImageUrl, Collectors.toList())
            ));

        return booths.stream()
            .map(booth -> BoothInfoResponseDto.fromEntity(booth, imageUrlsMap.getOrDefault(booth.getId(), Collections.emptyList())))
            .toList();
    }
}
