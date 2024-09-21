package ru.beresta.svs.vacationpay.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.beresta.svs.vacationpay.model.Country;

import java.math.RoundingMode;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "vacationpay.rounding")
public class RoundingConfig {
    private RoundingMode defaultMode;
    private Map<Country, RoundingMode> countries;

    public RoundingMode getRoundingModeForCountry(Country country) {
        return countries.getOrDefault(country, defaultMode);
    }

    public RoundingMode getDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(RoundingMode defaultMode) {
        this.defaultMode = defaultMode;
    }

    public Map<Country, RoundingMode> getCountries() {
        return countries;
    }

    public void setCountries(Map<Country, RoundingMode> countries) {
        this.countries = countries;
    }
}