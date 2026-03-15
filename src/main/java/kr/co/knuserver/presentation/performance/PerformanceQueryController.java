package kr.co.knuserver.presentation.performance;

import java.util.List;
import kr.co.knuserver.application.performance.PerformanceQueryService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.performance.docs.PerformanceQueryControllerDocs;
import kr.co.knuserver.presentation.performance.dto.PerformanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/performances")
@RequiredArgsConstructor
public class PerformanceQueryController implements PerformanceQueryControllerDocs {

    private final PerformanceQueryService performanceQueryService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<PerformanceResponseDto>>> getPerformances() {
        List<PerformanceResponseDto> result = performanceQueryService.getPerformances();
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }
}
