package kr.co.knuserver.application.notice;

import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.member.repository.MemberRepository;
import kr.co.knuserver.domain.notice.entity.Notice;
import kr.co.knuserver.domain.notice.entity.NoticeImage;
import kr.co.knuserver.domain.notice.repository.NoticeImageRepository;
import kr.co.knuserver.domain.notice.repository.NoticeRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.notice.dto.response.NoticeDetailResponse;
import kr.co.knuserver.presentation.notice.dto.response.NoticeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final MemberRepository memberRepository;

    public List<NoticeListResponse> getNotices() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(NoticeListResponse::fromEntity)
                .toList();
    }

    public List<NoticeListResponse> getRecentNotices() {
        return noticeRepository.findTop3ByOrderByIdDesc().stream()
                .map(NoticeListResponse::fromEntity)
                .toList();
    }

    public NoticeDetailResponse getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        Member author = memberRepository.findById(notice.getMemberId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        List<String> imageUrls = noticeImageRepository.findAllByNoticeId(noticeId).stream()
                .map(NoticeImage::getImageUrl)
                .toList();

        return NoticeDetailResponse.fromEntity(notice, author, imageUrls);
    }
}
