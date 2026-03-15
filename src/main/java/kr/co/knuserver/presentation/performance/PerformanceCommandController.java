package kr.co.knuserver.presentation.performance;

import jakarta.validation.Valid;
import kr.co.knuserver.application.performance.PerformanceCommandService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.performance.docs.PerformanceCommandControllerDocs;
import kr.co.knuserver.presentation.performance.dto.PerformanceCreateRequestDto;
import kr.co.knuserver.presentation.performance.dto.PerformanceUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/performances")
@RequiredArgsConstructor
public class PerformanceCommandController implements PerformanceCommandControllerDocs {

    private final PerformanceCommandService performanceCommandService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerPerformance(
        @Valid @RequestBody PerformanceCreateRequestDto request
    ) {
        performanceCommandService.registerPerformance(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success());
    }

    @Override
    @PatchMapping("/{performance-id}")
    public ResponseEntity<ApiResponse<?>> updatePerformance(
        @PathVariable(name = "performance-id") Long performanceId,
        @RequestBody PerformanceUpdateRequestDto request
    ) {
        performanceCommandService.updatePerformance(performanceId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success());
    }

    @Override
    @DeleteMapping("/{performance-id}")
    public ResponseEntity<ApiResponse<?>> deletePerformance(
        @PathVariable(name = "performance-id") Long performanceId
    ) {
        performanceCommandService.deletePerformance(performanceId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success());
    }
}
