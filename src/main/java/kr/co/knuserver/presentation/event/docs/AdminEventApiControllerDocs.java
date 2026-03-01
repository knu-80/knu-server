package kr.co.knuserver.presentation.event.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin Event API", description = "개발진용 이벤트 관련 API")
public interface AdminEventApiControllerDocs {

    @Operation(summary = "이벤트 생성", description = "새로운 이벤트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이벤트 생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<EventResponseDto>> registerEvent(
            @Valid @RequestBody EventRequestDto request);

    @Operation(summary = "이벤트 수정", description = "특정 이벤트를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 수정 성공"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<EventResponseDto>> updateEvent(
            @Parameter(description = "이벤트 ID", required = true) @PathVariable(name = "event-id") Long eventId,
            @RequestBody EventRequestDto request);

    @Operation(summary = "이벤트 삭제", description = "특정 이벤트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> deleteEvent(
            @Parameter(description = "이벤트 ID", required = true) @PathVariable(name = "event-id") Long eventId);
}
