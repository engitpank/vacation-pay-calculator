package ru.beresta.svs.vacationpay.service.calendar;

import org.springframework.stereotype.Service;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.DayType;
import ru.beresta.svs.vacationpay.model.ProductionCalendar;
import ru.beresta.svs.vacationpay.service.calendar.providers.ProductionCalendarProvider;
import ru.beresta.svs.vacationpay.service.calendar.providers.ProductionCalendarProviderFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class HolidayCalendarServiceImpl implements HolidayCalendarService {
    private final ProductionCalendarProviderFactory productionCalendarProviderFactory;

    public HolidayCalendarServiceImpl(ProductionCalendarProviderFactory productionCalendarProviderFactory) {
        this.productionCalendarProviderFactory = productionCalendarProviderFactory;
    }

    @Override
    public List<LocalDate> get(LocalDate startDate, LocalDate endDate, Country country) {
        ProductionCalendarProvider calendarProvider = productionCalendarProviderFactory.getCalendar(country);

        return getYearsInRange(startDate, endDate).stream()
                .flatMap(year -> extractHolidaysInRange(calendarProvider.get(year).getDays(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private Set<Integer> getYearsInRange(LocalDate startDate, LocalDate endDate) {
        return IntStream.rangeClosed(startDate.getYear(), endDate.getYear())
                .boxed()
                .collect(Collectors.toSet());
    }

    private Stream<LocalDate> extractHolidaysInRange(
            List<ProductionCalendar.ProductionDay> calendar,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return calendar.stream()
                .filter(prodDay -> isWithinRange(prodDay.getDate(), startDate, endDate))
                .filter(prodDay -> DayType.HOLIDAY.equals(prodDay.getType()))
                .map(ProductionCalendar.ProductionDay::getDate);
    }

    private boolean isWithinRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate) || date.isBefore(endDate));
    }
}