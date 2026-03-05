package kr.co.knuserver.presentation.notice.dto.request;

public record NoticeUpdateRequest(
        String title,
        String content,
        boolean includeImage,
        LostFoundDetailRequest lostFoundDetail
) {
    public record LostFoundDetailRequest(
            String foundPlace,
            String foundItem
    ) {
    }
}
