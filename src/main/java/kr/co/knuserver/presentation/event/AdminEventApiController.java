package kr.co.knuserver.presentation.event;

import jakarta.validation.Valid;
import kr.co.knuserver.application.event.EventCommandService;
import kr.co.knuserver.domain.member.entity.Role;
import kr.co.knuserver.global.auth.RequireRole;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.presentation.event.docs.AdminEventApiControllerDocs;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/v1/events")
@RequiredArgsConstructor
@RequireRole(Role.ADMIN)
public class AdminEventApiController implements AdminEventApiControllerDocs {

    private final EventCommandService eventCommandService;

    // 이벤트 생성
    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EventResponseDto>> registerEvent(
        @RequestPart(value = "data") @Valid EventRequestDto request,
        @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        EventResponseDto result = eventCommandService.registerEvent(request, image);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(result));
    }

    // 이벤트 정보(이미지 외 필드)만 수정
    @Override
    @PatchMapping("/{event-id}")
    public ResponseEntity<ApiResponse<EventResponseDto>> updateEvent(
        @PathVariable(name = "event-id") Long eventId,
        @RequestBody EventRequestDto request
    ) {
        EventResponseDto result = eventCommandService.updateEvent(eventId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 이미지만 수정
    @Override
    @PostMapping(value = "/{event-id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EventResponseDto>> updateEventImage(
        @PathVariable(name = "event-id") Long eventId,
        @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        EventResponseDto result = eventCommandService.updateEventImage(eventId, image);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 이벤트 삭제
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
