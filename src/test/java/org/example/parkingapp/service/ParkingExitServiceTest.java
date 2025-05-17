package org.example.parkingapp.service;

import org.example.parkingapp.exception.VehicleNotFoundException;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.model.enums.VehicleType;
import org.example.parkingapp.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingExitServiceTest {

    @Mock
    VehicleRepository vehicleRepo;
    @InjectMocks
    ParkingExitService service;

    @Test
    void exitExistingVehicle() {
        VehicleEntity vehicle = new VehicleEntity("ABC123", VehicleType.CAR, LocalDateTime.now());
        when(vehicleRepo.findByVehicleNumberAndExitTimeIsNull("ABC123"))
                .thenReturn(Optional.of(vehicle));

        VehicleEntity result = service.exitVehicle("ABC123");

        assertNotNull(result.getExitTime());
    }

    @Test
    void rejectMissingVehicle() {
        when(vehicleRepo.findByVehicleNumberAndExitTimeIsNull("ABC123"))
                .thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class,
                () -> service.exitVehicle("ABC123"));
    }
}
