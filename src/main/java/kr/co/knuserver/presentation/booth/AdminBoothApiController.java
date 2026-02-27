package kr.co.knuserver.presentation.booth;

import jakarta.validation.Valid;
import kr.co.knuserver.application.booth.BoothCommandService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.booth.docs.AdminBoothApiControllerDocs;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/booths")
@RequiredArgsConstructor
public class AdminBoothApiController implements AdminBoothApiControllerDocs {

    private final BoothCommandService boothCommandService;

    // 가두모집 부스 생성
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> createBooth(
        @Valid @RequestBody BoothRegisterRequestDto request
    ) {
        BoothInfoResponseDto result = boothCommandService.registerBooth(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(result));
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
