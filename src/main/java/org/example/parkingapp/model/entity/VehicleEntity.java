package org.example.parkingapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.parkingapp.model.enums.VehicleType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "vehicles",indexes = {
        @Index(name = "idx_vehicle_exit_time", columnList = "exit_time"),
        @Index(name = "idx_vehicle_entry_time", columnList = "entry_time")
})
public class VehicleEntity {

    public VehicleEntity(String vehicleNumber, VehicleType type, LocalDateTime entryTime) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = type;
        this.entryTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "vehicle_number", nullable = false, unique = true)
    @NotBlank(message = "Vehicle number cannot be blank")
    String vehicleNumber;

    @Column(name = "vehicle_type",nullable = false)
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;

    @Column(name = "entry_time", nullable = false)
    LocalDateTime entryTime;

    @Column(name = "exit_time")
    LocalDateTime exitTime;

    public void registerExit(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}
