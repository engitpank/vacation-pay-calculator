package ru.beresta.svs.vacationpay.service.calendar.providers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.service.calendar.providers.external.ProductionCalendarProviderRussia;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductionCalendarProviderFactory {

    private static final Map<Country, ProductionCalendarProvider> PROVIDERS = new HashMap<>();

    static {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        PROVIDERS.put(Country.RUS, new ProductionCalendarProviderRussia(builder));
        PROVIDERS.put(Country.BLR, new ProductionCalendarProviderRussia(builder));
    }

    public ProductionCalendarProvider getCalendar(Country country) {
        ProductionCalendarProvider provider = PROVIDERS.get(country);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported calendar provider for country: " + country);
        }
        return provider;
    }
}