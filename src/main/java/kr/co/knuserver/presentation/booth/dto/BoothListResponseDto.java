package kr.co.knuserver.presentation.booth.dto;

import kr.co.knuserver.domain.booth.entity.Booth;
import lombok.Builder;

@Builder
public record BoothListResponseDto(
    Long id,
    String name,
    String divisionName,
    String description,
    String imageUrl,
    String applyLink
) {
    public static BoothListResponseDto fromEntity(Booth entity, String firstImageUrl) {
        return BoothListResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .divisionName(entity.getDivision().getDescription())
            .description(entity.getDescription())
            .imageUrl(firstImageUrl)
            .applyLink(entity.getApplyLink())
            .build();
    }
}
