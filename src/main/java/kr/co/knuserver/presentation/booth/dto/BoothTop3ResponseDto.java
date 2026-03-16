package kr.co.knuserver.presentation.booth.dto;

public record BoothTop3ResponseDto(
    Long boothId,
    String boothName,
    long likeCount
) {}
