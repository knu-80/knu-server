package kr.co.knuserver.application.notice;

import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.member.repository.MemberRepository;
import kr.co.knuserver.domain.notice.entity.Notice;
import kr.co.knuserver.domain.notice.entity.NoticeImage;
import kr.co.knuserver.domain.notice.repository.NoticeImageRepository;
import kr.co.knuserver.domain.notice.repository.NoticeRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.presentation.notice.dto.NoticeDetailResponse;
import kr.co.knuserver.presentation.notice.dto.NoticeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    public List<NoticeListResponse> getNotices(Long lastId, int size) {
        List<Notice> notices = noticeRepository.findNoticesByCursor(lastId, PageRequest.of(0, size));

        return notices.stream()
                .map(notice -> {
                    Member author = findMemberOrThrow(notice.getMemberId());
                    List<String> imageUrls = getImageUrls(notice.getId());
                    return NoticeListResponse.fromEntity(notice, author, imageUrls);
                })
                .toList();
    }

    public NoticeDetailResponse getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        Member author = findMemberOrThrow(notice.getMemberId());
        List<String> imageUrls = getImageUrls(noticeId);

        return NoticeDetailResponse.fromEntity(notice, author, imageUrls);
    }

    private Member findMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.UNAUTHORIZED_USER));
    }

    private List<String> getImageUrls(Long noticeId) {
        return noticeImageRepository.findAllByNoticeId(noticeId).stream()
                .map(NoticeImage::getImageUrl)
                .toList();
    }
}
