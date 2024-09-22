package ru.beresta.svs.vacationpay.service.calendar.providers;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.UnsupportedCountryException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductionCalendarProviderFactory {

    private final Map<Country, ProductionCalendarProvider> providers;

    public ProductionCalendarProviderFactory(List<ProductionCalendarProvider> providerList) {
        providers = providerList.stream()
                .collect(Collectors.toMap(ProductionCalendarProvider::getCountry, Function.identity()));
    }

    public ProductionCalendarProvider getCalendar(Country country) {
        ProductionCalendarProvider provider = providers.get(country);
        if (provider == null) {
            throw new UnsupportedCountryException("Unsupported calendar provider for country: " + country);
        }
        return provider;
    }
}