package com.github.ludmylla.foodapi.api.exceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.github.ludmylla.foodapi.Util.Messages;
import com.github.ludmylla.foodapi.domain.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        }else if(rootCause instanceof PropertyBindingException){
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.MESSAGE_INCOMPRESSIBLE;
        String details = "The request body is invalid, check syntax error.";

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MESSAGE_INCOMPRESSIBLE;
        String details = String.format("The property '%s' received the value '%s' " +
                "which is of an invalid type. Correct and enter a compatible value %s",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(Messages.MSG_ERROR_GENERIC_USER_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MESSAGE_INCOMPRESSIBLE;
        String details = String.format("The property does not exist '%s'. Fix or remove this property", path);

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(Messages.MSG_ERROR_GENERIC_USER_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers,status,request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String details = String.format("The parameter URL '%s' received the value '%s' " +
                "which is an invalid type. Correct and enter a value compatible with the type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String details = String.format("The resource '%s', that you tried to access and non-existent.", ex.getRequestURL());

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;

        String details = String.format(Messages.MSG_ERROR_GENERIC_USER_FINAL);

        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String details = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String details = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERROR_BUSINESS;
        String details = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, details)
                .userMessage(details)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(body == null) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .userMessage(Messages.MSG_ERROR_GENERIC_USER_FINAL)
                    .build();
        }else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .status(status.value())
                    .title((String) body)
                    .userMessage(Messages.MSG_ERROR_GENERIC_USER_FINAL)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .details(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

}
