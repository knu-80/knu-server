package kr.co.knuserver.global.handler;

import kr.co.knuserver.global.exception.BusinessException;

import kr.co.knuserver.global.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[400] Validation Failed: {}", e.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        final ApiResponse response = ApiResponse.error(e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse> handleBusinessException(final BusinessException e) {
        log.error("[{}] Business Exception: {}", e.getErrorCode().getStatus(), e.getMessage());
        final ApiResponse response = ApiResponse.error(e.getErrorCode());

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("[500] Internal Server Error", e);
        final ApiResponse response = ApiResponse.error(e);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

