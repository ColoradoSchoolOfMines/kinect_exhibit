package edu.mines.csci598.recycler.splashscreen;

import edu.mines.csci598.recycler.splashscreen.highscores.PlayerHighScoreInformation;
import edu.mines.csci598.recycler.splashscreen.highscores.SerializePlayerInformation;
import edu.mines.csci598.recycler.splashscreen.social.TwitterMessages;
import edu.mines.csci598.recycler.splashscreen.weather.LocalAreaWeather;
import edu.mines.csci598.recycler.splashscreen.weather.WeatherInformation;

import javax.swing.*;
import java.util.List;

public class TestApp extends JFrame {

    public TestApp() {
        initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JLabel label = new JLabel(getTwitterMessage());
        panel.add(label);
        label = new JLabel();
        label.setText(convertToHtml(getWeatherInformation().toString()));
        panel.add(label);

        for (PlayerHighScoreInformation player : getPlayerHighScores()) {
            label = new JLabel();
            label.setText(player.getPlayerInitials() + " " + player.getPlayerScore());
            panel.add(label);
            label = new JLabel(player.getPlayerImage());
            panel.add(label);
        }
        setTitle("Test App");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TestApp app = new TestApp();
                app.setVisible(true);
            }
        });
    }

    private String getTwitterMessage() {
        TwitterMessages messages = new TwitterMessages();
        return messages.retrieveLatestMessage();
    }

    private WeatherInformation getWeatherInformation() {
        return LocalAreaWeather.retrieveWeatherInformation();
    }

    private static String convertToHtml(String text)
    {
        return "<html>" + text.replaceAll("\n", "<brt c>");
    }

    private List<PlayerHighScoreInformation> getPlayerHighScores() {
        return SerializePlayerInformation.retrievePlayerHighScoreInformation();
    }
}
