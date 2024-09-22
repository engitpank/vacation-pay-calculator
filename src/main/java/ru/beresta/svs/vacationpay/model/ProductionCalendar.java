package ru.beresta.svs.vacationpay.model;

import java.time.LocalDate;
import java.util.List;

public class ProductionCalendar {
    private List<ProductionDay> days;

    public ProductionCalendar() {
    }

    public ProductionCalendar(List<ProductionDay> days) {
        this.days = days;
    }

    public static class ProductionDay {
        private DayType type;
        private LocalDate date;

        public ProductionDay() {
        }

        public DayType getType() {
            return type;
        }

        public LocalDate getDate() {
            return date;
        }

        @Override
        public String toString() {
            return "ProductionDay{" +
                    "type='" + type + '\'' +
                    ", date=" + date +
                    '}';
        }
    }

    public List<ProductionDay> getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "ProductionCalendarResponse{" +
                ", productionDay=" + days +
                '}';
    }
}