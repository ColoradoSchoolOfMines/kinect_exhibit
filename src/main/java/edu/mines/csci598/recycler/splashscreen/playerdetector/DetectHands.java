package edu.mines.csci598.recycler.splashscreen.playerdetector;

import edu.mines.csci598.recycler.backend.GameManager;

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
