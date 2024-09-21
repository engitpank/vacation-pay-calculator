package ru.beresta.svs.vacationpay.service.calendar.providers.external;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.beresta.svs.vacationpay.model.ProductionCalendar;
import ru.beresta.svs.vacationpay.service.calendar.providers.ProductionCalendarProvider;

import java.time.Duration;

public class ProductionCalendarProviderRussia implements ProductionCalendarProvider {
    private final RestTemplate restTemplate;
    //    @Value("${app.external.production-calendar}")
    private final String baseUrl = "http://localhost:8081/prod-days/";

    public ProductionCalendarProviderRussia(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Override
    public ProductionCalendar get(int year) {
        final ParameterizedTypeReference<ProductionCalendar> typeReference = new ParameterizedTypeReference<>() {
        };
        try {
            final ResponseEntity<ProductionCalendar> responseEntity = restTemplate.exchange(
                    baseUrl + "?year={year}", HttpMethod.GET, null, typeReference, year);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unexpected response from external service: " + responseEntity.getStatusCode());
            }
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Error fetching Production Calendar for year: " + year, ex);
        }
    }
}