package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Point2D;

import junit.framework.TestCase;
import junit.framework.Test;
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
        p.addLine(new Line(0.0, 0.0, 5.0, 0.0));
        p.addLine(new Line(5.0, 0.0, 5.0, 15.0));
        p.addLine(new Line(5.0, 15.0, 10.0, 15.0));
    	
        Point2D start;
        Point2D end;
        
        // Test trying to move a point not on the line does nothing
        start = new Coordinate(2, 1);
        end = p.getLocation(start, 5, 5);
        assertEquals(2.0, end.getX());
        assertEquals(1.0, end.getY());


        start = new Coordinate(0, 0);
        
        
        // Test normal cases
        end = p.getLocation(start, 1.2, 3);
        assertEquals(3.6, end.getX(), precision);
        assertEquals(0.0, end.getY(), precision);

        start = new Coordinate(0, 0);
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
        start = new Coordinate(5, 2);
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
        start = new Coordinate(0, 0);
        end = p.getLocation(start, 1, 10);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);
    }
}
