package ru.beresta.svs.vacationpay.service.calculator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.beresta.svs.vacationpay.model.BusinessError;

/**
 * An exception thrown when sending incorrect data for calculating vacation pay.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VacationPayCalculationException extends RuntimeException implements BusinessError {
    public VacationPayCalculationException(String message) {
        super(message);
    }
}