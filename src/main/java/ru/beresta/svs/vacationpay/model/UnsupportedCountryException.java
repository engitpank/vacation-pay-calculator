package ru.beresta.svs.vacationpay.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The exception thrown when trying to execute action for an unsupported country.
 */
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UnsupportedCountryException extends IllegalArgumentException implements BusinessError {
    public UnsupportedCountryException(String message) {
        super(message);
    }
}