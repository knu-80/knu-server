package kr.co.knuserver.presentation.pubTable.dto;

import jakarta.validation.constraints.NotNull;
import kr.co.knuserver.domain.pubTable.entity.PubTable;

public record PubTableRequestDto(
    int tableNum,
    @NotNull Long pubBoothId
) {

    public PubTable toEntity() {
        return PubTable.createPubTable(tableNum, pubBoothId);
    }
}
