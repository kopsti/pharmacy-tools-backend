package gr.appleton.ms.pharmacytools.config;

import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The RestExceptionHandler, handling uncaught exceptions and mapping to valid responses.
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle exceptions of type AccessDeniedException.
     *
     * @param e the exception
     * @return the response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonModel> handleAccessDeniedException(final AccessDeniedException e) {
        log.warn("Handling AccessDeniedException exception with message {}", e.getMessage());
        return ResponseEntity
            .ok(new CommonModel(null, ClientResponses.UNAUTH.getCode(), ClientResponses.UNAUTH.getMessage()));
    }

    /**
     * Handle all other exceptions.
     *
     * @param e the exception
     * @return the response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonModel> handleException(final Exception e) {
        log.warn("Handling exception {} with message {}", e.getClass(), e.getMessage());
        return ResponseEntity
            .ok(new CommonModel(null, ClientResponses.NOK.getCode(), ClientResponses.NOK.getMessage()));
    }


}
