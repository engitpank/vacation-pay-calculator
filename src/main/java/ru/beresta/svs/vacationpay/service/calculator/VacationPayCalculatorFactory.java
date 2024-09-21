package ru.beresta.svs.vacationpay.service.calculator;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class VacationPayCalculatorFactory {

    private final Map<Country, VacationPayCalculator> calculators;

    public VacationPayCalculatorFactory(List<VacationPayCalculator> calculatorList) {
        this.calculators = calculatorList.stream()
                .collect(Collectors.toMap(VacationPayCalculator::getCountry, Function.identity()));
    }

    public VacationPayCalculator getCalculator(Country country) {
        VacationPayCalculator calculator = calculators.get(country);
        if (calculator == null) {
            throw new IllegalArgumentException("Unsupported vacation pay calculation for country: " + country);
        }
        return calculator;
    }
}