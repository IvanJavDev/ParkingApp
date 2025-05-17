package org.example.parkingapp.service;


import lombok.AllArgsConstructor;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.model.enums.VehicleType;
import org.example.parkingapp.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
@AllArgsConstructor
public class ParkingEntryService implements ParkingService {
    private final VehicleRepository vehicleRepository;

    @Transactional(
            propagation = Propagation.REQUIRED,
            timeout = 5,
            rollbackFor = Exception.class
    )
    public VehicleEntity registerVehicle(String vehicleNumber, VehicleType vehicleType, LocalDateTime entryTime) {


        if (vehicleRepository.existsByVehicleNumberAndExitTimeIsNull(vehicleNumber)) {
            throw new IllegalArgumentException("Vehicle number " + vehicleNumber + " already parked");
        }

        VehicleEntity vehicleEntity = new VehicleEntity(vehicleNumber,vehicleType,entryTime);
        return vehicleRepository.save(vehicleEntity);
    }

}
