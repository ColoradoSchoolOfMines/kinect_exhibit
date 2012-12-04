package edu.mines.csci598.recycler.splashscreen.graphics;

import java.awt.*;

public class GraphicsHelper {
    public static Polygon getRectangle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        int[] xPoints = new int[] {topLeftX, topLeftX, bottomRightX, bottomRightX};
        int[] yPoints = new int[] {topLeftY, bottomRightY, bottomRightY, topLeftY};
        return new Polygon(xPoints, yPoints, 4);
    }
}
