package kr.co.knuserver.global.handler;

import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.bind.ServletRequestBindingException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[400] Validation Failed: {}", e.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        final ApiResponse response = ApiResponse.error(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    protected ResponseEntity<ApiResponse> handleServletRequestBindingException(ServletRequestBindingException e) {
        log.debug("[400] Invalid Access: {}", e.getMessage());
        final ApiResponse response = ApiResponse.success("잘못된 접근입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.debug("[405] Method Not Allowed: {}", e.getMessage());
        final ApiResponse response = ApiResponse.error(BusinessErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        log.debug("[404] No Resource Found: {}", e.getMessage());
        final ApiResponse response = ApiResponse.error(BusinessErrorCode.BOOTH_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AsyncRequestNotUsableException.class)
    protected void handleAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
        log.debug("[Async] 클라이언트 연결 끊김: {}", e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse> handleBusinessException(final BusinessException e) {
        log.error("[{}] Business Exception: {}", e.getErrorCode().getStatus(), e.getMessage());
        final ApiResponse response = ApiResponse.error(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<ApiResponse> handleDataAccessException(DataAccessException e) {
        log.error("[503] Data Access Exception", e);
        final ApiResponse response = ApiResponse.error(BusinessErrorCode.REDIS_UNAVAILABLE);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("[500] Internal Server Error", e);
        final ApiResponse response = ApiResponse.error(e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
