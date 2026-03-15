package kr.co.knuserver.presentation.booth;

import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.application.booth.BoothCommandService;
import kr.co.knuserver.domain.member.entity.Role;
import kr.co.knuserver.global.auth.RequireRole;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.booth.docs.AdminBoothApiControllerDocs;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/v1/booths")
@RequiredArgsConstructor
@RequireRole(Role.ADMIN)
public class AdminBoothApiController implements AdminBoothApiControllerDocs {

    private final BoothCommandService boothCommandService;

    // 가두모집 부스 생성
    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> createBooth(
        @RequestPart(value = "data") @Valid BoothRegisterRequestDto request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        BoothInfoResponseDto result = boothCommandService.registerBooth(request, images);
        return ResponseEntity.status(HttpStatus.CREATED)
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


    // 가두모집 부스 삭제
    @Override
    @DeleteMapping("/{booth-id}")
    public ResponseEntity<ApiResponse<?>> deleteBooth(
        @PathVariable(name = "booth-id") Long boothId
    ) {
        boothCommandService.deleteBooth(boothId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success());
    }

}
