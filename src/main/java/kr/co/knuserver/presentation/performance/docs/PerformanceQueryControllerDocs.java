package kr.co.knuserver.presentation.performance.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kr.co.knuserver.presentation.performance.dto.PerformanceResponseDto;
import org.springframework.http.ResponseEntity;

@Tag(name = "Performance API", description = "일반 사용자용 공연 타임테이블 API")
public interface PerformanceQueryControllerDocs {

    @Operation(summary = "공연 목록 조회", description = "활성 부스의 전체 공연 타임테이블을 startAt 오름차순으로 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공연 목록 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<PerformanceResponseDto>>> getPerformances();
}
