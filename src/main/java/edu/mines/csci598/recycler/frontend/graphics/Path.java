package edu.mines.csci598.recycler.frontend.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * A Path is a representation of the path an item is to follow on the screen. It consists
 * of a list of Lines.  Lines on the path should *not* intersect or figuring out which
 * line a point is on will break.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Path {

    List<Line> path = new ArrayList<Line>();
    double startTime;

    /**
     * Create a path with a specific start time.
     * @param startTime
     */
    public Path(double startTime) {
        this.startTime = startTime;
    }

    /**
     * Create a path from a list of lines.
     * @param lines
     */
    public Path( List<Line> lines) {
    	for(Line l : lines){
    		path.add(l);
    	}
    }

    /**
     * Create a path from an existing path.
     * @param existingPath
     */
    public Path(Path existingPath) {
    	for(Line l : existingPath.path){
    		path.add(l);
    	}
	}

	/**
     * This function adds the Line to the end of the path.
     *
     * @param l
     * @return if it was added successfully
     */
    public boolean addLine(Line l) {
        return path.add(l);
    }

    /**
     * Returns the location the item is based on the current time.
     * @param time
     * @return
     */
    public Coordinate getLocation(double time){
        Coordinate coordinate = new Coordinate(0, 0);
        double elapsedTime = time - startTime;
        for (Line l : path) {
            double currentLineTotalTime = l.getTimeToComplete();
            if (elapsedTime <= currentLineTotalTime || l == path.get(path.size() - 1)) {
                coordinate = l.getCoordinateAtTime(elapsedTime);
                break;
            }
            elapsedTime = elapsedTime - currentLineTotalTime;
        }
        return coordinate;
    }

    /**
     * Calculates if the current path has been finished or not
     * based on how long it would take it to go.
     * @param currentTimeSec
     * @return
     */
    public boolean PathFinished(double currentTimeSec) {
        double totalTime = 0;
        double elapsedTime = currentTimeSec - startTime;
        for(Line l: path){
            totalTime += l.getTimeToComplete();
        }
        return elapsedTime >= totalTime;
    }
    
    public Coordinate getInitialPosition(){
         Line l = path.get(0);
         if(l != null) {
             return (Coordinate) l.getP1();
         }
         else {
             return null;
         }
    }

    public void setStartTime(double time){
        startTime = time;
    }

    public List<Line> getPath() {
        return path;
    }

}
