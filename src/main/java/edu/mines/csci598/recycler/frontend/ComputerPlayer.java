package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/13/12
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComputerPlayer {
    public ComputerHand primary;
    public Path p;
    private Random random;
    private double lastStrikeTime;
    private double lastStrikeDelay;
    private double currentTime;

    public ComputerPlayer(double cts){
        primary = new ComputerHand();
        random = new Random(System.currentTimeMillis());
        lastStrikeTime=0;
        lastStrikeDelay=0.25;
        currentTime = cts;


    }
    public boolean ICanStrike(){
        boolean ret = false;
        int rand = random.nextInt(100)+1;
        if(rand>90){
            ret = true;
        }
        return ret;
    }
    public void followRecyclable(Recyclable r, double currentTimeSec){
        //Log.logInfo("rx="+r.getSprite().getScaledX()+",ry="+r.getSprite().getScaledY()+",hx="+primary.getSprite().getX()+
        //            ",hy="+primary.getSprite().getY());
        primary.getSprite().setY(r.getSprite().getScaledY());
        if(r.getSprite().isTouchable()){
            if(currentTimeSec > lastStrikeTime + lastStrikeDelay){
                if(ICanStrike()){
                    Log.logInfo("Strike");
                    //strikeRecyclable(r);
                    lastStrikeDelay=0.25;
                }else {
                    lastStrikeDelay+=0.25;
                }
            }
        }


    }
    public void strikeRecyclable(Recyclable r){
        int newX = r.getSprite().getX();
        int newY = r.getSprite().getY();
        Line l;
        if(newX>r.getSprite().getX())
            l = new Line(primary.x,primary.y,newX+100,newY,0.5);
        else
            l = new Line(primary.x,primary.y,newX-100,newY,0.5);
        p.addLine(l);
        primary.getSprite().setPath(p);

    }
    public void updateHandPosition(){

    }

}
