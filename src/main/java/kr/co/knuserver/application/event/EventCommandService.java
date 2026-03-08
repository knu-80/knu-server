package kr.co.knuserver.application.event;

import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.repository.EventRepository;
import kr.co.knuserver.infra.s3.S3Uploader;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class EventCommandService {

    private static final String S3_FOLDER = "event";

    private final EventRepository eventRepository;
    private final EventReader eventReader;
    private final S3Uploader s3Uploader;

    // 이벤트 생성
    public EventResponseDto registerEvent(EventRequestDto request, MultipartFile image) {
        String imageUrl = uploadImage(image);
        Event result = eventRepository.save(EventRequestDto.toEntity(request, imageUrl));

        return EventResponseDto.fromEntity(result);
    }

    // 이벤트 수정
    public EventResponseDto updateEvent(Long eventId, EventRequestDto request) {
        Event event = eventReader.getEventOrThrow(eventId);
        event.updateEvent(request, null);

        return EventResponseDto.fromEntity(event);
    }

    // 이벤트 이미지 수정 (교체)
    public EventResponseDto updateEventImage(Long eventId, MultipartFile image) {
        Event event = eventReader.getEventOrThrow(eventId);

        // 기존 S3 이미지 삭제
        if (event.getImageUrl() != null) {
            s3Uploader.delete(event.getImageUrl());
        }

        String imageUrl = uploadImage(image);
        event.updateEvent(new EventRequestDto(event.getTitle(), event.getDescription(), event.getLocation(), event.getEventType(), event.getEventDuration().getStartAt(), event.getEventDuration().getEndAt(), event.getIsActive()), imageUrl);

        return EventResponseDto.fromEntity(event);
    }

    // 이벤트 삭제
    public void deleteEvent(Long eventId) {
        Event event = eventReader.getEventOrThrow(eventId);

        // S3 이미지 삭제
        if (event.getImageUrl() != null) {
            s3Uploader.delete(event.getImageUrl());
        }
        
        eventRepository.delete(event);
    }

    private String uploadImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        return s3Uploader.upload(image, S3_FOLDER);
    }
}
