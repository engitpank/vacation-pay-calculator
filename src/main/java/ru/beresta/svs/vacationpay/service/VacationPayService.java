package ru.beresta.svs.vacationpay.service;

import org.springframework.lang.Nullable;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.PayDetails;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayService {
    PayDetails calculate(BigDecimal averageSalary, int vacationDays, @Nullable Country country);

    PayDetails calculate(BigDecimal averageSalary, LocalDate startDate, LocalDate endDate, @Nullable Country country);
}