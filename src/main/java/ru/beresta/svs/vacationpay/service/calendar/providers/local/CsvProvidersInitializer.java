package ru.beresta.svs.vacationpay.service.calendar.providers.local;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvProvidersInitializer {
    private final List<CsvAnyCountryProductionCalendarProvider> CsvForAllCountriesProviders;

    public CsvProvidersInitializer(@Value("${vacationpay.providers.calendar.default-priority.internal}") int priority) {
        this.CsvForAllCountriesProviders = Arrays.stream(Country.values())
                .map(country -> new CsvAnyCountryProductionCalendarProvider(country, priority))
                .collect(Collectors.toList());
    }

    public List<CsvAnyCountryProductionCalendarProvider> getCsvForAllCountriesProviders() {
        return CsvForAllCountriesProviders;
    }
}