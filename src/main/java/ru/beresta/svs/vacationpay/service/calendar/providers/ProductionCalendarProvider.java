package ru.beresta.svs.vacationpay.service.calendar.providers;

import ru.beresta.svs.vacationpay.model.ProductionCalendar;

public interface ProductionCalendarProvider {
    ProductionCalendar get(int year);
}