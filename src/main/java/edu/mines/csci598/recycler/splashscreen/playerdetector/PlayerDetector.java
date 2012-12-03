package edu.mines.csci598.recycler.splashscreen.playerdetector;

import edu.mines.csci598.recycler.backend.GameManager;

public abstract class PlayerDetector {
    long _waitTime;
    long _startedDetecting;
    boolean _detected;
    GameManager _manager;

    public PlayerDetector( long waitTime, GameManager manager ){
        this._startedDetecting = 0;
        this._waitTime = waitTime;
        this._manager = manager;
    }

    abstract protected boolean playerFound();

    boolean startGame(){

        // If the player has been detected in the past is currently detected and the time since detection is greater
        // than the time to wait then return true
        if( startConditionsMet() ){
            return true;
        }

        // Make nessicary variable changes
        if( playerFound() ){

            // If this is the first frame the person is detected
            if( !_detected ){
                _startedDetecting = System.currentTimeMillis();
            }

            _detected = true;
        }
        else{
            _detected = false;
        }

        return false;
    }

    boolean startConditionsMet(){
        // If a player hasn't been detected yet
        if( !_detected ){
            return false;
        }
        // If the player is not currently found
        else if( !playerFound() ){
            return false;
        }
        // If the time with the player detected has not yet been met
        else if( (System.currentTimeMillis() - _startedDetecting) < _waitTime ){
            return false;
        }
        else{
            return true;
        }
    }
}
