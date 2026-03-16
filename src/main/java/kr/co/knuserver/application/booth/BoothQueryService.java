package kr.co.knuserver.application.booth;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothImage;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.global.dto.CursorPaginationResponse;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.booth.dto.BoothCountResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothListResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRankingResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothTop3ResponseDto;
import org.springframework.data.redis.core.ZSetOperations;
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
    private final BoothLikeService boothLikeService;

    public BoothCountResponseDto getBoothCount() {
        Integer count = boothRepository.countByIsActiveTrue();
        return BoothCountResponseDto.from(count);
    }

    public BoothInfoResponseDto getBooth(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));
        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();
        long likeCount = boothLikeService.getLikeCount(boothId);
        return BoothInfoResponseDto.fromEntity(booth, imageUrls, likeCount);
    }


    public List<BoothInfoResponseDto> searchBoothsByKeyword1(String keyword) {
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

    public List<BoothRankingResponseDto> getBoothRanking() {
        Set<ZSetOperations.TypedTuple<String>> rawRanking = boothLikeService.getRanking();
        if (rawRanking == null || rawRanking.isEmpty()) {
            return List.of();
        }

        BoothRanking boothRanking = new BoothRanking(rawRanking);
        if (boothRanking.isEmpty()) {
            return List.of();
        }

        List<Booth> booths = boothRepository.findAllById(boothRanking.boothIds());

        return booths.stream()
            .map(booth -> new BoothRankingResponseDto(
                booth.getId(),
                booth.getName(),
                boothRanking.getLikeCount(booth.getId())
            ))
            .sorted(Comparator.comparingLong(BoothRankingResponseDto::likeCount).reversed()
                .thenComparingLong(BoothRankingResponseDto::boothId))
            .toList();
    }

    public List<BoothTop3ResponseDto> getTop3BoothRanking() {
        Set<ZSetOperations.TypedTuple<String>> rawRanking = boothLikeService.getTopRanking(10);
        if (rawRanking == null || rawRanking.isEmpty()) {
            return List.of();
        }

        BoothRanking boothRanking = new BoothRanking(rawRanking);
        if (boothRanking.isEmpty()) {
            return List.of();
        }

        List<Booth> booths = boothRepository.findAllById(boothRanking.boothIds());

        return booths.stream()
            .map(booth -> new BoothTop3ResponseDto(
                booth.getId(),
                booth.getName(),
                boothRanking.getLikeCount(booth.getId())
            ))
            .sorted(Comparator.comparingLong(BoothTop3ResponseDto::likeCount).reversed()
                .thenComparingLong(BoothTop3ResponseDto::boothId))
            .limit(3)
            .toList();
    }

    public CursorPaginationResponse<BoothListResponseDto> searchBoothsByKeyword2(String keyword, Long lastId, int size) {
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
                String firstImageUrl = urls.isEmpty() ? null : urls.getFirst();
                return BoothListResponseDto.fromEntity(booth, firstImageUrl);
            })
            .toList();

        Long nextCursor = items.isEmpty() ? null : items.getLast().id();

        return CursorPaginationResponse.of(items, nextCursor, hasNext);
    }
}

