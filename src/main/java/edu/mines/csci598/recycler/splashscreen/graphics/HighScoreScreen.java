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

    final int SCORE_MARGIN_TOP_BOTTOM = 20;
    final int SCORE_MIN_PADDING_BETWEEN = 10;
    final int SCORE_BOX_HEIGHT = 60;
    final int SCORE_BOX_INNER_LEFT_RIGHT_PADDING = 10;
    final int PICTURE_HEIGHT = 400;
    final int PICTURE_WIDTH = 300;
    final int PICTURE_FRAME_PADDING = 20;
    final int SCORE_PADDING = 40;

	private static final int TIMER_DELAY = 10000;
	
	private ArrayList<PlayerHighScoreInformation> top10Scores;
	private int selectedScore;
	private Timer scoreSwitch;
    private Point topLeft;
    private Point bottomRight;
    private UpdateScreenCallback updateScreenCallback;
    private CycleScreenCallback cycleScreenCallback;
    private int timerUpdateCount;

	public HighScoreScreen() {
		top10Scores = getTop10Scores();
	}
	
	private ArrayList<PlayerHighScoreInformation> getTop10Scores() {
        return SortPlayerInformation.sortByScore(SerializePlayerInformation.retrievePlayerHighScoreInformation());
	}
	
	private void drawDivision(Graphics2D g) {
		int height = bottomRight.y - topLeft.y;
		int width = bottomRight.x - topLeft.x;

		int topLeftX = topLeft.x;
		int topLeftY = topLeft.y;
		int bottomRightX = topLeft.x + width - DIVISION_PIXELS_FROM_RIGHT;
		int bottomRightY = topLeft.y + height;
		Polygon individualScoreRectangle = GraphicsHelper.getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		g.setColor(new Color(250, 250, 250));
		g.fillPolygon(individualScoreRectangle);

		topLeftX = topLeft.x + width - DIVISION_PIXELS_FROM_RIGHT;
		topLeftY = topLeft.y;
		bottomRightX = topLeft.x + width;
		bottomRightY = topLeft.y + height;
		Polygon scoreListingRectangle = GraphicsHelper.getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		g.setColor(new Color(80, 80, 80));
		g.fillPolygon(scoreListingRectangle);
	}
	
	private void drawScoresList(ArrayList<PlayerHighScoreInformation> scores, int selectedIndex, Graphics2D g) {
		for(int i = 0; i < scores.size(); ++i) {
			drawScoreOnScoresList(scores.get(i), i, scores.size(), i == selectedIndex, g);
		}
	}
	
	private void drawScoreOnScoresList(PlayerHighScoreInformation highScore, int index, int totalScores, boolean selected, Graphics2D g) {
        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;
		
		int neededHeight = SCORE_MARGIN_TOP_BOTTOM * 2 + totalScores * SCORE_BOX_HEIGHT + (totalScores - 1) * SCORE_MIN_PADDING_BETWEEN;
		int extraHeight = height - neededHeight;
		int extraPadding = Math.max(SCORE_MIN_PADDING_BETWEEN, SCORE_MIN_PADDING_BETWEEN + extraHeight / (totalScores - 1));
		
		int topLeftX = topLeft.x + width - DIVISION_PIXELS_FROM_RIGHT;
		int topLeftY = topLeft.y + SCORE_MARGIN_TOP_BOTTOM + (SCORE_BOX_HEIGHT + extraPadding) * index;
        int bottomRightX = topLeft.x + width;
        int bottomRightY = topLeftY + SCORE_BOX_HEIGHT;
		Polygon rectangle = GraphicsHelper.getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		
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
		fontStartX = topLeft.x + (int) (width - SCORE_BOX_INNER_LEFT_RIGHT_PADDING - fontBounds.getWidth());
		fontStartY = topLeftY + topPadding;
		g.drawString(Double.toString(highScore.getPlayerScore()), fontStartX, fontStartY);
	}
	
	private void drawScore(PlayerHighScoreInformation score, Graphics2D g) {
        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;
		
		int topPadding = (height - PICTURE_HEIGHT) / 2;
		int leftPadding = ((width - DIVISION_PIXELS_FROM_RIGHT) - PICTURE_WIDTH) / 2;

        int topLeftX = topLeft.x + leftPadding;
        int topLeftY = topLeft.y + topPadding;
        int bottomRightX = topLeft.x + leftPadding + PICTURE_WIDTH;
        int bottomRightY = topLeft.y + topPadding + PICTURE_HEIGHT;

		Polygon innerFrame = GraphicsHelper.getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
        topLeftX = topLeft.x + leftPadding - PICTURE_FRAME_PADDING;
        topLeftY = topLeft.y + topPadding - PICTURE_FRAME_PADDING;
        bottomRightX = topLeft.x + leftPadding + PICTURE_WIDTH + PICTURE_FRAME_PADDING;
        bottomRightY = topLeft.y + topPadding + PICTURE_HEIGHT + PICTURE_FRAME_PADDING;
        if (score.getPlayerImage() != null)
            g.drawImage(score.getPlayerImage().getImage(),topLeftX, topLeftY, bottomRightX-topLeftX, bottomRightY - topLeftY, null );
        else {
            g.fillPolygon(innerFrame);
            g.setColor(PICTURE_FRAME_COLOR);
            g.drawPolygon(innerFrame);
        }

		Polygon outerFrame = GraphicsHelper.getRectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
		
		g.setColor(new Color(253, 253, 253));
		g.fillPolygon(outerFrame);
		g.setColor(new Color(230, 230, 230));
		g.fillPolygon(innerFrame);
		g.setColor(PICTURE_FRAME_COLOR);
		g.drawPolygon(outerFrame);
		
		g.setFont(INDIVIDUAL_NAME_FONT);
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle2D fontBounds = fontMetrics.getStringBounds(String.valueOf(score.getPlayerScore()), g);
		int topFontPadding = topLeft.y + (int) (topPadding + innerFrame.getBounds2D().getHeight() + PICTURE_FRAME_PADDING + SCORE_PADDING);
		int leftFontPadding = topLeft.x + (int) (((width - DIVISION_PIXELS_FROM_RIGHT) - fontBounds.getWidth()) / 2);
		g.setColor(SELECTED_TEXT_COLOR);
		g.drawString(String.valueOf(score.getPlayerScore()), leftFontPadding, topFontPadding);
	}

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.updateScreenCallback = updateScreenCallback;
        this.cycleScreenCallback = cycleScreenCallback;
    }

    @Override
    public void draw(Graphics2D g) {
        drawDivision(g);
        drawScoresList(top10Scores, selectedScore, g);
        if (!top10Scores.isEmpty())
            drawScore(top10Scores.get(selectedScore), g);
    }

    @Override
    public void startThreads() {
        timerUpdateCount = 0;
        selectedScore = 0;

        scoreSwitch = new Timer();
        scoreSwitch.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (top10Scores.size() > 0)
                    selectedScore = (selectedScore + 1) % top10Scores.size();
                HighScoreScreen.this.updateScreenCallback.updateScreen();

                timerUpdateCount++;

                if (timerUpdateCount == 10) {
                    HighScoreScreen.this.cycleScreenCallback.cycleScreen(HighScoreScreen.this);
                }
            }
        }, TIMER_DELAY, TIMER_DELAY);
    }

    @Override
    public void stopThreads() {
        scoreSwitch.cancel();
    }

}
