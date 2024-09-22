package ru.beresta.svs.vacationpay.service.calculator;

import ru.beresta.svs.vacationpay.config.env.PrecisionConfig;
import ru.beresta.svs.vacationpay.config.env.RoundingConfig;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractVacationPayCalculator implements VacationPayCalculator {

    protected final int precision;
    protected final RoundingMode roundingMode;

    protected AbstractVacationPayCalculator(
            PrecisionConfig precisionConfig,
            RoundingConfig roundingConfig
    ) {
        this.precision = precisionConfig.getPrecisionModeForCountry(getCountry());
        this.roundingMode = roundingConfig.getRoundingModeForCountry(getCountry());
    }

    @Override
    public final BigDecimal calculate(BigDecimal averageSalary, int vacationDays) {
        validateInputs(averageSalary, vacationDays);
        return doCalculate(averageSalary, vacationDays);
    }

    // Абстрактный метод, который будет реализован в подклассах для специфичных расчетов
    protected abstract BigDecimal doCalculate(BigDecimal averageSalary, int vacationDays);

    // Метод валидации входных данных
    private void validateInputs(BigDecimal averageSalary, int vacationDays) {
        if (vacationDays <= 0) {
            throw new VacationPayCalculationException("Vacation days must be greater than zero");
        }
        if (averageSalary == null || averageSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VacationPayCalculationException("Average salary must be greater than zero");
        }
    }
}