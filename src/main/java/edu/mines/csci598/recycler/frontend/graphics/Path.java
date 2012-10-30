package edu.mines.csci598.recycler.frontend.graphics;

import java.util.ArrayList;

/**
 * A Path is a representation of the path an item is to follow on the screen. It consists
 * of a list of Lines.
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Path {
    ArrayList<Line> path = new ArrayList<Line>();

    public Path(){

    }

    /**
     * This function adds the Line to the end of the path.
     * @param l
     * @return
     */
    public boolean addLine(Line l){
        return path.add(l);
    }


    /**
     *
     * @param startTime The time the path starts
     * @param referenceTime The time where you want to see it is along the path.
     * @return
     */
    // TODO this needs better documentation
    public Coordinate getLocation(double startTime, double referenceTime){
        Coordinate coordinate = new Coordinate(0,0);
        double time = referenceTime - startTime;
        for(Line l : path){
            double currentLineTotalTime = l.getTotalTime();
            if(time <= currentLineTotalTime || l == path.get(path.size()-1)){
                coordinate= l.getLocation(time);
                break;
            }
            time = time - currentLineTotalTime;
        }
        return coordinate;
    }
}
