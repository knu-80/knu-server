package kr.co.knuserver.presentation.notice.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.knuserver.presentation.notice.dto.response.NoticeDetailResponse;
import kr.co.knuserver.presentation.notice.dto.response.NoticeListResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "공지사항 Query", description = "공지사항 목록/단건 조회 API")
public interface NoticeQueryControllerDocs {

    @Operation(summary = "공지사항 목록 조회", description = "모든 공지사항 목록을 최신순으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<NoticeListResponse>>> getNotices();

    @Operation(summary = "공지사항 단건 조회", description = "공지사항 ID로 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "공지 없음")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<NoticeDetailResponse>> getNotice(
            @Parameter(description = "공지사항 ID", required = true)
            Long noticeId
    );
}
