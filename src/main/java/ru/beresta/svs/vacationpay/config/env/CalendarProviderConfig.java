package ru.beresta.svs.vacationpay.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.beresta.svs.vacationpay.model.Country;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "vacationpay.providers.calendar.url")
public class CalendarProviderConfig {
    private Map<Country, String> countries;

    public String getUrlForCountry(Country country) {
        return countries.get(country);
    }

    public Map<Country, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<Country, String> countries) {
        this.countries = countries;
    }
}