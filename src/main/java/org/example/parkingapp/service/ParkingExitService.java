package org.example.parkingapp.service;


import lombok.AllArgsConstructor;
import org.example.parkingapp.exception.VehicleNotFoundException;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
@AllArgsConstructor
public class ParkingExitService implements ParkingService {

    private final VehicleRepository vehicleRepository;

    @Transactional(propagation = Propagation.REQUIRED,
            timeout = 5,
            rollbackFor = Exception.class
    )
    public VehicleEntity exitVehicle(String vehicleNumber) {
        VehicleEntity vehicle = vehicleRepository.findByVehicleNumberAndExitTimeIsNull(normalizeNumber(vehicleNumber)).orElseThrow(() -> new VehicleNotFoundException("Car with number " + vehicleNumber + " not found on parking")
        );
        LocalDateTime actualExitTime = LocalDateTime.now();
        vehicle.registerExit(actualExitTime);
        return vehicleRepository.save(vehicle);
    }
}
