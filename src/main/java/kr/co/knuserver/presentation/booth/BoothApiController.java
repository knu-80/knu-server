package kr.co.knuserver.presentation.booth;


import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.application.booth.BoothCommandService;
import kr.co.knuserver.application.booth.BoothQueryService;
import kr.co.knuserver.global.dto.CursorPaginationResponse;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.booth.docs.BoothApiControllerDocs;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothListResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<CursorPaginationResponse<BoothListResponseDto>>> searchBooths(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "lastId", required = false) Long lastId,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        CursorPaginationResponse<BoothListResponseDto> result = boothQueryService.searchBoothsByKeyword(keyword, lastId, size);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 부스 정보(이미지 외 필드)만 수정
    @Override
    @PatchMapping("/{booth-id}")
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> updateBooth(
        @PathVariable(name = "booth-id") Long boothId,
        @Valid @RequestBody BoothUpdateRequestDto request
    ) {
        BoothInfoResponseDto result = boothCommandService.updateBooth(boothId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 이미지만 수정
    @Override
    @PostMapping(value = "/{booth-id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> updateBoothImages(
        @PathVariable(name = "booth-id") Long boothId,
        @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        BoothInfoResponseDto result = boothCommandService.updateBoothImages(boothId, images);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
