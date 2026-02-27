package kr.co.knuserver.presentation.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdminLoginRequest(
        @NotBlank(message = "PIN을 입력해주세요.")
        @Pattern(regexp = "^\\d{4}$", message = "PIN은 4자리 숫자여야 합니다.")
        String pin
) {
}
