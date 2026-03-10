#include <Arduino.h>
#include <WiFi.h>
#include <PubSubClient.h>
#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 4
#define LED_PIN 2

const char *ssid = "PLAY_Swiatlowodowy_5F95";
const char *password = "Fh@7c&@dFrPN";
const char *mqtt_server = "192.168.0.15";

WiFiClient espClient;
PubSubClient client(espClient);

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup_wifi()
{
  delay(10);
  Serial.println("Connecting to WiFi");

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi connected");
}

void reconnect()
{
  while (!client.connected())
  {
    Serial.println("Connecting to MQTT...");
    Serial.print("WiFi IP: ");
    Serial.println(WiFi.localIP());
    Serial.print("MQTT state: ");
    Serial.println(client.state());
    if (client.connect("ESP32_Temp"))
    {
      Serial.println("connected");
    }
    else
    {
      Serial.print("failed rc=");
      Serial.println(client.state());
      delay(2000);
    }
  }
}

void setup()
{
  Serial.begin(115200);

  pinMode(LED_PIN, OUTPUT);

  sensors.begin();

  setup_wifi();

  client.setServer(mqtt_server, 1884);
}

void loop()
{

  if (!client.connected())
  {
    reconnect();
  }

  client.loop();

  sensors.requestTemperatures();
  float tempC = sensors.getTempCByIndex(0);

  if (tempC < 15)
  {
    digitalWrite(LED_PIN, HIGH);
  }
  else
  {
    digitalWrite(LED_PIN, LOW);
  }

  Serial.print("Temperature: ");
  Serial.println(tempC);

  char tempString[8];
  dtostrf(tempC, 1, 2, tempString);

  client.publish("temperature", tempString);

  delay(1000);
}