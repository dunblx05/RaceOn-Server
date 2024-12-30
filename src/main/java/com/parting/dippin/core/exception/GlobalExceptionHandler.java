package com.parting.dippin.core.exception;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.BAD_REQUEST;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INTERNAL_SERVER_ERROR;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_URL;

import com.parting.dippin.core.base.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            HandlerMethodValidationException.class
    })
    public ResponseEntity<ErrorResponse> resolveIllegalArgumentException(
            Exception exception
    ) {
        return ResponseEntity.status(BAD_REQUEST.httpStatusCode())
                .body(ErrorResponse.from(BAD_REQUEST));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<ErrorResponse> resolveNoHandlerFoundException(
            NoHandlerFoundException exception
    ) {
        return ResponseEntity.status(INVALID_URL.httpStatusCode())
                .body(ErrorResponse.from(INVALID_URL));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> resolveIllegalStateException(
            IllegalStateException exception
    ) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.httpStatusCode())
                .body(ErrorResponse.from(INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> resolveBusinessException(
            BusinessException exception
    ) {
        return ResponseEntity.status(exception.getHttpStatusCode())
                .body(ErrorResponse.from(exception.getCodeAndMessage()));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> resolveCommonException(
            CommonException exception
    ) {
        return ResponseEntity.status(exception.getHttpStatusCode())
                .body(ErrorResponse.from(exception.getCodeAndMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> resolveServerError(
            Exception exception
    ) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.httpStatusCode())
                .body(ErrorResponse.from(INTERNAL_SERVER_ERROR));
    }
}