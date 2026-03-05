package kr.co.knuserver.presentation.booth.dto;

import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;
import lombok.Builder;

@Builder
public record BoothInfoResponseDto(
    Long id,
    Integer boothNumber,
    Integer userBoothNumber,
    String name,
    BoothDivision division,
    String description,
    String keywords,
    String applyLink,
    String imageUrl,
    boolean isActive
) {
    public static BoothInfoResponseDto fromEntity(Booth entity){
        return BoothInfoResponseDto.builder()
            .id(entity.getId())
            .boothNumber(entity.getBoothNumber())
            .userBoothNumber(entity.getUserBoothNumber())
            .name(entity.getName())
            .division(entity.getDivision())
            .description(entity.getDescription())
            .keywords(entity.getKeywords())
            .applyLink(entity.getApplyLink())
            .imageUrl(entity.getImageUrl())
            .isActive(entity.getIsActive())
            .build();
    }
}
