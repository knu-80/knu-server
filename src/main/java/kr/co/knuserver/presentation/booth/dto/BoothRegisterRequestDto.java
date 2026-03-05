package kr.co.knuserver.presentation.booth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;

public record BoothRegisterRequestDto(
    Long id,

    @NotNull(message = "부스 운영자 member ID는 필수입니다.")
    @Positive(message = "member ID는 양수만 입력 가능합니다.")
    Long memberId,

    @NotNull(message = "부스 번호는 필수입니다.")
    @Positive(message = "boothNumber는 양수만 입력 가능합니다.")
    Integer boothNumber,

    @NotNull(message = "유저 부스 번호는 필수입니다.")
    @Positive(message = "userBoothNumber는 양수만 입력 가능합니다.")
    Integer userBoothNumber,

    @NotBlank(message = "부스 이름은 필수입니다.")
    @Size(max = 50, message = "부스 이름은 최대 50자까지 입력 가능합니다.")
    String name,

    @NotNull(message = "부스 분과는 필수입니다.")
    BoothDivision division,
    String description,
    String keywords,
    String applyLink,
    String imageUrl
) {

    public static Booth toEntity(BoothRegisterRequestDto request) {
        return Booth.createBooth(
            request.memberId(),
            request.boothNumber(),
            request.userBoothNumber(),
            request.name(),
            request.division(),
            request.description(),
            request.keywords(),
            request.applyLink(),
            request.imageUrl()
        );
    }
}
