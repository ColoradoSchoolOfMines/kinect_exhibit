package edu.mines.csci598.recycler.frontend.graphics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 12/3/12
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteTest extends TestCase {
    public SpriteTest(String testName){
        super(testName);
    }
    public static Test suite() {
        return new TestSuite(SpriteTest.class);
    }
    public void testIsPointIn(){
        Sprite s = new Sprite("SomeFile",700,300);
        assertTrue(s.isPointInside(710,310));
    }
}
