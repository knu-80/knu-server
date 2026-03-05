package kr.co.knuserver.presentation.booth.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import kr.co.knuserver.domain.booth.entity.BoothDivision;

public record BoothUpdateRequestDto(
    @Positive(message = "member ID는 양수만 입력 가능합니다.")
    Long memberId,

    @Positive(message = "boothNumber는 양수만 입력 가능합니다.")
    Integer boothNumber,

    @Positive(message="userBoothNumber는 양수만 입력 가능합니다.")
    Integer userBoothNumber,

    @Size(max = 50, message = "부스 이름은 최대 50자까지 입력 가능합니다.")
    String name,
    BoothDivision division,
    String description,
    String applyLink,
    String imageUrl,
    Boolean isActive
) {

}
