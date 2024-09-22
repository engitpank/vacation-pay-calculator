package ru.beresta.svs.vacationpay.service.calendar.providers.external;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.beresta.svs.vacationpay.config.env.CalendarProviderConfig;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.ProductionCalendar;
import ru.beresta.svs.vacationpay.service.calendar.ProductionCalendarProviderException;
import ru.beresta.svs.vacationpay.service.calendar.providers.ProductionCalendarProvider;

@Component
public class ProductionCalendarProviderRussia implements ProductionCalendarProvider {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ProductionCalendarProviderRussia(RestTemplate restTemplate, CalendarProviderConfig providerConfig) {
        this.baseUrl = providerConfig.getUrlForCountry(Country.RUS);
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(cacheNames = "calendar-provider-russia")
    public ProductionCalendar get(int year) {
        final ParameterizedTypeReference<ProductionCalendar> typeReference = new ParameterizedTypeReference<>() {
        };
        try {
            final ResponseEntity<ProductionCalendar> responseEntity = restTemplate.exchange(
                    baseUrl + "?year={year}", HttpMethod.GET, null, typeReference, year);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new ProductionCalendarProviderException("Unexpected response from external service: " + responseEntity.getStatusCode());
            }
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new ProductionCalendarProviderException("Error fetching Production Calendar for year: " + year, ex);
        }
    }

    @Override
    public Country getCountry() {
        return Country.RUS;
    }

    @Override
    public int getPriority() {
        return 10;
    }
}