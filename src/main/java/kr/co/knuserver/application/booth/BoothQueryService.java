package kr.co.knuserver.application.booth;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothImage;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BoothQueryService {

    private final BoothReader boothReader;
    private final BoothRepository boothRepository;
    private final BoothImageRepository boothImageRepository;

    // 부스 조회
    public BoothInfoResponseDto getBooth(Long boothId) {
        Booth booth = boothReader.getBoothOrThrow(boothId);
        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();
        return BoothInfoResponseDto.fromEntity(booth, imageUrls);
    }

    // 주어진 키워드를 바탕으로 부스 리스트 조회
    public List<BoothInfoResponseDto> searchBoothsByKeyword(String keyword) {
        List<Booth> booths;

        // 키워드가 없거나 공백이면 전체 목록 반환
        if (keyword == null || keyword.isBlank()) {
            booths = boothRepository.findByIsActiveTrue();
        }
        // 키워드가 있으면 해당 키워드로 검색
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
