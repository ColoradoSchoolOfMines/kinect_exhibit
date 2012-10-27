package edu.mines.csci598.recycler.frontend.graphics;

/**
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line {
    private  double startX, startY, endX, endY, totalTime;
    public Line(double startX, double startY, double endX, double endY, double totalTime){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.totalTime = totalTime;

        //TODO: Add error detection

    }

    public Coordinate getLocation( double time){
       double x,y;
       if(time <= 0){
           x=startX;
           y=startY;
       } else if (time >= totalTime){
           x=endX;
           y=endY;
       } else { //we are somewhere in the middle of the line
           double fraction = time/totalTime;
           x=(endX-startX)*fraction+startX;
           y=(endY-startY)*fraction+startY;
       }
       return new Coordinate(x,y);
    }

    public double getTotalTime(){
        return totalTime;
    }


}
