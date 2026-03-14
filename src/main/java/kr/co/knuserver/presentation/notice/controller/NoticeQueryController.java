package kr.co.knuserver.presentation.notice.controller;

import kr.co.knuserver.application.notice.NoticeQueryService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.notice.dto.response.NoticeDetailResponse;
import kr.co.knuserver.presentation.notice.dto.response.NoticeListResponse;
import kr.co.knuserver.presentation.notice.controller.docs.NoticeQueryControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeQueryController implements NoticeQueryControllerDocs {

    private final NoticeQueryService noticeQueryService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeListResponse>>> getNotices() {
        List<NoticeListResponse> result = noticeQueryService.getNotices();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Override
    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<NoticeListResponse>>> getRecentNotices() {
        List<NoticeListResponse> result = noticeQueryService.getRecentNotices();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Override
    @GetMapping("/{notice-id}")
    public ResponseEntity<ApiResponse<NoticeDetailResponse>> getNotice(
            @PathVariable("notice-id") Long noticeId
    ) {
        NoticeDetailResponse result = noticeQueryService.getNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
