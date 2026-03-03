package kr.co.knuserver.presentation.pubWaiting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaiting;
import kr.co.knuserver.domain.pubWaiting.entity.PubWaitingStatus;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PubWaitingResponseDto(
        @NotNull Long pubWaitingId,
        @NotNull Long memberId,
        @NotBlank String phone,
        int guestCount,
        @NotNull PubWaitingStatus status
) {
    public static PubWaitingResponseDto fromEntity(PubWaiting pubWaiting) {
        return PubWaitingResponseDto.builder()
                .pubWaitingId(pubWaiting.getId())
                .memberId(pubWaiting.getMemberId())
                .phone(pubWaiting.getPhone())
                .guestCount(pubWaiting.getGuestCount())
                .status(pubWaiting.getStatus())
                .build();
    }
}
