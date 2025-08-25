package com.quadra.common.controllerAdvice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // For CICD TEST
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "내부 서버 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E002", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E003", "허용되지 않은 HTTP 메소드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "E004", "요청한 리소스를 찾을 수 없습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "E005", "잘못된 타입 값입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "E006", "접근이 거부되었습니다."),
    JSON_WRITE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E007", "JSON 변환 중 오류가 발생했습니다."),
    
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "V001", "입력값 검증에 실패했습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "V002", "중복된 리소스입니다."),
    
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "A002", "권한이 없습니다."),
    
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D001", "데이터베이스 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "D002", "외부 API 호출 중 오류가 발생했습니다.");
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}