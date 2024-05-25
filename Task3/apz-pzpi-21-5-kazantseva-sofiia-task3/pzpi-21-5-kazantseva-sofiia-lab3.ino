#include <Arduino.h>

const int temperaturePin = A0; 
const int soundPin = 2; 

class Truck {
  public:
    float height;
    float length;
    float width;
    float weight;

    Truck() {}

    Truck(float h, float l, float w, float wt) {
        height = h;
        length = l;
        width = w;
        weight = wt;
    }

    String serialize() {
        return String("truck_height=") + String(height) +
               "&truck_length=" + String(length) +
               "&truck_width=" + String(width) +
               "&truck_weight=" + String(weight);
    }
};

class ParkingPlace {
  public:
    float minHeight;
    float maxHeight;
    float minLength;
    float maxLength;
    float minWidth;
    float maxWidth;
    float minWeight;
    float maxWeight;

    ParkingPlace() {}

    ParkingPlace(float minH, float maxH, float minL, float maxL, float minW, float maxW, float minWt, float maxWt) {
        minHeight = minH;
        maxHeight = maxH;
        minLength = minL;
        maxLength = maxL;
        minWidth = minW;
        maxWidth = maxW;
        minWeight = minWt;
        maxWeight = maxWt;
    }

    String serialize() {
        return String("parking_minHeight=") + String(minHeight) +
               "&parking_maxHeight=" + String(maxHeight) +
               "&parking_minLength=" + String(minLength) +
               "&parking_maxLength=" + String(maxLength) +
               "&parking_minWidth=" + String(minWidth) +
               "&parking_maxWidth=" + String(maxWidth) +
               "&parking_minWeight=" + String(minWeight) +
               "&parking_maxWeight=" + String(maxWeight);
    }
};

Truck truck;
ParkingPlace parkingPlace;

void setup() {
    Serial.begin(9600);
}

void loop() {
    if (Serial.available()) {
        String command = Serial.readStringUntil('\n');
        if (command == "GET_DATA_TRUCK") {
            getTruck();
            String response = truck.serialize();
            Serial.println(response);
        } else if (command == "GET_DATA_PARKING") {
            getParkingPlace();
            String response = parkingPlace.serialize();
            Serial.println(response);
        }
    }
    delay(5000); 
}

void readTruckSensors() {
    float temperature = analogRead(temperaturePin) * (5.0 / 1023.0) * 100.0; 
    float soundLevel = analogRead(soundPin) * (5.0 / 1023.0) * 100.0;

    truck.height = map(temperature, 0, 100, 3, 6); 
    truck.length = map(soundLevel, 0, 100, 8, 22); 
    truck.width = map(temperature, 0, 100, 2, 3); 
    truck.weight = map(soundLevel, 0, 100, 12, 100);
}

void readParkingSensors() {
    float temperature = analogRead(temperaturePin) * (5.0 / 1023.0) * 100.0; 
    float soundLevel = analogRead(soundPin) * (5.0 / 1023.0) * 100.0;

    parkingPlace.minHeight = map(temperature, 0, 100, 2.5, 3.5);
    parkingPlace.maxHeight = map(temperature, 0, 100, 4.5, 6.5);
    parkingPlace.minLength = map(soundLevel, 0, 100, 7, 13);
    parkingPlace.maxLength = map(soundLevel, 0, 100, 19, 22);
    parkingPlace.minWidth = map(temperature, 0, 100, 1.5, 2.5);
    parkingPlace.maxWidth = map(temperature, 0, 100, 3.5, 5.5);
    parkingPlace.minWeight = map(soundLevel, 0, 100, 4, 6);
    parkingPlace.maxWeight = map(soundLevel, 0, 100, 80, 120);
}

float map(float x, float in_min, float in_max, float out_min, float out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}

void getTruck() {
    readTruckSensors();
    Truck truck1 = truck;
    readTruckSensors();
    Truck truck2 = truck;
    readTruckSensors();
    Truck truck3 = truck;
    
    Truck trucks[] = {truck1, truck2, truck3};

    float heights[3], lengths[3], widths[3], weights[3];

    for (int i = 0; i < 3; i++) {
        heights[i] = trucks[i].height;
        lengths[i] = trucks[i].length;
        widths[i] = trucks[i].width;
        weights[i] = trucks[i].weight;
    }

    truck.height = calculateAverage(heights) + calculateError(heights);
    truck.length = calculateAverage(lengths) + calculateError(lengths);
    truck.width = calculateAverage(widths) + calculateError(widths);
    truck.weight = calculateAverage(weights) + calculateError(weights);
}

void getParkingPlace() {
    readParkingSensors();
    ParkingPlace place1 = parkingPlace;
    readParkingSensors();
    ParkingPlace place2 = parkingPlace;
    readParkingSensors();
    ParkingPlace place3 = parkingPlace;
    
    ParkingPlace places[] = {place1, place2, place3};

    float minHeights[3], maxHeights[3],  minLengths[3], maxLengths[3],  minWidths[3], maxWidths[3], minWeights[3],  maxWeights[3];

    for (int i = 0; i < 3; i++) {
        minHeights[i] = places[i].minHeight;
        maxHeights[i] = places[i].maxHeight;
        minLengths[i] = places[i].minLength;
        maxLengths[i] = places[i].maxLength;
        minWidths[i] = places[i].minWidth;
        maxWidths[i] = places[i].maxWidth;
        minWeights[i] = places[i].minWeight;
        maxWeights[i] = places[i].maxWeight;
    }

    parkingPlace.minHeight = calculateAverage(minHeights) + calculateError(minHeights);
    parkingPlace.maxHeight = calculateAverage(maxHeights) + calculateError(maxHeights);
    parkingPlace.minLength = calculateAverage(minLengths) + calculateError(minLengths);
    parkingPlace.maxLength = calculateAverage(maxLengths) + calculateError(maxLengths);
    parkingPlace.minWidth = calculateAverage(minWidths) + calculateError(minWidths);
    parkingPlace.maxWidth = calculateAverage(maxWidths) + calculateError(maxWidths);
    parkingPlace.minWeight = calculateAverage(minWeights) + calculateError(minWeights);
    parkingPlace.maxWeight = calculateAverage(maxWeights) + calculateError(maxWeights);
}


float calculateError(float list[]){
  float average = calculateAverage(list);
  float sensorError = 0.5;
  float error = pow(average * sensorError, 2);
}

float calculateAverage(float list[]){
  float sum = 0;

  for (int i = 0; i < 3; i++) {
        sum += list[i];
  }
  return sum/3;
}