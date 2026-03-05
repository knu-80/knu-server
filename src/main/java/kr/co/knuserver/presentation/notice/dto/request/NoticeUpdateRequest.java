package kr.co.knuserver.presentation.notice.dto.request;

public record NoticeUpdateRequest(
        String title,
        String content,
        LostFoundDetailRequest lostFoundDetail
) {
    public record LostFoundDetailRequest(
            String foundPlace,
            String foundItem
    ) {
    }
}
