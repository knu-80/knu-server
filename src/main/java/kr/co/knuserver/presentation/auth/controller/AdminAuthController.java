package kr.co.knuserver.presentation.auth.controller;

import jakarta.validation.Valid;
import kr.co.knuserver.application.auth.AdminAuthService;
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
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AdminLoginRequest request) {
        TokenResponse response = adminAuthService.loginWithPin(request.pin());
        return ResponseEntity.ok(response);
    }
}
