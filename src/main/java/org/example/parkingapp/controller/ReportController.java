package org.example.parkingapp.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.example.parkingapp.service.ParkingReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static java.util.Collections.singletonMap;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ParkingReportService reportService;

    @GetMapping
    public ResponseEntity<?> getReport(
            @Parameter(description = "Дата въезда на парковку")
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Дата выезда с парковки")
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest()
                    .body(singletonMap("Ошибка", "Время въезда должно быть перед временем выезда"));
        }
        return ResponseEntity.ok(reportService.generateReport(startDate, endDate));
    }
}
