package org.example.parkingapp.service;

import lombok.AllArgsConstructor;
import org.example.parkingapp.config.ParkingProperties;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ParkingReportService {
    private final VehicleRepository vehicleRepository;
    private final ParkingProperties parkingProperties;
    public Map<String, Object> generateReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        int occupied = vehicleRepository.countByExitTimeIsNull();
        Double avgTime = calculateAverageParkingTime(start, end);

        Map<String, Object> report = new HashMap<>();
        report.put("occupied_spots", occupied);
        report.put("free_spots", parkingProperties.getTotalSpots() - occupied);
        report.put("average_time_minutes", avgTime != null ? Math.round(avgTime) : 0);
        report.put("period", startDate + " - " + endDate);

        return report;
    }
    public double calculateAverageParkingTime(LocalDateTime start, LocalDateTime end) {
        List<VehicleEntity> vehicles = vehicleRepository.findByEntryTimeBetween(start, end);
        return vehicles.stream()
                .mapToLong(v -> Duration.between(v.getEntryTime(), v.getExitTime()).getSeconds())
                .average()
                .orElse(0);
    }
}
