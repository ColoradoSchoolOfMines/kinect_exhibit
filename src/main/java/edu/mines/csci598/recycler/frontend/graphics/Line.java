package edu.mines.csci598.recycler.frontend.graphics;

/**
 * A line is a part of a path. It has the starting location, the ending location, and the total time it takes
 * the line to be traversed.
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line {
    private double startX, startY, endX, endY, totalTime;

    /**
     * Begining coordinates, ending coordinates and how long the line takes to be traversed. This way based on a time
     * later we can calculate where along the path it is.
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param totalTime
     */
    public Line(double startX, double startY, double endX, double endY, double totalTime) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.totalTime = totalTime;

        //TODO: Add error detection

    }

    /**
     * Based on the time this function will return the location along the line it is in.
     *
     * @param time
     * @return
     */
    public Coordinate getLocation(double time) {
        double x, y;
        if (time <= 0) {
            x = startX;
            y = startY;
        } else if (time >= totalTime) {
            x = endX;
            y = endY;
        } else { //we are somewhere in the middle of the line
            double fraction = time / totalTime;
            x = (endX - startX) * fraction + startX;
            y = (endY - startY) * fraction + startY;
        }
        return new Coordinate(x, y);
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }


}
