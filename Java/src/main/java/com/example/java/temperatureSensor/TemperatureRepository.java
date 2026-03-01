package com.example.java.temperatureSensor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class TemperatureRepository {

    private final RedisTemplate<String, Temperature> redisTemplate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public TemperatureRepository(RedisTemplate<String, Temperature> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Temperature getTemperatureHistory(LocalDateTime dateTime) {
        String key = "temperature:" + dateTime.format(formatter);
        return redisTemplate.opsForValue().get(key);
    }

    public Temperature getLatestTemperature() {
        return redisTemplate.opsForValue().get("temperature:latest");
    }

    public void saveTemperature(Temperature temperature) {
        String key = "temperature:" + temperature.getDateTime().format(formatter);
        redisTemplate.opsForValue().set(key, temperature);
        redisTemplate.opsForValue().set("temperature:latest", temperature);
    }
}
