package kr.co.knuserver.application.event;


import kr.co.knuserver.domain.event.entity.Event;
import kr.co.knuserver.domain.event.repository.EventRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EventReader {

    private final EventRepository eventRepository;

    public Event getEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.EVENT_NOT_FOUND));
    }
}
