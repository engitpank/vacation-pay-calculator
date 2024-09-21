package ru.beresta.svs.vacationpay.model;

import java.util.Currency;
import java.util.Locale;

public enum Country {
    RUS(new Locale(Constants.DEFAULT_LANGUAGE, "RU")),
    BLR(new Locale(Constants.DEFAULT_LANGUAGE, "BY"));

    private final String countryCode;
    private final String currencyCode;

    Country(Locale locale) {
        this.countryCode = locale.getCountry();
        this.currencyCode = Currency.getInstance(locale).getCurrencyCode();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    private static class Constants {
        public static final String DEFAULT_LANGUAGE = "EN";
    }

    public static Country getCountry(String countryCode) {
        try {
            return Country.valueOf(countryCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported country: " + countryCode);
        }
    }
}