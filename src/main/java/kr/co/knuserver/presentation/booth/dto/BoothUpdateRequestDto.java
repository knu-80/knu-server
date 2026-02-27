package kr.co.knuserver.presentation.booth.dto;

import jakarta.validation.constraints.Size;
import kr.co.knuserver.domain.booth.entity.BoothDivision;

public record BoothUpdateRequestDto(
    Long memberId,
    Integer boothNumber,

    @Size(max = 50, message = "부스 이름은 최대 50자까지 입력 가능합니다.")
    String name,
    BoothDivision division,
    String description,
    String applyLink,
    String imageUrl,
    Boolean isActive
) {

}
