package kr.co.knuserver.presentation.booth;


import kr.co.knuserver.application.booth.BoothService;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booths")
@RequiredArgsConstructor
public class BoothApiController {

    private final BoothService boothService;


    // 가두모집 부스 생성
    @PostMapping
    public ResponseEntity<ApiResponse<BoothInfoResponseDto>> createBooth(
        @RequestBody BoothRegisterRequestDto request
    ) {
        BoothInfoResponseDto result = boothService.registerBooth(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(result);
    }

    // 가두모집 부스 단건 조회
    @GetMapping("/{booth-id}")
    public ResponseEntity<BoothInfoResponseDto> getBooth(
        @PathVariable(name = "booth-id") Long boothId
    ) {
        BoothInfoResponseDto result = boothService.getBooth(boothId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(result);
    }

    // 가두모집 부스 수정
    @PatchMapping("/update/{booth-id}")
    public ResponseEntity<BoothInfoResponseDto> updateBooth(
        @PathVariable(name = "booth-id") Long boothId,
        @RequestBody BoothUpdateRequestDto request
    ) {
        BoothInfoResponseDto result = boothService.updateBooth(boothId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(result);
    }

    // 가두모집 부스 삭제
    @DeleteMapping("/{booth-id}")
    public ResponseEntity<?> deleteBooth(
        @PathVariable(name = "booth-id") Long boothId
    ) {
        boothService.deleteBooth(boothId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
