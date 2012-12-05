package edu.mines.csci598.recycler.splashscreen.playerdetector;

import com.googlecode.javacv.ObjectFinder;
import com.googlecode.javacv.cpp.opencv_core;
import edu.mines.csci598.recycler.backend.GameManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

public class DetectLocation {
    static final int _threshhold = 30;
    private boolean _inWatchedArea;
    private ObjectFinder _finder;
    private long _waitTime;
    private long _startedDetecting;
    private boolean _detected;
    private GameManager _manager;
    private int[][] _watchedArea;

    public DetectLocation( long waitTime, GameManager manager){
        this._startedDetecting = 0;
        this._waitTime = waitTime;
        this._manager = manager;

        _inWatchedArea = false;
        _watchedArea = new int[4][2];
        BufferedImage bimg;
        // Setup the object finder
        try{
             bimg = ImageIO.read(new File("recycle.jpg"));

            opencv_core.IplImage image = opencv_core.IplImage.createFrom(bimg);
            ObjectFinder.Settings settings = new ObjectFinder.Settings();
            settings.setObjectImage(image);
            settings.setRansacReprojThreshold(5);
            settings.setDistanceThreshold(0.6);
            //settings.setMatchesMin(10);
            settings.setUseFLANN(true);
            _finder = new ObjectFinder(settings);
        }
        catch( Exception e ){

        }


        updateAreaLocation();
    }

    public boolean playerFound(){
        return false;
    }

    /*
    * Finds the location of the image in the scene
    */
    void updateAreaLocation(){
        opencv_core.IplImage scene = opencv_core.IplImage.createFrom( _manager.getImage() );

        try{
            double[] points = _finder.find(scene);

            // Don't put them directly into watchedArea in case something blows up
            int[][] temp = new int[4][2];

            // There are always four points output from the finder
            for( int i = 0; i < 4; i++ ){
                temp[i][0] = (int)Math.round( points[2*i] );
                temp[i][1] = ( int )Math.round( points[2*i+1] );
            }

            _watchedArea = temp;
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    boolean inWatchedArea(  ){
        BufferedImage depth = _manager.getDepth();

        /*
         * Checks for rough linear gradient in depth map
         * Checks midpoint between each line consisting of corners of recognized object
         */
        boolean linear = true;
        try{
            for( int pt1 = 0; pt1 < 3; pt1++){
                for( int pt2 = pt1+1; pt2 < 4; pt2++ ){
                    int pt1depth = depth.getRGB( _watchedArea[pt1][0], _watchedArea[pt1][1] );
                    int pt2depth = depth.getRGB( _watchedArea[pt2][0], _watchedArea[pt2][1] );

                    int xMidpointDepth = Math.abs( _watchedArea[pt1][0] - _watchedArea[pt2][0] ) +
                            Math.min(_watchedArea[pt2][0], _watchedArea[pt1][0] );

                    int yMidpointDepth = Math.abs( _watchedArea[pt1][1] - _watchedArea[pt2][1] ) +
                            Math.min(_watchedArea[pt2][1], _watchedArea[pt1][1] );

                    int avg = pt1depth/2 + pt2depth/2;
                    if( Math.abs( avg - yMidpointDepth ) > _threshhold ){
                        linear = false;
                    }
                }
            }
        }
        catch( Exception e ){

        }

        return linear;
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

    private boolean startConditionsMet(){
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

    public int[][] getWatchedArea(){
        return _watchedArea;
    }
}
