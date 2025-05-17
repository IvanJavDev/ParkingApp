package org.example.parkingapp.repository;

import org.example.parkingapp.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    boolean existsByVehicleNumberAndExitTimeIsNull(String vehicleNumber);
    Optional<VehicleEntity> findByVehicleNumberAndExitTimeIsNull(String vehicleNumber);

    @Query("SELECT COUNT(v) FROM VehicleEntity v WHERE v.exitTime IS NULL")
    int countByExitTimeIsNull();

    List<VehicleEntity> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);
}
