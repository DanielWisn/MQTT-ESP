package com.example.java.temperatureSensor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temperature")
public class temperatureEndpoint {
    private TemperatureService temperatureService;

    public temperatureEndpoint(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping(path = "/get")
    public int gettemperature() {
        return this.temperatureService.getTemperature();
    }
}
