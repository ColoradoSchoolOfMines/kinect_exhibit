package edu.mines.csci598.recycler.frontend;

import java.util.Random;

/**
 * The possible types of "Recyclables"
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/21/12
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public enum RecyclableType {
    PLASTIC,
    PAPER,
    GLASS,
    TRASH;
    
    private static final Random rand = new Random();
    public static RecyclableType getRandom()
    {
    	int randomChoiceIndex = rand.nextInt(RecyclableType.values().length);
    	return RecyclableType.values()[randomChoiceIndex];
    }
}
