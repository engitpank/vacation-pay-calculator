package ru.beresta.svs.vacationpay.service.calendar.providers.external;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.beresta.svs.vacationpay.model.BusinessError;
/**
 * An exception thrown when errors occur when interacting with an external production calendar service.
 */
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ProductionCalendarClientException extends RuntimeException implements BusinessError {
    public ProductionCalendarClientException(String message) {
        super(message);
    }

    public ProductionCalendarClientException(String message, Throwable cause) {
        super(message, cause);
    }
}