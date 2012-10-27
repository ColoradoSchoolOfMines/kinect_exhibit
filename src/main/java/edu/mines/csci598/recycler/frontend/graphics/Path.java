package edu.mines.csci598.recycler.frontend.graphics;

import java.util.ArrayList;

/**
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


    public boolean addLine(Line l){
        return path.add(l);
    }

    public Coordinate getLocation(double startTime, double endTime){
        Coordinate coordinate = new Coordinate(0,0);
        double time = endTime - startTime;
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
