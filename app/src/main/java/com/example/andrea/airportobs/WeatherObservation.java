package com.example.andrea.airportobs;

import java.io.Serializable;

/**
 * Created by andrea on 01.12.15.
 */
public class WeatherObservation implements Serializable {
    private String clouds;
    private int windDirection;
    private int elevation;
    private String countryCode;
    private double lng;
    private double lat;
    private String temperature;
    private int humidity;
    private int hectoPascalAltimeter;


    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHectoPascalAltimeter() {
        return hectoPascalAltimeter;
    }

    public void setHectoPascalAltimeter(int hectoPascalAltimeter) {
        this.hectoPascalAltimeter = hectoPascalAltimeter;
    }
}
