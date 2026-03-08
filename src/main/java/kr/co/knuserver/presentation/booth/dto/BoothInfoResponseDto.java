package kr.co.knuserver.presentation.booth.dto;

import java.util.List;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;
import lombok.Builder;

@Builder
public record BoothInfoResponseDto(
    Long id,
    Integer boothNumber,
    String name,
    BoothDivision division,
    String description,
    String keywords,
    String applyLink,
    String contact,
    List<String> imageUrls,
    boolean isActive
) {
    public static BoothInfoResponseDto fromEntity(Booth entity, List<String> imageUrls){
        return BoothInfoResponseDto.builder()
            .id(entity.getId())
            .boothNumber(entity.getBoothNumber())
            .name(entity.getName())
            .division(entity.getDivision())
            .description(entity.getDescription())
            .keywords(entity.getKeywords())
            .applyLink(entity.getApplyLink())
            .contact(entity.getContact())
            .imageUrls(imageUrls == null ? List.of() : List.copyOf(imageUrls))
            .isActive(entity.getIsActive())
            .build();
    }
}
