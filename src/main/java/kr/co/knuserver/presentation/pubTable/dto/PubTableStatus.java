package kr.co.knuserver.presentation.pubTable.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PubTableStatus {
    INUSE("사용 중"),
    EMPTY("비어 있음");

    private final String description;

}
