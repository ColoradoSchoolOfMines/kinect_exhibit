package edu.mines.csci598.recycler.frontend.graphics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.geom.Point2D;

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
        p.addLine(new Line(0.0, 0.0, 5.0, 0.0));
        p.addLine(new Line(5.0, 0.0, 5.0, 15.0));
        p.addLine(new Line(5.0, 15.0, 10.0, 15.0));
    	
        Point2D start;
        Point2D end;
        
        // Test trying to move a point not on the line does nothing
        start = new Point2D.Double(2, 1);
        end = p.getLocation(start, 5, 5);
        assertEquals(2.0, end.getX());
        assertEquals(1.0, end.getY());


        start = new Point2D.Double(0, 0);
        
        
        // Test normal cases
        end = p.getLocation(start, 1.2, 3);
        assertEquals(3.6, end.getX(), precision);
        assertEquals(0.0, end.getY(), precision);

        start = new Point2D.Double(0, 0);
        end = p.getLocation(start, 1.2, 3);
        assertEquals(3.6, end.getX(), precision);
        assertEquals(0.0, end.getY(), precision);
        
        // Test corners
        end = p.getLocation(start, 0.1, 49);
        assertEquals(4.9, end.getX(), precision);
        assertEquals(0.0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 50);
        assertEquals(5.0, end.getX(), precision);
        assertEquals(0.0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 51);
        assertEquals(5.0, end.getX(), precision);
        assertEquals(0.1, end.getY(), precision);
        
        // Test end
        end = p.getLocation(start, 0.1, 249);
        assertEquals(9.9, end.getX(), precision);
        assertEquals(15.0, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 250);
        assertEquals(10, end.getX(), precision);
        assertEquals(15, end.getY(), precision);
        
        end = p.getLocation(start, 0.1, 251);
        assertEquals(10, end.getX(), precision);
        assertEquals(15, end.getY(), precision);
        
        // Test backwards - around a corner!
        start = new Point2D.Double(5, 2);
        end = p.getLocation(start, -0.5, 5);
        assertEquals(4.5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        // Test backwards to start
        end = p.getLocation(start, -0.3, 282);
        assertEquals(0, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
        
        // Test broken path
        p = new Path();
        p.addLine(new Line(0, 0, 5, 0));
        p.addLine(new Line(5, 15, 10, 15));
        start = new Point2D.Double(0, 0);
        end = p.getLocation(start, 1, 10);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
    }

    public void testNumericStability(){
        int iterations = 100000; //100k
        double timeStep = 0.001; //total example time is 100 seconds
        double pixPerSec = 1;
        double finalPosition = Math.sqrt(100*100+100*100)/2;
        Path p = new Path();

        Line l = new Line(0,0,100,100);
        p.addLine(l);

        Point2D expected = new Point2D.Double(finalPosition,finalPosition);
        Point2D start = new Point2D.Double(0,0);
        //If it is going at 1px/second and travels for 100 seconds it should reach (70.71...,70.71...)
        Point2D actualInOneStep = p.getLocation(start,pixPerSec,iterations*timeStep);

        assertEquals(expected.getX(),actualInOneStep.getX(),0.1);
        assertEquals(expected.getY(),actualInOneStep.getY(),0.1);


        Point2D actualInManySteps = new Point2D.Double(0,0);
        //Simulating calling the git position every 0.001 seconds
        for(int i =0; i<iterations; i++){
            actualInManySteps = p.getLocation(actualInManySteps,pixPerSec,timeStep);
        }

        assertEquals(expected.getX(),actualInManySteps.getX(),0.1);
        assertEquals(expected.getY(),actualInManySteps.getY(),0.1);

    }
}
