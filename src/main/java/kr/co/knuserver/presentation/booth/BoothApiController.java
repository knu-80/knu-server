package kr.co.knuserver.presentation.booth;


import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.application.booth.BoothCommandService;
import kr.co.knuserver.application.booth.BoothQueryService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.booth.docs.BoothApiControllerDocs;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booths")
@RequiredArgsConstructor
public class BoothApiController implements BoothApiControllerDocs {

    private final BoothQueryService boothQueryService;
    private final BoothCommandService boothCommandService;

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
        List<BoothInfoResponseDto> result = boothQueryService.searchBoothsByKeyword(keyword);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 가두모집 부스 수정
    @Override
    @PatchMapping("/update/{booth-id}")
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> updateBooth(
        @PathVariable(name = "booth-id") Long boothId,
        @Valid @RequestBody BoothUpdateRequestDto request
    ) {
        BoothInfoResponseDto result = boothCommandService.updateBooth(boothId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

}
