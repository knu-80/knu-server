package kr.co.knuserver.presentation.booth.dto;

import lombok.Builder;

@Builder
public record BoothCountResponseDto(
    Integer count
) {
    public static BoothCountResponseDto from(Integer count) {
        return BoothCountResponseDto.builder()
            .count(count)
            .build();
    }
}
