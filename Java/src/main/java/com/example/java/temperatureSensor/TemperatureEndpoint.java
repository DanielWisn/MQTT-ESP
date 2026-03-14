package com.example.java.temperatureSensor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/temperature")
public class TemperatureEndpoint {
    private final SseService sseService;
    private TemperatureService temperatureService;

    public TemperatureEndpoint(TemperatureService temperatureService, SseService sseService) {
        this.temperatureService = temperatureService;
        this.sseService = sseService;
    }

    @GetMapping(path = "/latest")
    public Temperature getLatestTemperature() {
        return this.temperatureService.getLatestTemperature();
    }

    @GetMapping(path = "/history/{date}")
    public Temperature getTemperature(@PathVariable LocalDateTime date) {
        return this.temperatureService.getTemperatureHistory(date);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamTemperature() {
        return sseService.subscribe();
    }
}