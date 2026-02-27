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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final MemberRepository memberRepository;

    public List<NoticeListResponse> getNotices(Long lastId, int size) {
        List<Notice> notices = noticeRepository.findNoticesByCursor(lastId, PageRequest.of(0, size));

        if (notices.isEmpty()) {
            return List.of();
        }

        List<Long> memberIds = notices.stream().map(Notice::getMemberId).distinct().toList();
        Map<Long, Member> memberMap = memberRepository.findAllById(memberIds).stream()
                .collect(Collectors.toMap(Member::getId, m -> m));

        List<Long> noticeIds = notices.stream().map(Notice::getId).toList();
        Map<Long, List<String>> imageUrlMap = noticeImageRepository.findAllByNoticeIdIn(noticeIds).stream()
                .collect(Collectors.groupingBy(
                        NoticeImage::getNoticeId,
                        Collectors.mapping(NoticeImage::getImageUrl, Collectors.toList())
                ));

        return notices.stream()
                .map(notice -> {
                    Member author = memberMap.get(notice.getMemberId());
                    if (author == null) {
                        throw new BusinessException(BusinessErrorCode.UNAUTHORIZED_USER);
                    }
                    List<String> imageUrls = imageUrlMap.getOrDefault(notice.getId(), List.of());
                    return NoticeListResponse.fromEntity(notice, author, imageUrls);
                })
                .toList();
    }

    public NoticeDetailResponse getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOTICE_NOT_FOUND));

        Member author = memberRepository.findById(notice.getMemberId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.UNAUTHORIZED_USER));

        List<String> imageUrls = noticeImageRepository.findAllByNoticeId(noticeId).stream()
                .map(NoticeImage::getImageUrl)
                .toList();

        return NoticeDetailResponse.fromEntity(notice, author, imageUrls);
    }
}
