package ru.beresta.svs.vacationpay.service.calendar.providers;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.UnsupportedCountryException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductionCalendarProviderFactory {

    private final Map<Country, List<ProductionCalendarProvider>> providers;

    public ProductionCalendarProviderFactory(List<ProductionCalendarProvider> providerList) {
        providers = providerList.stream()
                .collect(Collectors.groupingBy(
                        ProductionCalendarProvider::getCountry, Collectors.collectingAndThen(Collectors.toList(), list ->
                        {
                            list.sort(Comparator.comparingInt(ProductionCalendarProvider::getPriority));
                            return list;
                        })
                ));
    }

    public List<ProductionCalendarProvider> getCalendars(Country country) {
        List<ProductionCalendarProvider> providerList = providers.get(country);
        if (providerList == null || providerList.isEmpty()) {
            throw new UnsupportedCountryException("Unsupported calendar provider for country: " + country);
        }
        return providerList;
    }
}