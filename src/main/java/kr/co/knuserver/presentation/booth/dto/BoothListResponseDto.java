package kr.co.knuserver.presentation.booth.dto;

import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;
import lombok.Builder;

@Builder
public record BoothListResponseDto(
    Long id,
    String name,
    BoothDivision division,
    String description,
    String imageUrl,
    String applyLink
) {
    public static BoothListResponseDto fromEntity(Booth entity, String firstImageUrl) {
        return BoothListResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .division(entity.getDivision())
            .description(entity.getDescription())
            .imageUrl(firstImageUrl)
            .applyLink(entity.getApplyLink())
            .build();
    }
}
