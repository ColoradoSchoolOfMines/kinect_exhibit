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
    public static RecyclableType getRandom(int numberOfItemTypesInUse)
    {
    	int numRecyclableTypes = RecyclableType.values().length;
    	
    	if(numberOfItemTypesInUse > numRecyclableTypes){ // error checking, just use the number of recyclables instead
    		numberOfItemTypesInUse = numRecyclableTypes;
    	}
    	
    	// Generates a choice between 0 and the number of items in use passed in, or the total number available
    	int randomChoiceIndex = rand.nextInt(numberOfItemTypesInUse);
    	return RecyclableType.values()[randomChoiceIndex];
    }
}
