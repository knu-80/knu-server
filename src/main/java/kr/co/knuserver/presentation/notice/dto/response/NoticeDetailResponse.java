package kr.co.knuserver.presentation.notice.dto.response;

import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.notice.entity.LostFoundDetail;
import kr.co.knuserver.domain.notice.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeDetailResponse(
        Long noticeId,
        String title,
        LocalDateTime createdAt,
        Long authorId,
        String authorNickname,
        String content,
        String type,
        LostFoundDetailResponse lostFoundDetail,
        List<String> imageUrls
) {
    public record LostFoundDetailResponse(String foundPlace, String keepingPlace, String description) {
        public static LostFoundDetailResponse from(LostFoundDetail detail) {
            return new LostFoundDetailResponse(
                    detail.getFoundPlace(),
                    detail.getKeepingPlace(),
                    detail.getDescription()
            );
        }
    }

    public static NoticeDetailResponse fromEntity(Notice notice, Member author, List<String> imageUrls) {
        LostFoundDetailResponse lostFoundDetailResponse = notice.getLostFoundDetail() != null
                ? LostFoundDetailResponse.from(notice.getLostFoundDetail())
                : null;

        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getCreatedAt(),
                author.getId(),
                author.getNickname(),
                notice.getContent(),
                notice.getType().name(),
                lostFoundDetailResponse,
                imageUrls
        );
    }
}
