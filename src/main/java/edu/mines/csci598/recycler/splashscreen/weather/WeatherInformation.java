package edu.mines.csci598.recycler.splashscreen.weather;

public class WeatherInformation {

    private int _windSpeed;
    private int _visibility;
    private int _windDegree;
    private int _cloudCover;
    private double _precipitation;
    private int _temperature;
    private int _pressure;
    private int _humidity;

    public WeatherInformation(int windSpeed, int visibility, int windDegree, int cloudCover, double precipitation, int temperature, int pressure, int humidity) {
        _windDegree = windDegree;
        _windSpeed = windSpeed;
        _cloudCover = cloudCover;
        _precipitation = precipitation;
        _temperature = temperature;
        _pressure = pressure;
        _humidity = humidity;
        _visibility = visibility;
    }

    public int getWindSpeed() {
        return _windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this._windSpeed = windSpeed;
    }

    public int getVisibility() {
        return _visibility;
    }

    public void setVisibility(int visibility) {
        this._visibility = visibility;
    }

    public int getWindDegree() {
        return _windDegree;
    }

    public void setWindDegree(int windDegree) {
        this._windDegree = windDegree;
    }

    public int getCloudCover() {
        return _cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this._cloudCover = cloudCover;
    }

    public double getPrecipitation() {
        return _precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this._precipitation = precipitation;
    }

    public int getTemperature() {
        return _temperature;
    }

    public void setTemperature(int temperature) {
        this._temperature = temperature;
    }

    public int getPressure() {
        return _pressure;
    }

    public void setPressure(int pressure) {
        this._pressure = pressure;
    }

    public int getHumidity() {
        return _humidity;
    }

    public void setHumidity(int humidity) {
        this._humidity = humidity;
    }

    @Override
    public String toString() {
        return "Wind Speed (MPH): " + _windSpeed + "\n" +
                "Wind Degree (DIR): " + _windDegree + "\n" +
                "Cloud Cover: " + _cloudCover + "\n" +
                "Precipitation (mm): " + _precipitation + "\n" +
                "Temperature (C): " + _temperature + "\n" +
                "Pressure: " + _pressure + "\n" +
                "Humidity: " + _humidity + "\n" +
                "Visibility: " + _visibility + "\n";
    }
}
