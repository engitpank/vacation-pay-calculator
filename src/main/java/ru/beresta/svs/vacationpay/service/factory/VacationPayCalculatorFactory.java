package ru.beresta.svs.vacationpay.service.factory;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.service.VacationPayCalculator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class VacationPayCalculatorFactory extends AbstractByCountryFactory<VacationPayCalculator> {

    private final Map<Country, VacationPayCalculator> calculators;

    public VacationPayCalculatorFactory(List<VacationPayCalculator> calculatorList) {
        this.calculators = calculatorList.stream()
                .collect(Collectors.toMap(VacationPayCalculator::getCountry, Function.identity()));
    }

    @Override
    protected VacationPayCalculator getEntity(Country country) {
        return calculators.get(country);
    }
}