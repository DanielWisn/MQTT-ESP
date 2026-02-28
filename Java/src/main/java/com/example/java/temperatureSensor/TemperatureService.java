package com.example.java.temperatureSensor;

import org.springframework.stereotype.Service;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;

    public int getTemperature() {
        return this.temperatureRepository.getTemperature();
    }
}
