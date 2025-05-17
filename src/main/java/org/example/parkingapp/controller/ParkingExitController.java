package org.example.parkingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.parkingapp.exception.VehicleNotFoundException;
import org.example.parkingapp.model.dto.VehicleExitRequest;
import org.example.parkingapp.service.ParkingExitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/parking")
@Tag(name = "Parking API v1", description = "Версия 1 API для управления парковкой")
public class ParkingExitController {

    private final ParkingExitService parkingExitService;

    @Operation(
            summary = "Регистрация выезда",
            description = "Регистрирует выезд автомобиля с парковки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный выезд"),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
            })
    @PostMapping("/exit")
    public ResponseEntity<?> registerExit(
            @Parameter(description = "Номер автомобиля",example = "А123БВ77")
            @RequestBody VehicleExitRequest request
            ) {
        try {
             parkingExitService.exitVehicle(request.getVehicleNumber());
            return ResponseEntity.ok("Время выезда с парковки" + LocalDateTime.now());
        } catch (VehicleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
