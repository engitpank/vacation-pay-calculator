package ru.beresta.svs.vacationpay.service.calculator;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.config.env.PrecisionConfig;
import ru.beresta.svs.vacationpay.config.env.RoundingConfig;
import ru.beresta.svs.vacationpay.config.env.WorkingDaysConfig;
import ru.beresta.svs.vacationpay.model.Country;

import java.math.BigDecimal;

@Component
public class VacationPayCalculatorBelarus extends AbstractVacationPayCalculator {
    private final BigDecimal averageWorkingDaysPerYear;

    public VacationPayCalculatorBelarus(
            PrecisionConfig precisionConfig,
            RoundingConfig roundingConfig,
            WorkingDaysConfig workingDaysConfig) {
        super(precisionConfig, roundingConfig, Country.BLR);
        this.averageWorkingDaysPerYear = workingDaysConfig.getAverageWorkingDaysForCountry(country);
    }

    @Override
    protected BigDecimal doCalculate(BigDecimal averageSalary, int vacationDays) {
        return BigDecimal.valueOf(vacationDays).multiply(calculatePayPerDay(averageSalary));
    }

    private BigDecimal calculatePayPerDay(BigDecimal averageSalary) {
        return averageSalary.divide(averageWorkingDaysPerYear, precision, roundingMode);
    }
}