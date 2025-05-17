package org.example.parkingapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.*;

import org.example.parkingapp.config.ParkingProperties;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.model.enums.VehicleType;
import org.example.parkingapp.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingReportServiceTest {

    @Mock
    VehicleRepository vehicleRepo;
    @Mock
    ParkingProperties parkingProps;
    @InjectMocks ParkingReportService service;

    @Test
    void generateBasicReport() {
        // Настройка моков
        when(parkingProps.getTotalSpots()).thenReturn(100);
        when(vehicleRepo.countByExitTimeIsNull()).thenReturn(30);
        when(vehicleRepo.findByEntryTimeBetween(any(), any()))
                .thenReturn(Arrays.asList(
                        new VehicleEntity("A1", VehicleType.CAR,
                                LocalDateTime.now().minusHours(2)),
                        new VehicleEntity("A2", VehicleType.CAR,
                                LocalDateTime.now().minusHours(1))
                ));


        Map<String, Object> report = service.generateReport(
                LocalDate.now(),
                LocalDate.now()
        );


        assertEquals(30, report.get("occupied_spots"));
        assertEquals(70, report.get("free_spots"));
        assertTrue((Long)report.get("average_time_minutes") > 0);
    }
}
