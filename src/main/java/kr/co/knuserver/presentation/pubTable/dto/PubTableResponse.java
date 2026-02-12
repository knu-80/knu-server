package kr.co.knuserver.presentation.pubTable.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PubTableResponse(
        @NotNull Long pubTableId,
        @NotNull int tableNum,
        @NotNull Long pubBoothId,
        @NotNull String status,
        CurrentPubTableSession currentPubTableSession
) {
    public static PubTableResponse fromEntity(PubTable pubTable) {
        return PubTableResponse.builder()
                .pubTableId(pubTable.getId())
                .tableNum(pubTable.getTableNum())
                .pubBoothId(pubTable.getPubBoothId())
                .status(PubTableStatus.EMPTY.getDescription())
                .build();
    }

    public static PubTableResponse fromEntity(PubTable pubTable, PubTableSession pubTableSession) {
        if (pubTableSession == null) {
            return PubTableResponse.fromEntity(pubTable);
        }
        CurrentPubTableSession currentPubTableSession = CurrentPubTableSession.fromEntity(pubTableSession);
        return PubTableResponse.builder()
                .pubTableId(pubTable.getId())
                .tableNum(pubTable.getTableNum())
                .pubBoothId(pubTable.getPubBoothId())
                .status(PubTableStatus.INUSE.getDescription())
                .currentPubTableSession(currentPubTableSession)
                .build();
    }
}
