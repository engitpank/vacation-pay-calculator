package ru.beresta.svs.vacationpay.service.calendar;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.beresta.svs.vacationpay.model.BusinessError;

/**
 * An exception thrown when errors occur when interacting with an external production calendar service.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ProductionCalendarProviderException extends RuntimeException implements BusinessError {
    public ProductionCalendarProviderException(String message) {
        super(message);
    }

    public ProductionCalendarProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}