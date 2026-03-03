package kr.co.knuserver.presentation.auth.controller;

import kr.co.knuserver.application.auth.AdminAuthService;
import kr.co.knuserver.global.auth.MemberId;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.auth.controller.docs.AdminMeControllerDocs;
import kr.co.knuserver.presentation.auth.dto.AdminMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1")
public class AdminMeController implements AdminMeControllerDocs {

    private final AdminAuthService adminAuthService;

    @Override
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdminMeResponse>> me(@MemberId Long memberId) {
        AdminMeResponse response = adminAuthService.getMe(memberId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
