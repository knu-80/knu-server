package kr.co.knuserver.presentation.auth.controller;

import jakarta.validation.Valid;
import kr.co.knuserver.application.auth.AdminAuthService;
import kr.co.knuserver.global.auth.MemberId;
import kr.co.knuserver.presentation.auth.dto.AdminLoginRequest;
import kr.co.knuserver.presentation.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AdminLoginRequest request) {
        TokenResponse response = adminAuthService.login(request.loginId(), request.password());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Long>> me(@MemberId Long memberId) {
        return ResponseEntity.ok(Map.of("memberId", memberId));
    }
}
