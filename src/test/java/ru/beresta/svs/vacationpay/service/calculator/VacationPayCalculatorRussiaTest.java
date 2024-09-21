package ru.beresta.svs.vacationpay.service.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

class VacationPayCalculatorRussiaTest {

    @Test
    void doCalculate() {
        VacationPayCalculator calculator = new VacationPayCalculatorRussia(2, RoundingMode.FLOOR, BigDecimal.valueOf(29.3));

        BigDecimal vacationPay = calculator.calculate(BigDecimal.valueOf(50000), 7);

        Assertions.assertEquals(BigDecimal.valueOf(11945.36), vacationPay);
    }
}