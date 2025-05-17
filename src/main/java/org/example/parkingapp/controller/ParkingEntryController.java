package org.example.parkingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.parkingapp.model.dto.VehicleEntryRequest;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.model.enums.VehicleType;
import org.example.parkingapp.service.ParkingEntryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/parking")
@Tag(name = "Parking API v1", description = "Версия 1 API для управления парковкой")
public class ParkingEntryController {

    private final ParkingEntryService parkingEntryService;

    @Operation(
            summary = "Регистрация въезда",
            description = "Регистрирует въезд автомобиля на парковку",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешная регистрация"),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
            })
    @PostMapping("/entry")
    public ResponseEntity<String> vehicleEntry(
            @RequestBody VehicleEntryRequest request ) {
        try {
            parkingEntryService.registerVehicle(
                    request.getVehicleNumber(),
                    request.getVehicleType(),
                    LocalDateTime.now()
            );
            return ResponseEntity.ok("Время въезда на парковку: " + LocalDateTime.now());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

