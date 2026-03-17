package kr.co.knuserver.presentation.booth;

import java.util.List;
import kr.co.knuserver.application.booth.BoothQueryService;
import kr.co.knuserver.global.dto.CursorPaginationResponse;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.booth.docs.BoothApiControllerDocs;
import kr.co.knuserver.presentation.booth.dto.BoothCountResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothListResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRankingResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothTop3ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booths")
@RequiredArgsConstructor
public class BoothApiController implements BoothApiControllerDocs {

    private final BoothQueryService boothQueryService;

    // 가두모집 부스 개수 조회
    @Override
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<BoothCountResponseDto>> getBoothCount() {
        BoothCountResponseDto result = boothQueryService.getBoothCount();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 가두모집 부스 단건 조회
    @Override
    @GetMapping("/{booth-id}")
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> getBooth(
        @PathVariable(name = "booth-id") Long boothId
    ) {
        BoothInfoResponseDto result = boothQueryService.getBooth(boothId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 키워드 단위 가두모집 부스 리스트 조회
    @Override
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<BoothInfoResponseDto>>> searchBooths(
        @RequestParam(name = "keyword", required = false) String keyword
    ) {
        List<BoothInfoResponseDto> result = boothQueryService.searchBoothsByKeyword1(keyword);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 키워드 단위 가두모집 부스 리스트 조회
    @Override
    @GetMapping("/list/pagination")
    public ResponseEntity<ApiResponse<CursorPaginationResponse<BoothListResponseDto>>> searchBooths(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "lastId", required = false) Long lastId,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        CursorPaginationResponse<BoothListResponseDto> result = boothQueryService.searchBoothsByKeyword2(keyword, lastId, size);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    @Override
    @GetMapping("/ranking")
    public ResponseEntity<ApiResponse<List<BoothRankingResponseDto>>> getBoothRanking() {
        List<BoothRankingResponseDto> result = boothQueryService.getBoothRanking();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Override
    @GetMapping("/ranking/top3")
    public ResponseEntity<ApiResponse<List<BoothTop3ResponseDto>>> getTop3BoothRanking() {
        List<BoothTop3ResponseDto> result = boothQueryService.getTop3BoothRanking();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/ranking/daily")
    public ResponseEntity<ApiResponse<List<BoothRankingResponseDto>>> getTodayDailyRanking() {
        String today = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();
        List<BoothRankingResponseDto> result = boothQueryService.getDailyRanking(today);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/ranking/daily/{date}")
    public ResponseEntity<ApiResponse<List<BoothRankingResponseDto>>> getDailyRanking(
        @PathVariable(name = "date") String date
    ) {
        List<BoothRankingResponseDto> result = boothQueryService.getDailyRanking(date);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
