#include <Arduino.h>

#define SENSOR_PIN 2
#define CPM_TO_USVH_M4011 0.0095238
#define WIN_IN_MIL 10000L
#define WIN_IN_MIL_TO_MIN 6 

volatile unsigned long pulseCount = 0L;
unsigned long lastTime = 0L;

void setup() {
  pinMode(SENSOR_PIN, INPUT);

  attachInterrupt(
    digitalPinToInterrupt(SENSOR_PIN),
    onPulse,
    RISING // ?
  );

  Serial.begin(9600);
}

void onPulse() {
  pulseCount++;
}

void loop() {
  unsigned long now = millis();

  if (now - lastTime >= WIN_IN_MIL) {
    lastTime = now;

    noInterrupts();
    unsigned long count = pulseCount;
    pulseCount = 0;
    interrupts();

    Serial.println(count * WIN_IN_MIL_TO_MIN * CPM_TO_USVH_M4011);  
  }
}


