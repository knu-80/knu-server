package kr.co.knuserver.presentation.notice.controller;

import jakarta.validation.Valid;
import kr.co.knuserver.application.notice.NoticeCommandService;
import kr.co.knuserver.global.auth.MemberId;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.notice.dto.NoticeCreateRequest;
import kr.co.knuserver.presentation.notice.dto.NoticeResponse;
import kr.co.knuserver.presentation.notice.dto.NoticeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeCommandController {

    private final NoticeCommandService noticeCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(
            @MemberId Long memberId,
            @Valid @RequestBody NoticeCreateRequest request
    ) {
        NoticeResponse result = noticeCommandService.createNotice(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }

    @PatchMapping("/{notice-id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> updateNotice(
            @MemberId Long memberId,
            @PathVariable("notice-id") Long noticeId,
            @RequestBody NoticeUpdateRequest request
    ) {
        NoticeResponse result = noticeCommandService.updateNotice(noticeId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @DeleteMapping("/{notice-id}")
    public ResponseEntity<ApiResponse<?>> deleteNotice(
            @MemberId Long memberId,
            @PathVariable("notice-id") Long noticeId
    ) {
        noticeCommandService.deleteNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
