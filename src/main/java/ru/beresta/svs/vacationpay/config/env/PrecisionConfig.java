package ru.beresta.svs.vacationpay.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.beresta.svs.vacationpay.model.Country;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "vacationpay.precision")
public class PrecisionConfig {
    private int defaultMode;
    private Map<Country, Integer> countries;

    public Integer getPrecisionModeForCountry(Country country) {
        return countries.getOrDefault(country, defaultMode);
    }

    public int getDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(int defaultMode) {
        this.defaultMode = defaultMode;
    }

    public Map<Country, Integer> getCountries() {
        return countries;
    }

    public void setCountries(Map<Country, Integer> countries) {
        this.countries = countries;
    }
}