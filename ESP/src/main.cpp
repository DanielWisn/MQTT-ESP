#include <Arduino.h>
#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 4
#define LED_PIN 2

OneWire oneWire(ONE_WIRE_BUS);

DallasTemperature sensors(&oneWire);

void setup() {
  Serial.begin(115200);
  Serial.print("Device count: ");
  Serial.println(sensors.getDeviceCount());
  pinMode(LED_PIN, OUTPUT);
  sensors.begin();
}

void loop() {
  sensors.requestTemperatures();

  float tempC = sensors.getTempCByIndex(0);
  if (tempC < 15) {
    digitalWrite(LED_PIN, HIGH);
  } else {
    digitalWrite(LED_PIN, LOW);
  }
  Serial.print("Temperature: ");
  Serial.print(tempC);
  Serial.println(" °C");

  delay(1000);
}