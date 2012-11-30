package edu.mines.csci598.recycler.splashscreen.graphics;

import edu.mines.csci598.recycler.splashscreen.highscores.PlayerHighScoreInformation;
import edu.mines.csci598.recycler.splashscreen.highscores.SerializePlayerInformation;
import edu.mines.csci598.recycler.splashscreen.highscores.SortPlayerInformation;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HighScoreScreen implements SplashScreenSection {
	
	private static final int DIVISION_PIXELS_FROM_RIGHT = 300;
	
	private static final Color SELECTED_BACKGROUND_COLOR = new Color(250, 250, 250);
	private static final Color SELECTED_TEXT_COLOR = new Color(40, 40, 40);
	private static final Color UNSELECTED_BACKGROUND_COLOR = new Color(80, 80, 80);
	private static final Color UNSELECTED_TEXT_COLOR = new Color(220, 220, 220);
	private static final Color PICTURE_FRAME_COLOR = new Color(40, 40, 40);
	
	private static final Font SCORE_LIST_NAME_FONT = new Font("SansSerif", Font.PLAIN, 22);
	private static final Font SCORE_LIST_SCORE_FONT = new Font("SansSerif", Font.PLAIN, 16);
	private static final Font INDIVIDUAL_NAME_FONT = new Font("SansSerif", Font.BOLD, 32);
	
	private static final int TIMER_DELAY = 5000;
	
	
	private ArrayList<PlayerHighScoreInformation> top10Scores;
	private int selectedScore;
	private Timer scoreSwitch;
    private Point topLeft;
    private Point bottomRight;
    private UpdateScreenCallback callback;

	public HighScoreScreen() {
		top10Scores = getTop10Scores();
		selectedScore = 0;
	}
	
	private ArrayList<PlayerHighScoreInformation> getTop10Scores() {
        return SortPlayerInformation.sortByScore(SerializePlayerInformation.retrievePlayerHighScoreInformation());
	}
	
	private void drawDivision(Graphics2D g) {
		int height = bottomRight.y - topLeft.y;
		int width = bottomRight.x - topLeft.x;
		
		int topLeftX = 0;
		int topLeftY = 0;
		int bottomRightX = width - DIVISION_PIXELS_FROM_RIGHT;
		int bottomRightY = height;
		Polygon individualScoreRectangle = getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		g.setColor(new Color(250, 250, 250));
		g.fillPolygon(individualScoreRectangle);
		
		topLeftX = width - DIVISION_PIXELS_FROM_RIGHT;
		topLeftY = 0;
		bottomRightX = width;
		bottomRightY = height;
		Polygon scoreListingRectangle = getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		g.setColor(new Color(80, 80, 80));
		g.fillPolygon(scoreListingRectangle);
	}
	
	private void drawScoresList(ArrayList<PlayerHighScoreInformation> scores, int selectedIndex, Graphics2D g) {
		for(int i = 0; i < scores.size(); ++i) {
			drawScoreOnScoresList(scores.get(i), i, scores.size(), i == selectedIndex, g);
		}
	}
	
	private void drawScoreOnScoresList(PlayerHighScoreInformation highScore, int index, int totalScores, boolean selected, Graphics2D g) {
		final int MARGIN_TOP_BOTTOM = 20;
		final int MIN_PADDING_BETWEEN = 10;
		final int SCORE_BOX_HEIGHT = 60;
		final int SCORE_BOX_INNER_LEFT_RIGHT_PADDING = 10;

        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;
		
		int neededHeight = MARGIN_TOP_BOTTOM * 2 + totalScores * SCORE_BOX_HEIGHT + (totalScores - 1) * MIN_PADDING_BETWEEN;
		int extraHeight = height - neededHeight;
		int extraPadding = Math.max(MIN_PADDING_BETWEEN, MIN_PADDING_BETWEEN + extraHeight / (totalScores - 1));
		
		int topLeftX = width - DIVISION_PIXELS_FROM_RIGHT;
		int topLeftY = MARGIN_TOP_BOTTOM + (SCORE_BOX_HEIGHT + extraPadding) * index;
		int bottomRightX = width;
		int bottomRightY = topLeftY + SCORE_BOX_HEIGHT;
		Polygon rectangle = getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		
		if (selected) {
			g.setColor(SELECTED_BACKGROUND_COLOR);
		} else {
			g.setColor(UNSELECTED_BACKGROUND_COLOR);
		}
		
		g.fillPolygon(rectangle);
		
		g.setFont(SCORE_LIST_NAME_FONT);
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle2D fontBounds = fontMetrics.getStringBounds(highScore.getPlayerInitials(), g);
		int topPadding = (int) ((SCORE_BOX_HEIGHT - fontBounds.getHeight()) / 2) + fontMetrics.getAscent();
		int fontStartX = topLeftX + SCORE_BOX_INNER_LEFT_RIGHT_PADDING;
		int fontStartY = topLeftY + topPadding;
		if (selected) {
			g.setColor(SELECTED_TEXT_COLOR);
		} else {
			g.setColor(UNSELECTED_TEXT_COLOR);
		}
		g.drawString(highScore.getPlayerInitials(), fontStartX, fontStartY);
		
		g.setFont(SCORE_LIST_SCORE_FONT);
		fontMetrics = g.getFontMetrics();
		fontBounds = fontMetrics.getStringBounds(Double.toString(highScore.getPlayerScore()), g);
		topPadding = (int) ((SCORE_BOX_HEIGHT - fontBounds.getHeight()) / 2) + fontMetrics.getAscent();
		fontStartX = (int) (width - SCORE_BOX_INNER_LEFT_RIGHT_PADDING - fontBounds.getWidth());
		fontStartY = topLeftY + topPadding;
		g.drawString(Double.toString(highScore.getPlayerScore()), fontStartX, fontStartY);
	}
	
	private void drawScore(PlayerHighScoreInformation score, Graphics2D g) {
		final int PICTURE_HEIGHT = 400;
		final int PICTURE_WIDTH = 300;
		final int FRAME_PADDING = 20;
		final int SCORE_PADDING = 40;

        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;
		
		int topPadding = (height - PICTURE_HEIGHT) / 2;
		int leftPadding = ((width - DIVISION_PIXELS_FROM_RIGHT) - PICTURE_WIDTH) / 2;
		Polygon innerFrame = getRectangle(leftPadding, topPadding, leftPadding + PICTURE_WIDTH, topPadding + PICTURE_HEIGHT);
		Polygon outerFrame = getRectangle(leftPadding - FRAME_PADDING, topPadding - FRAME_PADDING, leftPadding + PICTURE_WIDTH + FRAME_PADDING, topPadding + PICTURE_HEIGHT + FRAME_PADDING);
		
		g.setColor(new Color(253, 253, 253));
		g.fillPolygon(outerFrame);
		g.setColor(new Color(230, 230, 230));
		g.fillPolygon(innerFrame);
		g.setColor(PICTURE_FRAME_COLOR);
		g.drawPolygon(innerFrame);
		g.drawPolygon(outerFrame);
		
		g.setFont(INDIVIDUAL_NAME_FONT);
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle2D fontBounds = fontMetrics.getStringBounds(score.getPlayerInitials(), g);
		int topFontPadding = (int) (topPadding + innerFrame.getBounds2D().getHeight() + FRAME_PADDING + SCORE_PADDING);
		int leftFontPadding = (int) (((width - DIVISION_PIXELS_FROM_RIGHT) - fontBounds.getWidth()) / 2);
		g.setColor(SELECTED_TEXT_COLOR);
		g.drawString(score.getPlayerInitials(), leftFontPadding, topFontPadding);
	}

	private Polygon getRectangle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		int[] xPoints = new int[] {topLeftX, topLeftX, bottomRightX, bottomRightX};
		int[] yPoints = new int[] {topLeftY, bottomRightY, bottomRightY, topLeftY};
		return new Polygon(xPoints, yPoints, 4);
	}

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback callback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.callback = callback;

        scoreSwitch = new Timer();
        scoreSwitch.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                selectedScore = (selectedScore + 1) % top10Scores.size();
                HighScoreScreen.this.callback.updateScreen();
            }
        }, TIMER_DELAY, TIMER_DELAY);
    }

    @Override
    public void draw(Graphics2D g) {
        drawDivision(g);
        drawScoresList(top10Scores, selectedScore, g);
        drawScore(top10Scores.get(selectedScore), g);
    }

    @Override
    public void stop() {
        scoreSwitch.cancel();
    }
}
