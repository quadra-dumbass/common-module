package com.quadra.common.controllerAdvice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quadra.common.controllerAdvice.dto.ApiResponse;
import com.quadra.common.controllerAdvice.exception.CustomException;
import com.quadra.common.controllerAdvice.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;

    public GlobalControllerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<Object>> handleBusinessException(CustomException e) {
        log.error("BusinessException: {}", e.getMessage(), e);
        
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<Object> response = ApiResponse.error(e.getMessage(), errorCode.getCode());
        
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage());
        
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });
        
        String message = String.join(", ", errors);
        ApiResponse<Object> response = ApiResponse.error(message, ErrorCode.VALIDATION_ERROR.getCode());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        ErrorCode errorcode = ErrorCode.INTERNAL_SERVER_ERROR;
        ApiResponse<Object> response = ApiResponse.error(
            errorcode.getMessage(),
            errorcode.getCode()
        );
        
        return ResponseEntity.status(errorcode.getHttpStatus()).body(response);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        
        if (parameterType.equals(ApiResponse.class)) {
            return false;
        }

        if (ResponseEntity.class.equals(parameterType)) {
            return false;
        }
        
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        ApiResponse<?> apiResponse;

        if(body instanceof String){
            apiResponse = ApiResponse.success((String) body);
        }else{
            apiResponse = ApiResponse.success(body);
        }

        if(MappingJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)){
            return apiResponse;
        }
        try {
            response.getHeaders().set("Content-Type", "application/json");
            return objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}