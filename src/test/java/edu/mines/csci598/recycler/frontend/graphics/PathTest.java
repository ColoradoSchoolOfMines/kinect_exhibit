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

        Path p = new Path( 0.0);
        p.addLine(new Line(0, 0, 5, 0,10));
        p.addLine(new Line(5, 0, 5, 15,10));
        p.addLine(new Line(5, 15, 10, 15,10));

        Coordinate end;
        // Test going through and before the start and end time.
        end = p.getLocation(-1);
        assertEquals(0, end.getX(), precision);
        assertEquals(0, end.getY(), precision);

        end = p.getLocation(0);
        assertEquals(0, end.getX(), precision);
        assertEquals(0, end.getY(), precision);



        end = p.getLocation(5);
        assertEquals(2.5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);

        end = p.getLocation(10);
        assertEquals(5, end.getX(), precision);
        assertEquals(0, end.getY(), precision);

        end = p.getLocation(15);
        assertEquals(5, end.getX(), precision);
        assertEquals(7.5, end.getY(), precision);

        end = p.getLocation(20);
        assertEquals(5, end.getX(), precision);
        assertEquals(15, end.getY(), precision);

        end = p.getLocation(25);
        assertEquals(7.5, end.getX(), precision);
        assertEquals(15, end.getY(), precision);

        end = p.getLocation(30);
        assertEquals(10, end.getX(), precision);
        assertEquals(15, end.getY(), precision);

        end = p.getLocation(105);
        assertEquals(10, end.getX(), precision);
        assertEquals(15, end.getY(), precision);




      }

}
