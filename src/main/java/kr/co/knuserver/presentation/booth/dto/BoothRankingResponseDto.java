package kr.co.knuserver.presentation.booth.dto;

public record BoothRankingResponseDto(
    Long boothId,
    String name,
    long likeCount
) {
}
