package kr.co.knuserver.presentation.event;

import jakarta.validation.Valid;
import kr.co.knuserver.application.event.EventCommandService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.event.docs.AdminEventApiControllerDocs;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/events")
@RequiredArgsConstructor
public class AdminEventApiController implements AdminEventApiControllerDocs {

    private final EventCommandService eventCommandService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<EventResponseDto>> registerEvent(
        @Valid @RequestBody EventRequestDto request
    ) {
        EventResponseDto result = eventCommandService.registerEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(result));
    }

    @Override
    @PatchMapping("/update/{event-id}")
    public ResponseEntity<ApiResponse<EventResponseDto>> updateEvent(
        @PathVariable(name = "event-id") Long eventId,
        @RequestBody EventRequestDto request
    ) {
        EventResponseDto result = eventCommandService.updateEvent(eventId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(result));
    }

    @Override
    @DeleteMapping("/{event-id}")
    public ResponseEntity<ApiResponse<?>> deleteEvent(
        @PathVariable(name = "event-id") Long eventId
    ) {
        eventCommandService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success());
    }
}
