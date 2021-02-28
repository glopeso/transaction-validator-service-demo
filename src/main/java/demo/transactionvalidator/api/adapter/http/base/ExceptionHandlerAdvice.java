package demo.transactionvalidator.api.adapter.http.base;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ExceptionHandlerAdvice  extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ExceptionHandlerAdvice(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ErrorResponse errorResponse = createErrorResponseBuilder(status)
                .message(convertMessage(MessagesConstants.MSG_VALIDATION_MISSING_REQUEST_PARAMETER,
                        ex.getParameterName()))
                .build();

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(e);
        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        final ErrorResponse errorResponse = createErrorResponseBuilder(status)
                .message(convertMessage(MessagesConstants.MSG_VALIDATION_INVALID_BODY))
                .build();

        return handleExceptionInternal(e, errorResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ErrorResponse errorResponse = createErrorResponseBuilder(status)
                .message(e.getMessage())
                .build();

        return handleExceptionInternal(e, errorResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final BindingResult bindingResult = ex.getBindingResult();

        final List<ErrorResponse.Field> fields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> ErrorResponse.Field.builder()
                        .name(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = createErrorResponseBuilder(status)
                .message(convertMessage(MessagesConstants.MSG_VALIDATION_ONE_MORE_FIELDS_INVALID))
                .fields(fields)
                .build();

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null) {
            body = createErrorResponseBuilder(status).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(value = DynamoDBMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(final DynamoDBMappingException exception) {
        return createErrorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
    }

    private ErrorResponse.ErrorResponseBuilder createErrorResponseBuilder(HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(status.name());
    }

    public String convertMessage(final String code, final Locale locale, final Object... params) {
        return messageSource.getMessage(code, params, locale);
    }

    public String convertMessage(final String code, final Object... params) {
        return convertMessage(code, null, params);
    }

}
