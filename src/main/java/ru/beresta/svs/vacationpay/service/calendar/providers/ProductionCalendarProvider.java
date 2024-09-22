package ru.beresta.svs.vacationpay.service.calendar.providers;

import org.springframework.stereotype.Component;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.ProductionCalendar;

@Component
public interface ProductionCalendarProvider {
    ProductionCalendar get(int year);

    Country getCountry();

    int getPriority();
}