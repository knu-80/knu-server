package kr.co.knuserver.presentation.booth.dto;

import kr.co.knuserver.domain.booth.entity.BoothDivision;

public record BoothUpdateRequestDto(
    Long memberId,
    Integer boothNumber,
    String name,
    BoothDivision division,
    String description,
    String applyLink,
    String imageUrl,
    Boolean isActive
) {

}
