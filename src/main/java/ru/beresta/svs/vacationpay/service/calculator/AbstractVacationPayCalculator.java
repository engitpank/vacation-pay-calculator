package ru.beresta.svs.vacationpay.service.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.beresta.svs.vacationpay.config.env.MinPayPerDayConfig;
import ru.beresta.svs.vacationpay.config.env.PrecisionConfig;
import ru.beresta.svs.vacationpay.config.env.RoundingConfig;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractVacationPayCalculator implements VacationPayCalculator {

    private static final Logger log = LoggerFactory.getLogger(AbstractVacationPayCalculator.class);
    protected final int precision;
    protected final RoundingMode roundingMode;
    private final BigDecimal minPayPerDay;
    protected final Country country;

    protected AbstractVacationPayCalculator(
            PrecisionConfig precisionConfig,
            RoundingConfig roundingConfig,
            MinPayPerDayConfig minPayPerDayConfig,
            Country country) {
        this.country = country;
        this.precision = precisionConfig.getPrecisionModeForCountry(country);
        this.roundingMode = roundingConfig.getRoundingModeForCountry(country);
        this.minPayPerDay = minPayPerDayConfig.getMinPayForCountry(country);
    }

    @Override
    public final BigDecimal calculate(BigDecimal averageSalary, int vacationDays) {
        validateInputs(averageSalary, vacationDays);

        BigDecimal days = BigDecimal.valueOf(vacationDays);

        BigDecimal totalPay = doCalculate(averageSalary, days);

        return applyMinPayPolicy(totalPay, days);
    }

    protected abstract BigDecimal doCalculate(BigDecimal averageSalary, BigDecimal vacationDays);

    private void validateInputs(BigDecimal averageSalary, int vacationDays) {
        if (vacationDays <= 0) {
            throw new VacationPayCalculationException("Vacation days must be greater than zero");
        }
        if (averageSalary == null || averageSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VacationPayCalculationException("Average salary must be greater than zero");
        }
    }

    private BigDecimal applyMinPayPolicy(BigDecimal totalPay, BigDecimal vacationDays) {
        if (totalPay.compareTo(minPayPerDay) < 0) {
            log.info("Applied minimum pay policy: {} and {} days", totalPay, vacationDays);
            return minPayPerDay.multiply(vacationDays);
        }
        return totalPay;
    }

    @Override
    public Country getCountry() {
        return country;
    }
}