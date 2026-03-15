package kr.co.knuserver.presentation.performance.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.knuserver.presentation.performance.dto.PerformanceCreateRequestDto;
import kr.co.knuserver.presentation.performance.dto.PerformanceUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin Performance API", description = "어드민용 공연 관리 API")
public interface PerformanceCommandControllerDocs {

    @Operation(summary = "공연 등록", description = "새로운 공연을 등록합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "공연 등록 성공"),
        @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
        @ApiResponse(responseCode = "404", description = "부스를 찾을 수 없습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> registerPerformance(
        @Valid @RequestBody PerformanceCreateRequestDto request);

    @Operation(summary = "공연 수정", description = "공연 시간 정보를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공연 수정 성공"),
        @ApiResponse(responseCode = "404", description = "공연 정보를 찾을 수 없습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> updatePerformance(
        @Parameter(description = "공연 ID", required = true) @PathVariable(name = "performance-id") Long performanceId,
        @RequestBody PerformanceUpdateRequestDto request);

    @Operation(summary = "공연 삭제", description = "공연을 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공연 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "공연 정보를 찾을 수 없습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> deletePerformance(
        @Parameter(description = "공연 ID", required = true) @PathVariable(name = "performance-id") Long performanceId);
}
