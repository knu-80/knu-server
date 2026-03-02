package kr.co.knuserver.presentation.auth.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.knuserver.presentation.auth.dto.AdminLoginRequest;
import kr.co.knuserver.presentation.auth.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin Auth API", description = "관리자 인증 관련 API")
public interface AdminAuthControllerDocs {

    @Operation(summary = "관리자 로그인", description = "관리자 아이디와 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 유효성 검증 실패"),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않음")
    })
    ResponseEntity<TokenResponse> login(
            @Valid @RequestBody AdminLoginRequest request
    );

}
