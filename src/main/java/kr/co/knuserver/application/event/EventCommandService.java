package kr.co.knuserver.application.event;

import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.repository.EventRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.infra.s3.S3Uploader;
import kr.co.knuserver.presentation.event.dto.EventRequestDto;
import kr.co.knuserver.presentation.event.dto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EventCommandService {

    private static final String S3_FOLDER = "event";

    private final EventRepository eventRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public EventResponseDto registerEvent(EventRequestDto request, MultipartFile image) {
        String imageUrl = uploadImage(image);
        try {
            Event result = eventRepository.saveAndFlush(EventRequestDto.toEntity(request, imageUrl));
            return EventResponseDto.fromEntity(result);
        } catch (DataIntegrityViolationException e) {
            if (imageUrl != null) {
                s3Uploader.delete(imageUrl);
            }
            throw new BusinessException(BusinessErrorCode.ALREADY_EXISTS);
        }
    }

    @Transactional
    public EventResponseDto updateEvent(Long eventId, EventRequestDto request) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.EVENT_NOT_FOUND));
        event.updateEvent(request, null);

        return EventResponseDto.fromEntity(event);
    }

    @Transactional
    public EventResponseDto updateEventImage(Long eventId, MultipartFile image) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.EVENT_NOT_FOUND));

        if (event.getImageUrl() != null) {
            s3Uploader.delete(event.getImageUrl());
        }

        String imageUrl = uploadImage(image);
        event.updateEvent(new EventRequestDto(event.getTitle(), event.getDescription(), event.getLocation(), event.getEventType(), event.getEventDuration().getStartAt(), event.getEventDuration().getEndAt(), event.getIsActive()), imageUrl);

        return EventResponseDto.fromEntity(event);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.EVENT_NOT_FOUND));

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
