package edu.mines.csci598.recycler.splashscreen.playerdetector;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.GameState;
import edu.mines.csci598.recycler.backend.OpenNIHandTrackerInputDriver;

public class TestDetectHand {
    public static void main(String[] args) {
        GameManager man = new GameManager( "TestPlayerDetector" );
        OpenNIHandTrackerInputDriver driver = new OpenNIHandTrackerInputDriver();
        driver.installInto( man );

        DetectHand dh = new DetectHand( 0, man );

        man.setState(dh);

        GameState state = man.getGameState();


        long lastClock = System.nanoTime();

        //Determine elapsed time
        long clock = System.nanoTime();
        float et = (clock-lastClock) * 1.0e-9f;

        while( !dh.playerFound() ){
            clock = System.nanoTime();
            et = (clock-lastClock) * 1.0e-9f;
            lastClock = clock;

            // Display splash screen stuff
            System.out.println( dh.playerFound() );

            state = man.getGameState();

            //Handle updating
            if (state != null) {
                state = state.update(et);
            }

            // Update info from the kinect
            driver.pumpInput( state );
        }

        // Move on to the game run loop
    }
}