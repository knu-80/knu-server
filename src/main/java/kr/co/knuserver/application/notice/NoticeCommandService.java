package kr.co.knuserver.application.notice;

import kr.co.knuserver.domain.notice.entity.LostFoundDetail;
import kr.co.knuserver.domain.notice.entity.Notice;
import kr.co.knuserver.domain.notice.entity.NoticeImage;
import kr.co.knuserver.domain.notice.entity.NoticeType;
import kr.co.knuserver.domain.notice.repository.NoticeImageRepository;
import kr.co.knuserver.domain.notice.repository.NoticeRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.infra.s3.S3Uploader;
import kr.co.knuserver.presentation.notice.dto.request.NoticeCreateRequest;
import kr.co.knuserver.presentation.notice.dto.response.NoticeResponse;
import kr.co.knuserver.presentation.notice.dto.request.NoticeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCommandService {

    private static final String S3_FOLDER = "notice";

    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final S3Uploader s3Uploader;
    private final ApplicationEventPublisher eventPublisher;

    public NoticeResponse createNotice(NoticeCreateRequest request, List<MultipartFile> images, Long memberId) {
        NoticeType type = parseNoticeType(request.type());
        LostFoundDetail lostFoundDetail = toLostFoundDetail(type, request.lostFoundDetail());

        Notice notice = Notice.createNotice(request.title(), request.content(), type, memberId, lostFoundDetail);
        Notice saved = noticeRepository.save(notice);

        List<String> imageUrls = uploadImages(saved.getId(), images);

        return NoticeResponse.fromEntity(saved, imageUrls);
    }

    public NoticeResponse updateNotice(Long noticeId, NoticeUpdateRequest request, Long memberId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        validateAuthor(notice, memberId);

        LostFoundDetail lostFoundDetail = request.lostFoundDetail() != null
                ? new LostFoundDetail(
                        request.lostFoundDetail().foundPlace(),
                        request.lostFoundDetail().keepingPlace(),
                        request.lostFoundDetail().description())
                : null;

        notice.updateNotice(request.title(), request.content(), lostFoundDetail);

        List<String> imageUrls = noticeImageRepository.findAllByNoticeId(noticeId).stream()
                .map(NoticeImage::getImageUrl)
                .toList();

        return NoticeResponse.fromEntity(notice, imageUrls);
    }

    public void deleteNotice(Long noticeId, Long memberId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        validateAuthor(notice, memberId);

        List<String> imageUrls = noticeImageRepository.findAllByNoticeId(noticeId).stream()
                .map(NoticeImage::getImageUrl)
                .toList();

        noticeImageRepository.deleteAllByNoticeId(noticeId);
        noticeRepository.delete(notice);

        eventPublisher.publishEvent(new NoticeImageDeleteEvent(imageUrls));
    }

    private void validateAuthor(Notice notice, Long memberId) {
        if (!notice.getMemberId().equals(memberId)) {
            throw new BusinessException(BusinessErrorCode.ACCESS_DENIED);
        }
    }

    private NoticeType parseNoticeType(String type) {
        try {
            return NoticeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(BusinessErrorCode.INVALID_TYPE_VALUE);
        }
    }

    private LostFoundDetail toLostFoundDetail(NoticeType type, NoticeCreateRequest.LostFoundDetailRequest request) {
        if (type == NoticeType.LOST_FOUND) {
            if (request == null) {
                throw new BusinessException(BusinessErrorCode.MISSING_INPUT_VALUE);
            }
            return new LostFoundDetail(request.foundPlace(), request.keepingPlace(), request.description());
        }
        return null;
    }

    private List<String> uploadImages(Long noticeId, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }
        return images.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    String imageUrl = s3Uploader.upload(file, S3_FOLDER);
                    noticeImageRepository.save(NoticeImage.of(noticeId, imageUrl));
                    return imageUrl;
                })
                .toList();
    }
}
