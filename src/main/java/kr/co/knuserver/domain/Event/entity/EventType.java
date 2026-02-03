package kr.co.knuserver.domain.Event.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    RECRUITMENT("가두모집"),
    CLUB_FESTIVAL("동아리 축제");

    private final String description;
}
