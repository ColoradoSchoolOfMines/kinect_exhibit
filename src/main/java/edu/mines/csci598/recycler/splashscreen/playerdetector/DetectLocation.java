package edu.mines.csci598.recycler.splashscreen.playerdetector;

import com.googlecode.javacv.ObjectFinder;
import com.googlecode.javacv.cpp.opencv_core;
import edu.mines.csci598.recycler.backend.GameManager;

import java.awt.*;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

public class DetectLocation extends Thread{
    Polygon _area;
    boolean _inWatchedArea;
    Thread _areaWatcher;
    ObjectFinder _finder;
    long _waitTime;
    long _startedDetecting;
    boolean _detected;
    GameManager _manager;

    public DetectLocation( long waitTime, GameManager manager){
        this._startedDetecting = 0;
        this._waitTime = waitTime;
        this._manager = manager;

        _inWatchedArea = false;
        this._areaWatcher = this;

        // Setup the object finder
        opencv_core.IplImage image = cvLoadImage("/home/jimmie/Downloads/box.png");
        ObjectFinder.Settings settings = new ObjectFinder.Settings();
        settings.setObjectImage(image);
        settings.setUseFLANN( true );
        settings.setRansacReprojThreshold(5);
        _finder = new ObjectFinder(settings);
    }

    public boolean playerFound(){
        return false;
    }

    /*
    * Finds the location of the image in the scene
    */
    void updateAreaLocation(){
        opencv_core.IplImage scene = opencv_core.IplImage.createFrom( _manager.getImage() );
        double[] points = _finder.find(scene);
        _area = new Polygon();

        // There are always four points output from the finder
        for( int i = 0; i < 4; i++ ){
            _area.addPoint( (int)points[i], ( int )points[i+1] );
        }
    }

    boolean inWatchedArea(  ){


        return _inWatchedArea;
    }

    void stopWatchingArea(){
        _areaWatcher.stop();
    }

    void startWatchingArea(){
        // Probably should be in it's own thread
        _areaWatcher.run();
    }

    public void run() {
        while( true ){
            updateAreaLocation();
        }
    }


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
