package com.example.java.temperatureSensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Temperature {
    private LocalDateTime dateTime;
    private double temperature;
}
