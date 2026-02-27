package kr.co.knuserver.domain.notice.entity;

import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType {
    GENERAL("일반 공지"),
    LOST_FOUND("분실물 공지");

    private final String description;

    public static NoticeType from(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(BusinessErrorCode.INVALID_TYPE_VALUE);
        }
        try {
            return NoticeType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(BusinessErrorCode.INVALID_TYPE_VALUE);
        }
    }
}
