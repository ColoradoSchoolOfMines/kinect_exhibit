package edu.mines.csci598.recycler.splashscreen.graphics;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenLauncher extends GameState {

    private GameManager gameManager;
    private List<SplashScreenSection> sections;
    private boolean refreshScreen;

    public static void main(String[] args) {
        SplashScreenLauncher launcher = new SplashScreenLauncher();
        launcher.getGameManager().setState(launcher);
        launcher.getGameManager().run();
        launcher.getGameManager().destroy();
    }

    public SplashScreenLauncher() {
        gameManager = new GameManager("SplashScreen", false);

        sections = new ArrayList<SplashScreenSection>();
        SplashScreenSection highScoreSection = new HighScoreScreen();
        highScoreSection.initialize(new Point(0, 0), new Point(1280, 720), callback);
        sections.add(highScoreSection);
        refreshScreen = true;
    }

    UpdateScreenCallback callback = new UpdateScreenCallback() {
        @Override
        public void updateScreen() {
            refreshScreen = true;
        }
    };

    @Override
    protected GameState updateThis(float elapsedTime) {
        return this;
    }

    @Override
    protected void drawThis(Graphics2D g) {
        if (refreshScreen) {
            refreshScreen = false;

            for (SplashScreenSection section : sections) {
                section.draw(g);
            }
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
