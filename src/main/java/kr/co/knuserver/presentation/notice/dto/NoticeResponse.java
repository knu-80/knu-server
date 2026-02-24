package kr.co.knuserver.presentation.notice.dto;

import kr.co.knuserver.domain.notice.entity.Notice;

public record NoticeResponse(Long noticeId, String title, String content, String type) {

    public static NoticeResponse fromEntity(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getType().name()
        );
    }
}
