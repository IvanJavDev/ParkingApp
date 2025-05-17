package org.example.parkingapp.service;
import org.example.parkingapp.model.enums.VehicleType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import org.example.parkingapp.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingEntryServiceTest {

    @Mock
    VehicleRepository vehicleRepo;
    @InjectMocks
    ParkingEntryService service;
    LocalDateTime time = LocalDateTime.now();

    @Test
    void registerNewVehicle() {
        when(vehicleRepo.existsByVehicleNumberAndExitTimeIsNull("ABC123")).thenReturn(false);
        assertNotNull(service.registerVehicle("ABC123", VehicleType.CAR, time));
    }

    @Test
    void rejectDuplicateVehicle() {
        when(vehicleRepo.existsByVehicleNumberAndExitTimeIsNull("ABC123")).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
                () -> service.registerVehicle("ABC123", VehicleType.CAR, time));
    }
}