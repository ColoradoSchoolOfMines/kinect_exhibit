package edu.mines.csci598.recycler.frontend.graphics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Testing Path class
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PathTest extends TestCase {

    public PathTest(String testName) {
        super(testName);
    }


    public static Test suite() {
        return new TestSuite(PathTest.class);
    }
    
    public void testGetLocation(){
    	double precision = 0.1;
    	
        Path p = new Path();
        p.addLine(new Line(0, 0, 5, 0));
        p.addLine(new Line(5, 0, 5, 15));
        p.addLine(new Line(5, 15, 10, 15));
    	
        Coordinate start;
        Coordinate end;
        
        // Test trying to move a point not on the line does nothing
        start = new Coordinate(2, 1);
        end = p.getLocation(start, 5, 5);
        assertEquals(2.0, end.getX());
        assertEquals(1.0, end.getY());


        start = new Coordinate(0, 0);
        
        
        // Test normal cases
        end = p.getLocation(start, 1.2, 2);
        assertEquals(2, end.getX(), precision);
        assertEquals(0, end.getY(), precision);

        start = new Coordinate(0, 0);
        end = p.getLocation(start, 0.8, 2);
        assertEquals(2, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        // Test corners
        end = p.getLocation(start, 0.1, 49);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 50);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 51);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 54);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 55);
        assertEquals(5, end.getX(), precision);
        assertEquals(1, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 56);
        assertEquals(5, end.getX(), precision);
        assertEquals(1, end.getY(), precision);
        
        // Test end
        end = p.getLocation(start, 0.1, 244);
        assertEquals(9, end.getX(), precision);
        assertEquals(15.0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 245);
        assertEquals(10, end.getX(), precision);
        assertEquals(15.0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 249);
        assertEquals(10, end.getX(), precision);
        assertEquals(15.0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 250);
        assertEquals(10, end.getX(), precision);
        assertEquals(15, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 251);
        assertEquals(10, end.getX(), precision);
        assertEquals(15, end.getY(), precision);
        
        // Test backwards - around a corner!
        start = new Coordinate(5, 2);
        end = p.getLocation(start, -0.4, 7);
        assertEquals(4, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        // Test backwards to start
        end = p.getLocation(start, -0.3, 282);
        assertEquals(0, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        // Test broken path
        p = new Path();
        p.addLine(new Line(0, 0, 5, 0));
        p.addLine(new Line(5, 15, 10, 15));
        start = new Coordinate(0, 0);
        end = p.getLocation(start, 1, 10);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
    }

    public void testNumericStability(){
        int iterations = 100000; //100k
        double timeStep = 0.001; //total example time is 100 seconds
        double pixPerSec = 1;
        int finalPosition = (int) Math.round((Math.sqrt(100*100+100*100)/2));
        Path p = new Path();

        Line l = new Line(0,0,100,100);
        p.addLine(l);

        Coordinate expected = new Coordinate(finalPosition,finalPosition);
        Coordinate start = new Coordinate(0,0);
        //If it is going at 1px/second and travels for 100 seconds it should reach (70.71...,70.71...)
        Coordinate actualInOneStep = p.getLocation(start,pixPerSec,iterations*timeStep);

        assertEquals(expected.getX(),actualInOneStep.getX(),0.1);
        assertEquals(expected.getY(),actualInOneStep.getY(),0.1);


        Coordinate actualInManySteps = new Coordinate(0,0);
        double timeSinceLastMotion = 0;
        //Simulating calling the git position every 0.001 seconds
        for(int i =0; i<iterations; i++){
        	timeSinceLastMotion += timeStep;
        	Coordinate nextPosition = p.getLocation(actualInManySteps,pixPerSec,timeSinceLastMotion);
        	if(!nextPosition.equals(actualInManySteps)){
        		actualInManySteps = nextPosition;
        		timeSinceLastMotion = 0;
        	}
        }

        assertEquals(expected.getX(),actualInManySteps.getX(),0.1);
        assertEquals(expected.getY(),actualInManySteps.getY(),0.1);

    }
}
