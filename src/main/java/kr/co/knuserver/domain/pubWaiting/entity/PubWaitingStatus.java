package kr.co.knuserver.domain.pubWaiting.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PubWaitingStatus {
    WAITING("웨이팅 등록"),
    CALL("입장 호출"),
    ENTERED("입장 완료"),
    NO_SHOW("미방문"),
    CANCELLED("취소");

    private final String description;
}
