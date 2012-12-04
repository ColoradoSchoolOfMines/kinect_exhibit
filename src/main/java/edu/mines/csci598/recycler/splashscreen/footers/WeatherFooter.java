package edu.mines.csci598.recycler.splashscreen.footers;

import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;
import edu.mines.csci598.recycler.splashscreen.weather.LocalAreaWeather;
import edu.mines.csci598.recycler.splashscreen.weather.WeatherInformation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherFooter implements ActionListener, SplashScreenSection {

    private WeatherInformation weather;

    public WeatherFooter() {
        weather = LocalAreaWeather.retrieveWeatherInformation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
    }

    @Override
    public void draw(Graphics2D g) {
    }

    @Override
    public void stop() {
    }
}
