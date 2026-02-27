package kr.co.knuserver.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import javax.lang.model.type.ErrorType;
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
public class ApiResponse<T> {
    private final ResultType result;
    private final T data;
    private String code;
    private String message;

    public static ApiResponse<?> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null, null);
    }

    public static <S> ApiResponse<S> success(String message) {
        return new ApiResponse<>(ResultType.SUCCESS, null, null, message);
    }

    public static <S> ApiResponse<S> success(S data, String message) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null, message);
    }

    public static ApiResponse<?> error(ErrorCode error) {
        return new ApiResponse<>(ResultType.FAIL, null, error.getCode(), error.getMessage());
    }

    public static ApiResponse<?> error(MethodArgumentNotValidException error) {
        return new ApiResponse<>(ResultType.FAIL, null, null, error.getMessage());
    }

    public static ApiResponse<?> error(Exception error) {
        return new ApiResponse<>(ResultType.FAIL, null, null, error.getMessage());
    }
}