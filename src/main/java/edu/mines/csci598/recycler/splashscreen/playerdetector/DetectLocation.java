package edu.mines.csci598.recycler.splashscreen.playerdetector;

import com.googlecode.javacv.ObjectFinder;
import edu.mines.csci598.recycler.backend.GameManager;

import java.awt.*;

public class DetectLocation extends PlayerDetector{
    Polygon _area;
    boolean _inWatchedArea;
    Thread _areaWatcher;
    ObjectFinder _finder;

    public DetectLocation( long waitTime, GameManager manager){
        super( waitTime, manager );
    }

    public boolean playerFound(){
        return false;
    }
}
