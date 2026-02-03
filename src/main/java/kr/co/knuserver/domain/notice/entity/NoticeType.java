package kr.co.knuserver.domain.notice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType {
    GENERAL("일반 공지"),
    LOST_FOUND("분실물 공지");

    private final String description;
}
