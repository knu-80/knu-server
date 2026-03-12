package kr.co.knuserver.presentation.event.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Admin Event API", description = "개발진용 이벤트 관련 API")
public interface AdminEventApiControllerDocs {

    @Operation(summary = "이벤트 생성", description = "새로운 이벤트를 생성합니다. (multipart/form-data)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이벤트 생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<EventResponseDto>> registerEvent(
            @Parameter(description = "이벤트 생성 정보", required = true)
            @Valid @RequestPart("data") EventRequestDto request,
            @Parameter(description = "이벤트 이미지")
            @RequestPart(value = "image", required = false) MultipartFile image);

    @Operation(summary = "이벤트 정보 수정", description = "이벤트의 텍스트 필드(제목, 설명 등)를 수정합니다. 이미지 변경은 이미지 수정 API를 사용하세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 수정 성공"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<EventResponseDto>> updateEvent(
            @Parameter(description = "이벤트 ID", required = true) @PathVariable(name = "event-id") Long eventId,
            @Valid @RequestBody EventRequestDto request);

    @Operation(summary = "이벤트 이미지 수정", description = "이벤트의 이미지를 교체합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이미지 수정 성공"),
            @ApiResponse(responseCode = "404", description = "이벤트 없음")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<EventResponseDto>> updateEventImage(
            @Parameter(description = "이벤트 ID", required = true) @PathVariable(name = "event-id") Long eventId,
            @Parameter(description = "교체할 이미지 (선택, 미전송 시 기존 이미지 삭제)")
            @RequestPart(value = "image", required = false) MultipartFile image);

    @Operation(summary = "이벤트 삭제", description = "특정 이벤트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> deleteEvent(
            @Parameter(description = "이벤트 ID", required = true) @PathVariable(name = "event-id") Long eventId);
}
