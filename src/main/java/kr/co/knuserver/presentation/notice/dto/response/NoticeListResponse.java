package kr.co.knuserver.presentation.notice.dto.response;

import kr.co.knuserver.domain.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeListResponse(
        Long noticeId,
        String type,
        String title,
        LocalDateTime createdAt
) {
    public static NoticeListResponse fromEntity(Notice notice) {
        return new NoticeListResponse(
                notice.getId(),
                notice.getType().name(),
                notice.getTitle(),
                notice.getCreatedAt()
        );
    }
}
