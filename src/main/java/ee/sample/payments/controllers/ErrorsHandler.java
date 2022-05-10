package ee.sample.payments.controllers;

import ee.sample.payments.exceptions.PaymentCancellationError;
import ee.sample.payments.exceptions.PaymentInvalidError;
import ee.sample.payments.exceptions.PaymentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class ErrorsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorsHandler.class.getName());

    @ExceptionHandler({SQLException.class, IOException.class})
    public ResponseEntity<String> handleException(HttpServletRequest request, Exception ex) {
        logWarning("Exception occurred URL='%s', exception: %s", request.getRequestURL(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal error");
    }

    @ExceptionHandler(value = {PaymentNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(HttpServletRequest request, Exception ex) {
        logWarning("Item not found (%s) - %s", request.getRequestURL(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PaymentInvalidError.class)
    public ResponseEntity<String> handleInvalidRequestException(HttpServletRequest request, Exception ex) {
        logWarning("Bad data (%s) - %s", request.getRequestURL(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PaymentCancellationError.class)
    public ResponseEntity<String> handleCancellationError(HttpServletRequest request, Exception ex) {
        logWarning("Cancellation error (%s) - %s", request.getRequestURL(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    private void logWarning(String stringFormat, Object... params) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(String.format(stringFormat, params));
        }
    }
}
