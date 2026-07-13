# MQTT-ESP Temperature Monitor

A project connecting an ESP32 microcontroller to a Spring Boot backend using the MQTT protocol. The ESP32 reads temperature data from a sensor and publishes it to an MQTT broker, which the Java backend subscribes to and stores. The data is then accessible via a REST endpoint.

## How It Works

```
ESP32 (sensor) → publishes → Mosquitto (MQTT broker) → Spring Boot subscribes → Redis → REST API
```

1. The ESP32 reads temperature from a connected sensor and publishes the reading to a topic on the Mosquitto MQTT broker
2. The Spring Boot backend is subscribed to that topic and receives the message as soon as it arrives
3. The reading is stored in Redis
4. The data is exposed via a REST endpoint that returns the latest temperature

## Tech Stack

**ESP32 (C++)**
- Arduino framework
- MQTT client library for publishing sensor data

**Backend (Java)**
- Java 17
- Spring Boot
- Spring Integration MQTT — subscribes to the broker and handles incoming messages
- Redis — stores incoming temperature readings
- Eclipse Mosquitto — MQTT broker running in Docker

**Infrastructure**
- Docker / Docker Compose — runs Mosquitto and Redis

## Project Structure

```
├── ESP/          # C++ Arduino code for the ESP32
├── Java/         # Spring Boot backend
├── docker-compose.yml
└── mosquitto.conf
```

## Prerequisites

- Docker and Docker Compose
- Java 17+
- Maven
- Arduino IDE or PlatformIO (for flashing the ESP32)

## Getting Started

**1. Start the broker and Redis**

```bash
docker compose up -d
```

This starts:
- Mosquitto MQTT broker on port `1884` (mapped from internal `1883`)
- Redis on port `6379`

**2. Configure the ESP32**

In the ESP32 code (`ESP/` folder), set your WiFi credentials and the broker IP address to match the machine running Docker:

```cpp
const char* ssid = "your-wifi-ssid";
const char* password = "your-wifi-password";
const char* mqtt_server = "192.168.x.x"; // your machine's local IP
```

Flash the sketch to your ESP32 via Arduino IDE or PlatformIO.

**3. Run the backend**

```bash
cd Java
./mvnw spring-boot:run
```

The backend starts on `http://localhost:8080` and immediately subscribes to the MQTT topic. Once the ESP32 is running and connected to the same network, temperature readings will start flowing in automatically.

## API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/temperature` | Returns the latest temperature reading received from the ESP32 |

## Mosquitto Configuration

The broker is configured to allow anonymous connections with no authentication, suitable for local development:

```
allow_anonymous true
listener 1883
```

## Notes

- The ESP32 and the machine running Docker must be on the same local network, or the broker must be reachable from the ESP32's network
- Mosquitto is exposed on port `1884` externally (mapped to internal `1883`) to avoid conflicts with any locally installed MQTT brokers
