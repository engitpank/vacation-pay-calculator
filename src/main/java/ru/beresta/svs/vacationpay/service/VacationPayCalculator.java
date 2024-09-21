package ru.beresta.svs.vacationpay.service;

import java.math.BigDecimal;

public interface VacationPayCalculator {
    BigDecimal calculate(BigDecimal averageSalary, int vacationDays);
}