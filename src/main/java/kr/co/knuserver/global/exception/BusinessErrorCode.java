package kr.co.knuserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode implements ErrorCode {
    /*
     * 주석 아래로 Service에서 발생하는 ErrorCode 작성
     * 김두현 : C1xx,
     * 송재훈 : C2xx,
     * 이견희 : C3xx,
     * 유우석 : C4xx,
     * 유지훈 : C5xx
     *
     * xx부분 01부터 순차적으로 작성
     */


    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 타입이 입력되었습니다."),
    MISSING_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C003", "필수 입력값이 누락되었습니다."),

    /*
     * 401 UNAUTHORIZED: 인증되지 않은 사용자
     */
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "C004", "인증되지 않은 사용자입니다."),
    INVALID_PIN(HttpStatus.UNAUTHORIZED, "C402", "잘못된 PIN입니다."),

    /*
     * 403 FORBIDDEN: 권한이 없는 사용자
     */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근 권한이 없습니다."),

    /*
     * 404 NOT_FOUND: 존재하지 않는 리소스
     */
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C102", "이벤트가 존재하지 않습니다."),
    BOOTH_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", "존재하지 않는 부스입니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "C401", "관리자 계정을 찾을 수 없습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "C402", "해당 공지사항을 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C007", "허용되지 않은 메서드입니다."),

    /*
     * 409 CONFLICT: 리소스 충돌 (중복 데이터 등)
     */
    ALREADY_EXISTS(HttpStatus.CONFLICT, "C008", "이미 존재하는 데이터입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C009", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public int getStatus() {
        return status.value();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}