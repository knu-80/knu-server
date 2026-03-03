package kr.co.knuserver.presentation.auth.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.knuserver.global.auth.MemberId;
import kr.co.knuserver.presentation.auth.dto.AdminMeResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Admin Auth API", description = "관리자 인증 관련 API")
public interface AdminMeControllerDocs {

    @Operation(summary = "관리자 내 정보 조회", description = "JWT 토큰을 검증하고 관리자 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰 또는 미인증 사용자")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<AdminMeResponse>> me(
            @MemberId Long memberId
    );

}
