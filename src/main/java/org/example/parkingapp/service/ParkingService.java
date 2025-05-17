package org.example.parkingapp.service;

import org.springframework.stereotype.Service;

@Service
public interface ParkingService {
     default String normalizeNumber(String number) {
        return number.trim().toUpperCase();
    }
}
