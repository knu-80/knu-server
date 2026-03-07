package kr.co.knuserver.presentation.event.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kr.co.knuserver.domain.event.entity.EventType;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Event API", description = "일반 사용자용 이벤트 관련 API")
public interface EventApiControllerDocs {

    @Operation(summary = "이벤트 리스트 조회", description = "특정 타입(가두모집/동아리 축제)의 이벤트 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 리스트 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<EventResponseDto>>> getEventList(
            @Parameter(description = "이벤트 타입", required = true) @PathVariable(name = "event-type") EventType eventType);
}
