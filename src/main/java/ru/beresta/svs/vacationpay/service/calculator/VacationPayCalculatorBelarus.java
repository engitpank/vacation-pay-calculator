package ru.beresta.svs.vacationpay.service.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VacationPayCalculatorBelarus extends AbstractVacationPayCalculator {
    private final BigDecimal averageWorkingDaysPerYear;

    public VacationPayCalculatorBelarus(int precision, RoundingMode roundingMode, BigDecimal averageWorkingDaysPerYear) {
        super(precision, roundingMode);
        this.averageWorkingDaysPerYear = averageWorkingDaysPerYear;
    }

    @Override
    protected BigDecimal doCalculate(BigDecimal averageSalary, int vacationDays) {
        return BigDecimal.valueOf(vacationDays).multiply(calculatePayPerDay(averageSalary));
    }

    private BigDecimal calculatePayPerDay(BigDecimal averageSalary) {
        return averageSalary.divide(averageWorkingDaysPerYear, precision, roundingMode);
    }
}