package ru.beresta.svs.vacationpay.service.calculator;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Component
public class VacationPayCalculatorFactory {

    private static final Map<Country, VacationPayCalculator> CALCULATORS = new HashMap<>();

    static {
        CALCULATORS.put(Country.RUS, new VacationPayCalculatorRussia(2, RoundingMode.FLOOR, BigDecimal.valueOf(29.3)));
        CALCULATORS.put(Country.BLR, new VacationPayCalculatorBelarus(4, RoundingMode.FLOOR, BigDecimal.valueOf(20)));
    }

    public static VacationPayCalculator getCalculator(Country country) {
        VacationPayCalculator calculator = CALCULATORS.get(country);
        if (calculator == null) {
            throw new IllegalArgumentException("Unsupported vacation pay calculation for country: " + country);
        }
        return calculator;
    }
}