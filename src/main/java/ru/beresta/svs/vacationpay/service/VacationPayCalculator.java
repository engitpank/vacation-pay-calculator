package ru.beresta.svs.vacationpay.service;

import ru.beresta.svs.vacationpay.model.Country;

import java.math.BigDecimal;

public interface VacationPayCalculator {
    BigDecimal calculate(BigDecimal averageSalary, int vacationDays);

    Country getCountry();
}