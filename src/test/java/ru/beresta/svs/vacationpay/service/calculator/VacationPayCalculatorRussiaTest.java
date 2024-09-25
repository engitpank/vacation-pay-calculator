package ru.beresta.svs.vacationpay.service.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.beresta.svs.vacationpay.config.env.MinPayPerDayConfig;
import ru.beresta.svs.vacationpay.config.env.PrecisionConfig;
import ru.beresta.svs.vacationpay.config.env.RoundingConfig;
import ru.beresta.svs.vacationpay.config.env.WorkingDaysConfig;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class VacationPayCalculatorRussiaTest {

    @Mock
    PrecisionConfig precisionConfig;

    @Mock
    RoundingConfig roundingConfig;

    @Mock
    WorkingDaysConfig workingDaysConfig;

    @Mock
    MinPayPerDayConfig minPayPerDayConfig;

    @BeforeEach
    void setUp() {
        Mockito.when(precisionConfig.getPrecisionModeForCountry(any())).thenReturn(2);
        Mockito.when(roundingConfig.getRoundingModeForCountry(any())).thenReturn(RoundingMode.FLOOR);
        Mockito.when(workingDaysConfig.getAverageWorkingDaysForCountry(any())).thenReturn(BigDecimal.valueOf(29.3));
        Mockito.when(minPayPerDayConfig.getMinPayForCountry(any())).thenReturn(BigDecimal.valueOf(656.72));
    }

    @Test
    void doCalculate() {
        VacationPayCalculator calculator = new VacationPayCalculatorRussia(precisionConfig, roundingConfig,
                workingDaysConfig, minPayPerDayConfig);

        BigDecimal vacationPay = calculator.calculate(BigDecimal.valueOf(50000), 7);

        Assertions.assertEquals(BigDecimal.valueOf(11945.36), vacationPay);
    }
}