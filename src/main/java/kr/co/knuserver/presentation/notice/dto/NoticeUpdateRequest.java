package kr.co.knuserver.presentation.notice.dto;

public record NoticeUpdateRequest(
        String title,
        String content,
        String type,
        LostFoundDetailRequest lostFoundDetail
) {
    public record LostFoundDetailRequest(
            String foundPlace,
            String keepingPlace,
            String description
    ) {
    }
}
