package ru.beresta.svs.vacationpay.service.calendar;

import ru.beresta.svs.vacationpay.model.Country;

import java.time.LocalDate;
import java.util.List;

public interface HolidayCalendarService {
    List<LocalDate> get(LocalDate startDate, LocalDate endDate, Country country);
}