package ru.beresta.svs.vacationpay.service.factory;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.service.calendar.providers.ProductionCalendarProvider;
import ru.beresta.svs.vacationpay.service.calendar.providers.local.CsvAnyCountryProductionCalendarProvider;
import ru.beresta.svs.vacationpay.service.calendar.providers.local.CsvProvidersInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductionCalendarProviderFactory extends AbstractByCountryFactory<List<ProductionCalendarProvider>> {

    private final Map<Country, List<ProductionCalendarProvider>> providers;

    public ProductionCalendarProviderFactory(
            List<ProductionCalendarProvider> providerList,
            CsvProvidersInitializer csvProvidersInitializer) {
        List<CsvAnyCountryProductionCalendarProvider> csvProvidersList = csvProvidersInitializer.getCsvForAllCountriesProviders();
        providers = Stream.concat(providerList.stream(), csvProvidersList.stream())
                .collect(Collectors.groupingBy(
                        ProductionCalendarProvider::getCountry, Collectors.collectingAndThen(Collectors.toList(), list ->
                        {
                            list.sort(Comparator.comparingInt(ProductionCalendarProvider::getPriority));
                            return List.copyOf(list);
                        })
                ));
    }

    @Override
    protected List<ProductionCalendarProvider> getEntity(Country country) {
        return providers.get(country);
    }
}