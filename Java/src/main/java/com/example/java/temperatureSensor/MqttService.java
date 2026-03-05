package com.example.java.temperatureSensor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MqttService {

    private final TemperatureService temperatureService;

    public MqttService(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    public void handleMessage(String payload) {

        try {
            double value = Double.parseDouble(payload);

            Temperature temperature = new Temperature();
            temperature.setTemperature(value);
            temperature.setDateTime(LocalDateTime.now());

            temperatureService.updateTemperature(temperature);

        } catch (NumberFormatException e) {
            System.out.println("Invalid temperature received: " + payload);
        }
    }
}