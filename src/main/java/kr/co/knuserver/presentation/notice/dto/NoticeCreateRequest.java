package kr.co.knuserver.presentation.notice.dto;

import jakarta.validation.constraints.NotBlank;

public record NoticeCreateRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String type
) {
}
