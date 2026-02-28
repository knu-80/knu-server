package kr.co.knuserver.presentation.pubTable.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import lombok.Builder;

@Builder
public record CurrentPubTableSession(
        @NotNull LocalDateTime entryTime,
        int guestCount
) {
        public static CurrentPubTableSession fromEntity(PubTableSession pubTableSession) {
                return CurrentPubTableSession.builder()
                        .entryTime(pubTableSession.getEntryTime())
                        .guestCount(pubTableSession.getGuestCount())
                        .build();
        }
}
