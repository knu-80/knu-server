package kr.co.knuserver.presentation.booth.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin Booth API", description = "개발진용 가두모집 부스 관련 API")
public interface AdminBoothApiControllerDocs {

    @Operation(summary = "가두모집 부스 생성", description = "새로운 가두모집 부스를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "부스 생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothInfoResponseDto>> createBooth(
            @RequestBody BoothRegisterRequestDto request);

    @Operation(summary = "가두모집 부스 삭제", description = "특정 가두모집 부스를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "부스 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "부스를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> deleteBooth(
            @Parameter(description = "부스 ID", required = true) @PathVariable(name = "booth-id") Long boothId);
}
