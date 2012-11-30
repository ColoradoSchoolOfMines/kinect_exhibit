package edu.mines.csci598.recycler.splashscreen.footers;

import edu.mines.csci598.recycler.splashscreen.weather.LocalAreaWeather;
import edu.mines.csci598.recycler.splashscreen.weather.WeatherInformation;

import javax.swing.*;

public class WeatherFooter extends JPanel {

    private WeatherInformation weather;

    public WeatherFooter() {
        super();
        weather = LocalAreaWeather.retrieveWeatherInformation();
    }


}
