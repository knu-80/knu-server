package kr.co.knuserver.application.booth;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothImage;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.global.dto.CursorPaginationResponse;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  
    // 주어진 키워드를 바탕으로 부스 리스트 조회 (커서 기반 페이지네이션)
    public CursorPaginationResponse<BoothListResponseDto> searchBoothsByKeyword(String keyword, Long lastId, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Booth> booths;

        if (lastId == null) {
            lastId = 0L;
        }
  
        if (keyword == null || keyword.isBlank()) {
            booths = boothRepository.findByIsActiveTrueAndIdGreaterThanOrderByIdAsc(lastId, pageable);
        }
        else {
            booths = boothRepository.searchByKeywordWithCursor(keyword, lastId, pageable);
        }

        boolean hasNext = booths.size() > size;
        List<Booth> pagedBooths = hasNext ? booths.subList(0, size) : booths;

        List<Long> boothIds = pagedBooths.stream().map(Booth::getId).toList();
        Map<Long, List<String>> imageUrlsMap = boothImageRepository.findAllByBoothIdIn(boothIds).stream()
            .collect(Collectors.groupingBy(
                BoothImage::getBoothId,
                Collectors.mapping(BoothImage::getImageUrl, Collectors.toList())
            ));

        List<BoothListResponseDto> items = pagedBooths.stream()
            .map(booth -> {
                List<String> urls = imageUrlsMap.getOrDefault(booth.getId(), Collections.emptyList());
                String firstImageUrl = urls.isEmpty() ? null : urls.get(0);
                return BoothListResponseDto.fromEntity(booth, firstImageUrl);
            })
            .toList();

        Long nextCursor = items.isEmpty() ? null : items.get(items.size() - 1).id();

        return CursorPaginationResponse.of(items, nextCursor, hasNext);
    }
}

