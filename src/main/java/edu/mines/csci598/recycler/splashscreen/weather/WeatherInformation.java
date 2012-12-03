package edu.mines.csci598.recycler.splashscreen.weather;

public class WeatherInformation {

    private int windSpeed;
    private int visibility;
    private int windDegree;
    private int cloudCover;
    private double precipitation;
    private int temperature;
    private int pressure;
    private int humidity;

    public WeatherInformation(int windSpeed, int visibility, int windDegree, int cloudCover, double precipitation, int temperature, int pressure, int humidity) {
        this.windDegree = windDegree;
        this.windSpeed = windSpeed;
        this.cloudCover = cloudCover;
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibility = visibility;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Wind Speed (MPH): " + windSpeed + "\n" +
                "Wind Degree (DIR): " + windDegree + "\n" +
                "Cloud Cover: " + cloudCover + "\n" +
                "Precipitation (mm): " + precipitation + "\n" +
                "Temperature (C): " + temperature + "\n" +
                "Pressure: " + pressure + "\n" +
                "Humidity: " + humidity + "\n" +
                "Visibility: " + visibility + "\n";
    }
}
