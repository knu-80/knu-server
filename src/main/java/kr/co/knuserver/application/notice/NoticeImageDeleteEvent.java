package kr.co.knuserver.application.notice;

import java.util.List;

public record NoticeImageDeleteEvent(List<String> imageUrls) {
}
