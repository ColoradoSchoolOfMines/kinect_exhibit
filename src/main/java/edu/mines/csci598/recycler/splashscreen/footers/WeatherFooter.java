package edu.mines.csci598.recycler.splashscreen.footers;

import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.GraphicsHelper;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;
import edu.mines.csci598.recycler.splashscreen.weather.LocalAreaWeather;
import edu.mines.csci598.recycler.splashscreen.weather.WeatherInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class WeatherFooter implements ActionListener, SplashScreenSection {

    private WeatherInformation weather;
    private Timer timer;
    private Point topLeft;
    private Point bottomRight;
    private UpdateScreenCallback callback;

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);
    private static final Font TEMPERATURE_FONT = new Font("SansSerif", Font.BOLD, 32);
    private static final Font DESCRIPTION_FONT = new Font("SansSerif", Font.PLAIN, 20);
    private static final Color TEXT_COLOR = new Color(80, 80, 80);
    private static final Color BACKGROUND_COLOR = new Color(230, 230, 230);

    private static final int UPDATE_INTERVAL = 300000;

    private static final String TITLE = "Weather";
    private static final String WEATHER_NOT_FOUND = "Could not connect to weather service";

    public WeatherFooter() {
        weather = LocalAreaWeather.retrieveWeatherInformation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        weather = LocalAreaWeather.retrieveWeatherInformation();
        timer.start();
        callback.updateScreen();
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.callback = updateScreenCallback;
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawTitle(g);
        drawTemperature(g);
        drawDescription(g);
    }

    private void drawBackground(Graphics2D g) {
        Polygon frame = GraphicsHelper.getRectangle(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
        g.setColor(BACKGROUND_COLOR);
        g.fillPolygon(frame);
    }

    private void drawTitle(Graphics2D g) {
        g.setFont(TITLE_FONT);
        g.setColor(TEXT_COLOR);
        g.drawString(TITLE, topLeft.x + 20, topLeft.y + 40);
    }

    private void drawTemperature(Graphics2D g) {
        String temperature = Integer.toString(weather.getTemperature()) + "°F";
        g.setFont(TEMPERATURE_FONT);
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D fontBounds = fontMetrics.getStringBounds(temperature, g);

        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;

        int leftXPadding = width / 2 - (int)fontBounds.getWidth() - 10;
        int fontStartX = topLeft.x + leftXPadding;

        int totalYPadding = height - (int)fontBounds.getHeight();
        int fontStartY = topLeft.y + totalYPadding / 2 + (int)fontMetrics.getAscent();

        g.setColor(TEXT_COLOR);
        g.drawString(temperature, fontStartX, fontStartY);
    }

    private void drawDescription(Graphics2D g) {
        String description = weather.getDescription();
        g.setFont(DESCRIPTION_FONT);
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D fontBounds = fontMetrics.getStringBounds(description, g);

        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;

        int leftXPadding = width / 2 + 10;
        int fontStartX = topLeft.x + leftXPadding;

        int totalYPadding = height - (int)fontBounds.getHeight();
        int fontStartY = topLeft.y + totalYPadding / 2 + (int)fontMetrics.getAscent();

        g.setColor(TEXT_COLOR);
        g.drawString(description, fontStartX, fontStartY);
    }

    @Override
    public void startThreads() {
        timer = new Timer(UPDATE_INTERVAL, this);
        timer.start();
    }

    @Override
    public void stopThreads() {
        timer.stop();
    }

}
