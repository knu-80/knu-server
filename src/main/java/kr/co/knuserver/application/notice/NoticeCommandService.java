package kr.co.knuserver.application.notice;

import kr.co.knuserver.domain.notice.entity.Notice;
import kr.co.knuserver.domain.notice.entity.NoticeType;
import kr.co.knuserver.domain.notice.repository.NoticeRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.notice.dto.NoticeCreateRequest;
import kr.co.knuserver.presentation.notice.dto.NoticeResponse;
import kr.co.knuserver.presentation.notice.dto.NoticeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCommandService {

    private final NoticeRepository noticeRepository;

    public NoticeResponse createNotice(NoticeCreateRequest request, Long memberId) {
        Notice notice = Notice.createNotice(
                request.title(),
                request.content(),
                NoticeType.valueOf(request.type()),
                memberId
        );
        Notice saved = noticeRepository.save(notice);
        return NoticeResponse.fromEntity(saved);
    }

    public NoticeResponse updateNotice(Long noticeId, NoticeUpdateRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        NoticeType type = request.type() != null ? NoticeType.valueOf(request.type()) : null;
        notice.updateNotice(request.title(), request.content(), type);

        return NoticeResponse.fromEntity(notice);
    }

    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));
        noticeRepository.delete(notice);
    }
}
