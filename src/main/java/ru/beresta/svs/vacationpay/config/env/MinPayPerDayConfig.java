package ru.beresta.svs.vacationpay.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.beresta.svs.vacationpay.model.Country;

import java.math.BigDecimal;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "vacationpay.min-pay")
public class MinPayPerDayConfig {
    private BigDecimal defaultMode;
    private Map<Country, BigDecimal> countries;

    public BigDecimal getMinPayForCountry(Country country) {
        return countries.getOrDefault(country, defaultMode);
    }

    public BigDecimal getDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(BigDecimal defaultMode) {
        this.defaultMode = defaultMode;
    }

    public Map<Country, BigDecimal> getCountries() {
        return countries;
    }

    public void setCountries(Map<Country, BigDecimal> countries) {
        this.countries = countries;
    }
}