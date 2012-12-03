package edu.mines.csci598.recycler.splashscreen.playerdetector;

import edu.mines.csci598.recycler.backend.GameManager;

/**
 * Created with IntelliJ IDEA.
 * User: jimmie
 * Date: 12/2/12
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetectHands extends PlayerDetector{
    public DetectHands( long waitTime, GameManager manager){
        super(waitTime, manager);
    }

    public boolean playerFound(){
        // if there exists a hand
        if( _manager.getSharedInputStatus().pointers[0][0] > 0 ){
            return true;
        }

        return false;
    }
}
