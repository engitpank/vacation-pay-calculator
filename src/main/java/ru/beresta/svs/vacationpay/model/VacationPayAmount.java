package ru.beresta.svs.vacationpay.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VacationPayAmount {
    private static final int PRECISION = 4;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    // TODO: Можно подтягивать из конфига
    private static final BigDecimal AVERAGE_WORKING_DAYS_PER_YEAR = BigDecimal.valueOf(29.3);

    private final BigDecimal value;

    private VacationPayAmount(BigDecimal value) {
        this.value = value;
    }

    public static VacationPayAmount calculate(BigDecimal averageSalary, int vacationDays) {
        BigDecimal amountPay = BigDecimal.valueOf(vacationDays).multiply(calculatePayPerDay(averageSalary));
        return new VacationPayAmount(amountPay);
    }

    private static BigDecimal calculatePayPerDay(BigDecimal averageSalary) {
        return averageSalary.divide(AVERAGE_WORKING_DAYS_PER_YEAR, PRECISION, ROUNDING_MODE);
    }

    public BigDecimal getRoundedValue() {
        return value;
    }
}