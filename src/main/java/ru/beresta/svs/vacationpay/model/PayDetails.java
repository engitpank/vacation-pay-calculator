package ru.beresta.svs.vacationpay.model;

import java.math.BigDecimal;

public class PayDetails {
    private final BigDecimal amount;
    private final String countryCode;
    private final String currencyCode;

    public PayDetails(BigDecimal amount, Country country) {
        this.amount = amount;
        this.countryCode = country.getCurrencyCode();
        this.currencyCode = country.getCurrencyCode();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}