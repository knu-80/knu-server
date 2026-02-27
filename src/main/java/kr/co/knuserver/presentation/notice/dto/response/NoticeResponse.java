package kr.co.knuserver.presentation.notice.dto.response;

import kr.co.knuserver.domain.notice.entity.LostFoundDetail;
import kr.co.knuserver.domain.notice.entity.Notice;

import java.util.List;

public record NoticeResponse(
        Long noticeId,
        String title,
        String content,
        String type,
        LostFoundDetailResponse lostFoundDetail,
        List<String> imageUrls
) {

    public record LostFoundDetailResponse(String foundPlace, String keepingPlace, String description) {
        public static LostFoundDetailResponse from(LostFoundDetail detail) {
            return new LostFoundDetailResponse(detail.getFoundPlace(), detail.getKeepingPlace(), detail.getDescription());
        }
    }

    public static NoticeResponse fromEntity(Notice notice, List<String> imageUrls) {
        LostFoundDetailResponse lostFoundDetailResponse = notice.getLostFoundDetail() != null
                ? LostFoundDetailResponse.from(notice.getLostFoundDetail())
                : null;

        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getType().name(),
                lostFoundDetailResponse,
                imageUrls == null ? List.of() : List.copyOf(imageUrls)
        );
    }

    public static NoticeResponse fromEntity(Notice notice) {
        return fromEntity(notice, List.of());
    }
}
