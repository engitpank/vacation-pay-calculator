package ru.beresta.svs.vacationpay.service.calculator;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.config.env.MinPayPerDayConfig;
import ru.beresta.svs.vacationpay.config.env.PrecisionConfig;
import ru.beresta.svs.vacationpay.config.env.RoundingConfig;
import ru.beresta.svs.vacationpay.config.env.WorkingDaysConfig;
import ru.beresta.svs.vacationpay.model.Country;

import java.math.BigDecimal;

@Component
public class VacationPayCalculatorRussia extends AbstractVacationPayCalculator {
    private final BigDecimal averageWorkingDaysPerYear;

    public VacationPayCalculatorRussia(PrecisionConfig precisionConfig,
                                       RoundingConfig roundingConfig,
                                       WorkingDaysConfig workingDaysConfig,
                                       MinPayPerDayConfig minPayPerDayConfig) {
        super(precisionConfig, roundingConfig, minPayPerDayConfig, Country.RUS);
        this.averageWorkingDaysPerYear = workingDaysConfig.getAverageWorkingDaysForCountry(country);
    }

    @Override
    protected BigDecimal doCalculate(BigDecimal averageSalary, BigDecimal vacationDays) {
        return vacationDays.multiply(calculatePayPerDay(averageSalary));
    }

    private BigDecimal calculatePayPerDay(BigDecimal averageSalary) {
        return averageSalary.divide(averageWorkingDaysPerYear, precision, roundingMode);
    }
}