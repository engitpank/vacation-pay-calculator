package ru.beresta.svs.vacationpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.PayDetails;
import ru.beresta.svs.vacationpay.service.calculator.VacationPayCalculatorFactory;
import ru.beresta.svs.vacationpay.service.calendar.HolidayCalendarService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class VacationPayServiceImpl implements VacationPayService {
    private final HolidayCalendarService calendarHolidaysService;
    private final Country DEFAULT_COUNTRY = Country.RUS;

    private static final Logger log = LoggerFactory.getLogger(VacationPayServiceImpl.class);

    public VacationPayServiceImpl(HolidayCalendarService calendarHolidaysService) {
        this.calendarHolidaysService = calendarHolidaysService;
    }

    @Override
    public PayDetails calculate(BigDecimal averageSalary, int vacationDays, @Nullable Country country) {
        log.info("Calculating vacation pay with averageSalary: {}, vacationDays: {}, country: {}", averageSalary, vacationDays, country);

        country = resolveCountry(country);

        VacationPayCalculator payCalculator = VacationPayCalculatorFactory.getCalculator(country);
        log.debug("Selected VacationPayCalculator for country: {}", country);

        BigDecimal totalPay = payCalculator.calculate(averageSalary, vacationDays);
        log.info("Calculated total vacation pay: {} for vacationDays: {} in country: {}", totalPay, vacationDays, country);

        return new PayDetails(totalPay, country);
    }

    @Override
    public PayDetails calculate(BigDecimal averageSalary, LocalDate startDate, LocalDate endDate, @Nullable Country country) {
        log.info("Calculating vacation pay for period from {} to {} with averageSalary: {}", startDate, endDate, averageSalary);

        country = resolveCountry(country);

        int vacationDays = calculateVacationDays(startDate, endDate, country);

        return calculate(averageSalary, vacationDays, country);
    }

    private int calculateVacationDays(LocalDate startDate, LocalDate endDate, Country country) {
        log.debug("Fetching holidays for country: {} between {} and {}", country, startDate, endDate);

        int holidayCount = calendarHolidaysService.get(startDate, endDate, country).size();
        log.debug("Number of holidays between {} and {}: {}", startDate, endDate, holidayCount);

        int totalVacationDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include last day
        log.debug("Total vacation days (including holidays): {}", totalVacationDays);

        int resultDays = totalVacationDays - holidayCount;
        log.info("Final vacation days after excluding holidays: {}", resultDays);

        return resultDays;
    }

    private Country resolveCountry(@Nullable Country country) {
        Country resolvedCountry = (country == null) ? DEFAULT_COUNTRY : country;
        log.debug("Resolved country: {}", resolvedCountry);
        return resolvedCountry;
    }
}