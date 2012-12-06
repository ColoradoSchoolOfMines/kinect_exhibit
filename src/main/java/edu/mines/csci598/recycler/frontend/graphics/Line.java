package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

/**
 * A line is a part of a path. It has the starting location, the ending location, and the total time it takes
 * the line to be traversed.
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line extends Line2D{
    private static final Logger logger = Logger.getLogger(Line.class);

	private final Coordinate startPoint, endPoint;
	private double timeToComplete;

    /**
     * Begining coordinates, ending coordinates and how long the line takes to be traversed. This way based on a time
     * later we can calculate where along the path it is.
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param timeToComplete
     */
    public Line(double startX, double startY, double endX, double endY, double timeToComplete) {
        startPoint = new Coordinate(startX, startY);
        endPoint = new Coordinate(endX, endY);
        this.timeToComplete = timeToComplete;
    }

    public Line(double startX, double startY, double endX, double endY, double timeToComplete, double rotation) {
        startPoint = new Coordinate(startX, startY);
        endPoint = new Coordinate(endX, endY, rotation);
        this.timeToComplete = timeToComplete;
    }

    public Line(Coordinate start, Coordinate end, double timeToComplete ) {
        startPoint = start;
        endPoint = end;
        this.timeToComplete = timeToComplete;
    }

	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
	}

	@Override
	public double getX1() {
		return startPoint.getX();
	}

	@Override
	public double getY1() {
		return startPoint.getY();
	}

	@Override
	public Coordinate getP1() {
		return startPoint;
	}

	@Override
	public double getX2() {
		return endPoint.getX();
	}

	@Override
	public double getY2() {
		return endPoint.getY();
	}

	@Override
	public Coordinate getP2() {
		return endPoint;
	}

	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		throw new IllegalArgumentException("Use the coordinate line constructor instead.");
	}

    public double getTimeToComplete() {
        return timeToComplete;
    }

    /**
     * Pass in the elapsed time since this line has started to get the coordinates at that time
     * @param time
     * @return Coordinate of where it is at this relative time
     */
    public Coordinate getCoordinateAtTime(double time) {
        double x, y;
        if (time <= 0) {
            return startPoint;
        }
        else if (time >= timeToComplete) {
            return endPoint;
        }
        else { //we are somewhere in the middle of the line
            double fraction = time / timeToComplete;
            x = (endPoint.getX() - startPoint.getX()) * fraction + startPoint.getX();
            y = (endPoint.getY() - startPoint.getY()) * fraction + startPoint.getY();
            double rotation = (endPoint.getRotation() - startPoint.getRotation()) * fraction;

            return new Coordinate(x, y, rotation);
        }
    }

    /**
     * Pass in the elapsed time since this line has started to get the coordinates at that time
     * @param time
     * @return Coordinate of where it is at this relative time
     */
    public Coordinate getScaledCoordinateAtTime(double time) {
        double x, y;
        double scale = GraphicsConstants.SCALE_FACTOR;
        if (time <= 0) {
            return startPoint;
        }
        else if (time >= timeToComplete) {
            return endPoint;
        }
        else { //we are somewhere in the middle of the line
            double fraction = time / timeToComplete;
            x = ((endPoint.getX()*scale) - (startPoint.getX()*scale)) * fraction + (startPoint.getX() * scale);
            y = ((endPoint.getY()*scale) - (startPoint.getY()*scale)) * fraction + (startPoint.getY() * scale);
            return new Coordinate(x, y);
        }
    }

}
