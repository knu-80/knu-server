package kr.co.knuserver.presentation.event;

import java.util.List;
import kr.co.knuserver.application.event.EventService;
import kr.co.knuserver.domain.event.entity.EventType;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventApiController {

    private final EventService eventService;

    // 이벤트 생성
    @PostMapping
    public ResponseEntity<ApiResponse<EventResponseDto>> registerEvent(
        @RequestBody EventRequestDto request
    ) {
        EventResponseDto result = eventService.registerEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(result));
    }

    // 이벤트 단건 조회
    @GetMapping("/{event-id}")
    public ResponseEntity<ApiResponse<EventResponseDto>> getEvent(
        @PathVariable(name = "event-id") Long eventId
    ) {
        EventResponseDto result = eventService.getEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 이벤트 리스트 조회
    @GetMapping("/list/{event-type}")
    public ResponseEntity<ApiResponse<List<EventResponseDto>>> getEventList(
        @PathVariable(name = "event-type") EventType eventType
    ) {
        List<EventResponseDto> list = eventService.getEventListByEventType(eventType);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(list));
    }

    // 이벤트 수정
    @PatchMapping("/update/{event-id}")
    public ResponseEntity<ApiResponse<EventResponseDto>> updateEvent(
        @PathVariable(name = "event-id") Long eventId,
        @RequestBody EventRequestDto request
    ) {
        EventResponseDto result = eventService.updateEvent(eventId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    // 이벤트 삭제
    @DeleteMapping("/{event-id}")
    public ResponseEntity<ApiResponse<?>> deleteEvent(
        @PathVariable(name = "event-id") Long eventId
    ) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success());
    }
}
