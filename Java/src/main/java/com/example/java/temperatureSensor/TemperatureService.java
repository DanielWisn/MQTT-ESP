package com.example.java.temperatureSensor;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    public void notifyClients(Temperature temperature) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("temperature")
                        .data(temperature));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

    public void updateTemperature(Temperature temperature) {
        temperatureRepository.saveTemperature(temperature);
        notifyClients(temperature);
    }

    public Temperature getLatestTemperature() {
        return this.temperatureRepository.getLatestTemperature();
    }

    public Temperature getTemperatureHistory(LocalDateTime dateTime) {
        return this.temperatureRepository.getTemperatureHistory(dateTime);
    }
}
