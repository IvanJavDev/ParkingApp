package org.example.parkingapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.parkingapp.model.enums.VehicleType;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleEntryRequest {
    @NotBlank
    private String vehicleNumber;

    @NotNull
    private VehicleType vehicleType;
}
