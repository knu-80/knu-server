package kr.co.knuserver.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public static ErrorResponse of(Integer status, String code, String message) {
        return new ErrorResponse(status, code, message);
    }

    public static ErrorResponse from(Exception e) {
        return new ErrorResponse(500, "INTERNAL_SERVER_ERROR", e.getMessage());
    }

    public static ErrorResponse from(BusinessException e) {
        return new ErrorResponse(e.getErrorCode().getStatus(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    public static ErrorResponse from(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        String message = "Validation failed for fields: " + errors.toString();
        return new ErrorResponse(BusinessErrorCode.INVALID_INPUT_VALUE.getStatus(),
                BusinessErrorCode.INVALID_INPUT_VALUE.getCode(),
                message);
    }
}
