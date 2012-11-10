package edu.mines.csci598.recycler.frontend.graphics;

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

    public void testGetLocation() throws Exception {
        Path p = new Path();
        p.addLine(new Line(0.0, 0.0, 0.5, 0.8, 4.0));
        p.addLine(new Line(0.5, 0.8, 0.5, 0.2, 8.0));
        p.addLine(new Line(0.5, 0.2, 1.0, 1.0, 4.0));

        Coordinate c = p.getLocation(0.0,2.0);
        assertEquals(0.25,c.x,0.001);
        assertEquals(0.4,c.y,0.001);

        c = p.getLocation(0.0,4.0);
        assertEquals(0.5,c.x,0.001);
        assertEquals(0.8,c.y,0.001);

        c = p.getLocation(0.0,8.0);
        assertEquals(0.5,c.x,0.001);
        assertEquals(0.5,c.y,0.001);

        c = p.getLocation(0.0,12.0);
        assertEquals(0.5,c.x,0.001);
        assertEquals(0.2,c.y,0.001);

        c = p.getLocation(0.0,14.0);
        assertEquals(0.75,c.x,0.001);
        assertEquals(0.6,c.y,0.001);

        c = p.getLocation(0.0,18.0);
        assertEquals(1.0,c.x,0.001);
        assertEquals(1.0,c.y,0.001);
    }
}
