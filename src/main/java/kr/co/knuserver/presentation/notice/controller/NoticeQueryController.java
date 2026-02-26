package kr.co.knuserver.presentation.notice.controller;

import kr.co.knuserver.application.notice.NoticeQueryService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.notice.dto.NoticeDetailResponse;
import kr.co.knuserver.presentation.notice.dto.NoticeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeQueryController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeListResponse>>> getNotices(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<NoticeListResponse> result = noticeQueryService.getNotices(lastId, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{notice-id}")
    public ResponseEntity<ApiResponse<NoticeDetailResponse>> getNotice(
            @PathVariable("notice-id") Long noticeId
    ) {
        NoticeDetailResponse result = noticeQueryService.getNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
