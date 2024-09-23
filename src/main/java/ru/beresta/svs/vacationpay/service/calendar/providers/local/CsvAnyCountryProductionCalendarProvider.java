package ru.beresta.svs.vacationpay.service.calendar.providers.local;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.DayType;
import ru.beresta.svs.vacationpay.model.ProductionCalendar;
import ru.beresta.svs.vacationpay.service.calendar.ProductionCalendarProviderException;
import ru.beresta.svs.vacationpay.service.calendar.providers.ProductionCalendarProvider;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvAnyCountryProductionCalendarProvider implements ProductionCalendarProvider {
    private final Country country;
    private final int priority;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String BASE_PATH = "datasource/calendar";


    public CsvAnyCountryProductionCalendarProvider(Country country, int priority) {
        this.country = country;
        this.priority = priority;
    }

    @Override
    public ProductionCalendar get(int year) {
        Path filePath = constructFilePath(year);

        try (Reader reader = Files.newBufferedReader(filePath)) {
            List<ProductionCalendar.ProductionDay> productionDays = parseCsv(reader);
            return new ProductionCalendar(productionDays);
        } catch (IOException e) {
            throw new ProductionCalendarProviderException("Error extracting data from csv-file: " + filePath, e);
        }
    }

    private Path constructFilePath(int year) {
        return Paths.get(BASE_PATH, country.name().toLowerCase(), year + ".csv");
    }

    private List<ProductionCalendar.ProductionDay> parseCsv(Reader reader) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("type", "date")
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(reader);

        List<ProductionCalendar.ProductionDay> productionDayList = new ArrayList<>();

        for (CSVRecord record : records) {
            DayType type = DayType.valueOf(record.get("type"));
            LocalDate date = LocalDate.parse(record.get("date"), FORMATTER);
            productionDayList.add(new ProductionCalendar.ProductionDay(type, date));
        }

        return productionDayList;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}