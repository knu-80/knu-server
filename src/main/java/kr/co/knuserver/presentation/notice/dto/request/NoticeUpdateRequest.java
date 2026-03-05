package kr.co.knuserver.presentation.notice.dto.request;

import jakarta.validation.constraints.NotNull;

public record NoticeUpdateRequest(
        String title,
        String content,
        @NotNull Boolean includeImage,
        LostFoundDetailRequest lostFoundDetail
) {
    public record LostFoundDetailRequest(
            String foundPlace,
            String foundItem
    ) {
    }
}
