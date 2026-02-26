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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeCommandController {

    private final NoticeCommandService noticeCommandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(
            @MemberId Long memberId,
            @RequestPart("data") @Valid NoticeCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        NoticeResponse result = noticeCommandService.createNotice(request, images, memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }

    @PatchMapping("/{notice-id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> updateNotice(
            @MemberId Long memberId,
            @PathVariable("notice-id") Long noticeId,
            @RequestBody NoticeUpdateRequest request
    ) {
        NoticeResponse result = noticeCommandService.updateNotice(noticeId, request, memberId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @DeleteMapping("/{notice-id}")
    public ResponseEntity<ApiResponse<?>> deleteNotice(
            @MemberId Long memberId,
            @PathVariable("notice-id") Long noticeId
    ) {
        noticeCommandService.deleteNotice(noticeId, memberId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
