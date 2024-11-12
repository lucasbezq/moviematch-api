package com.ezequiel.moviematch.api.exceptionHandler;

import com.ezequiel.moviematch.domain.exception.BusinessException;
import com.ezequiel.moviematch.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var errorType = ErrorType.RESOURCE_NOT_FOUND;
        var detail = e.getMessage();

        var error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorType = ErrorType.INTERNAL_ERROR;
        var detail = e.getMessage();

        var error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }


    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        var timestamp = OffsetDateTime.now();
        if (body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .timestamp(timestamp)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title(body.toString())
                    .status(status.value())
                    .timestamp(timestamp)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createProblemBuilder(HttpStatusCode status, ErrorType type, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    protected ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                                                              HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorType problemType = ErrorType.INVALID_DATA;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<ApiError.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ApiError error = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

}
