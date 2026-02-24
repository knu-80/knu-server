package kr.co.knuserver.presentation.booth.dto;

import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;

public record BoothRegisterRequestDto(
    Long id,
    Long memberId,
    Integer boothNumber,
    String name,
    BoothDivision division,
    String description,
    String applyLink,
    String imageUrl
) {

    public static Booth toEntity(BoothRegisterRequestDto request) {
        return Booth.createBooth(
            request.memberId(),
            request.boothNumber(),
            request.name(),
            request.division(),
            request.description(),
            request.applyLink(),
            request.imageUrl()
        );
    }
}
