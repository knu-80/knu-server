package kr.co.knuserver.presentation.notice.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.knuserver.presentation.notice.dto.request.NoticeCreateRequest;
import kr.co.knuserver.presentation.notice.dto.request.NoticeUpdateRequest;
import kr.co.knuserver.presentation.notice.dto.response.NoticeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "공지사항 Command", description = "공지사항 등록/수정/삭제 API")
public interface NoticeCommandControllerDocs {

    @Operation(summary = "공지사항 등록", description = "공지사항을 등록합니다. LOST_FOUND 타입일 경우 lostFoundDetail이 필수입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "필수 필드 누락 또는 잘못된 타입 값"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<NoticeResponse>> createNotice(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "공지사항 등록 요청 데이터 (JSON)", required = true)
            NoticeCreateRequest request,
            @Parameter(description = "첨부 이미지 목록 (선택)")
            List<MultipartFile> images
    );

    @Operation(summary = "공지사항 수정", description = "공지사항의 텍스트 필드(제목, 내용 등)를 수정합니다. 이미지 변경은 이미지 수정 API를 사용하세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "GENERAL 타입 공지에 lostFoundDetail 포함, LOST_FOUND 타입 공지에 lostFoundDetail 누락 또는 필드 미입력"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "작성자가 아님"),
            @ApiResponse(responseCode = "404", description = "공지 없음")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<NoticeResponse>> updateNotice(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "공지사항 ID", required = true)
            Long noticeId,
            @Parameter(description = "공지사항 수정 요청 데이터 (JSON)", required = true)
            NoticeUpdateRequest request
    );

    @Operation(summary = "공지사항 이미지 수정", description = "공지사항의 이미지를 교체합니다. 기존 이미지는 전부 삭제되고 전송한 이미지로 교체됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이미지 수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "작성자가 아님"),
            @ApiResponse(responseCode = "404", description = "공지 없음")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<NoticeResponse>> updateNoticeImages(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "공지사항 ID", required = true)
            Long noticeId,
            @Parameter(description = "교체할 이미지 목록 (선택, 미전송 시 이미지 전체 삭제)")
            List<MultipartFile> images
    );

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "작성자가 아님"),
            @ApiResponse(responseCode = "404", description = "공지 없음")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> deleteNotice(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "공지사항 ID", required = true)
            Long noticeId
    );
}
