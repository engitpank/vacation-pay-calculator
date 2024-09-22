package ru.beresta.svs.vacationpay.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.beresta.svs.vacationpay.model.Country;

import java.math.BigDecimal;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "vacationpay.average-working-days")
public class WorkingDaysConfig {
    private BigDecimal defaultMode;
    private Map<Country, BigDecimal> countries;

    public BigDecimal getAverageWorkingDaysForCountry(Country country) {
        return countries.getOrDefault(country, defaultMode);
    }

    public BigDecimal getDefaultMode() {
        return defaultMode;
    }

    public Map<Country, BigDecimal> getCountries() {
        return countries;
    }

    public void setDefaultMode(BigDecimal defaultMode) {
        this.defaultMode = defaultMode;
    }

    public void setCountries(Map<Country, BigDecimal> countries) {
        this.countries = countries;
    }
}