package kr.co.knuserver.presentation.event;

import java.util.List;
import kr.co.knuserver.application.event.EventQueryService;
import kr.co.knuserver.domain.event.entity.EventType;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.event.docs.EventApiControllerDocs;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventApiController implements EventApiControllerDocs {

    private final EventQueryService eventQueryService;

    // 이벤트 단건 조회
    @Override
    @GetMapping("/{event-id}")
    public ResponseEntity<ApiResponse<EventResponseDto>> getEvent(
        @PathVariable(name = "event-id") Long eventId
    ) {
        EventResponseDto result = eventQueryService.getEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 이벤트 리스트 조회
    @Override
    @GetMapping("/list/{event-type}")
    public ResponseEntity<ApiResponse<List<EventResponseDto>>> getEventList(
        @PathVariable(name = "event-type") EventType eventType
    ) {
        List<EventResponseDto> list = eventQueryService.getEventListByEventType(eventType);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(list));
    }
}
