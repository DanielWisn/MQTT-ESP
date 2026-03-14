package com.example.java.temperatureSensor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;
    private SseService sseService;

    public TemperatureService(TemperatureRepository temperatureRepository,SseService sseService) {
        this.temperatureRepository = temperatureRepository;
        this.sseService = sseService;
    }

    public void updateTemperature(Temperature temperature) {
        temperatureRepository.saveTemperature(temperature);
        this.sseService.notifyClients(temperature);
    }

    public Temperature getLatestTemperature() {
        return this.temperatureRepository.getLatestTemperature();
    }

    public Temperature getTemperatureHistory(LocalDateTime dateTime) {
        return this.temperatureRepository.getTemperatureHistory(dateTime);
    }
}
