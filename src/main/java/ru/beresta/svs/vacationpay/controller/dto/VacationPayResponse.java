package ru.beresta.svs.vacationpay.controller.dto;

import ru.beresta.svs.vacationpay.model.PayDetails;

import java.math.BigDecimal;

public class VacationPayResponse {
    private final BigDecimal vacationPay;

    public VacationPayResponse(BigDecimal bigDecimal) {
        this.vacationPay = bigDecimal;
    }

    public static VacationPayResponse from(PayDetails details) {
        return new VacationPayResponse(details.getAmount());
    }

    public BigDecimal getVacationPay() {
        return vacationPay;
    }
}