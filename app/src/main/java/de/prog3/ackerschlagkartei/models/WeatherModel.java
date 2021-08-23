package de.prog3.ackerschlagkartei.models;

public class WeatherModel {
    private final String locationName;
    private final double temp;

    public WeatherModel(String locationName, double temp) {
        this.locationName = locationName;
        this.temp = temp;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getTemp() {
        return temp;
    }
}
