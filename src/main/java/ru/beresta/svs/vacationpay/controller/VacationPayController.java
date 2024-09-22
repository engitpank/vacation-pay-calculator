package ru.beresta.svs.vacationpay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.beresta.svs.vacationpay.controller.dto.VacationPayResponse;
import ru.beresta.svs.vacationpay.controller.dto.VacationRequest;
import ru.beresta.svs.vacationpay.model.PayDetails;
import ru.beresta.svs.vacationpay.service.VacationPayService;

import javax.validation.Valid;

@RestController
public class VacationPayController {
    private static final Logger log = LoggerFactory.getLogger(VacationPayController.class);
    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<VacationPayResponse> calculateVacationPay(
            @Valid @RequestBody VacationRequest request) {
        PayDetails result = processVacationRequest(request);
        return ResponseEntity.ok(VacationPayResponse.from(result));
    }

    private PayDetails processVacationRequest(VacationRequest request) {
        if (request.hasPeriod()) {
            log.info("Received request for calculation vacation pay based on the period from {} to {}",
                    request.getVacationPeriod().getStartDate(), request.getVacationPeriod().getEndDate());
            return vacationPayService.calculate(
                    request.getAverageSalary(),
                    request.getVacationPeriod().getStartDate(),
                    request.getVacationPeriod().getEndDate(),
                    request.getCountry());
        } else {
            log.info("Received request for calculation vacation pay based on the number of days: {}", request.getVacationDays());
            return vacationPayService.calculate(
                    request.getAverageSalary(),
                    request.getVacationDays(),
                    request.getCountry());
        }
    }
}