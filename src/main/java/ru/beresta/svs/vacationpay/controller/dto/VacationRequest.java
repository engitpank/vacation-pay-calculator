package ru.beresta.svs.vacationpay.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;
import ru.beresta.svs.vacationpay.model.Country;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VacationRequest {
    @PositiveOrZero
    @NotNull
    private BigDecimal averageSalary;
    @Positive
    private Integer vacationDays;
    private VacationPeriod vacationPeriod;
    private Country country;

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public VacationPeriod getVacationPeriod() {
        return vacationPeriod;
    }

    public Country getCountry() {
        return country;
    }

    public static class VacationPeriod {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @FutureOrPresent
        private LocalDate startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @FutureOrPresent
        private LocalDate endDate;

        public VacationPeriod() {
        }

        public @FutureOrPresent LocalDate getStartDate() {
            return startDate;
        }

        public @FutureOrPresent LocalDate getEndDate() {
            return endDate;
        }

    }

    @AssertTrue(message = "Either vacationDays or vacationPeriod must be provided, but not both.")
    public boolean isValid() {
        return (vacationDays != null && vacationPeriod == null) || (vacationDays == null && vacationPeriod != null);
    }

    @AssertTrue(message = "Vacation start date cannot be after end date")
    public boolean isValidOrderDates() {
        if (vacationPeriod == null) return true; // true if not passed
        LocalDate startDate = vacationPeriod.startDate;
        LocalDate endDate = vacationPeriod.endDate;
        return startDate.equals(endDate) || startDate.isBefore(endDate);
    }

    @AssertTrue(message = "Difference between start and end date must be no more than one year")
    public boolean isValidDateRange() {
        return vacationPeriod == null || vacationPeriod.startDate.until(vacationPeriod.endDate, ChronoUnit.YEARS) <= 1;
    }

    public boolean hasPeriod() {
        return (vacationPeriod != null);
    }
}