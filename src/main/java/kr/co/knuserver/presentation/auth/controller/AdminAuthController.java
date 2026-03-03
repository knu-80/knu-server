package kr.co.knuserver.presentation.auth.controller;

import jakarta.validation.Valid;
import kr.co.knuserver.application.auth.AdminAuthService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.auth.controller.docs.AdminAuthControllerDocs;
import kr.co.knuserver.presentation.auth.dto.AdminLoginRequest;
import kr.co.knuserver.presentation.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminAuthController implements AdminAuthControllerDocs {

    private final AdminAuthService adminAuthService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody @Valid AdminLoginRequest request) {
        TokenResponse response = adminAuthService.login(request.loginId(), request.password());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
