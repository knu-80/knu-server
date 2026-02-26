package kr.co.knuserver.presentation.notice.dto;

import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.notice.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeListResponse(
        Long noticeId,
        String title,
        String contentPreview,
        LocalDateTime createdAt,
        Long authorId,
        String authorNickname,
        String type,
        List<String> imageUrls
) {
    private static final int PREVIEW_LENGTH = 100;

    public static NoticeListResponse fromEntity(Notice notice, Member author, List<String> imageUrls) {
        String content = notice.getContent();
        String preview = content.length() > PREVIEW_LENGTH ? content.substring(0, PREVIEW_LENGTH) + "..." : content;

        return new NoticeListResponse(
                notice.getId(),
                notice.getTitle(),
                preview,
                notice.getCreatedAt(),
                author.getId(),
                author.getNickname(),
                notice.getType().name(),
                imageUrls
        );
    }
}
