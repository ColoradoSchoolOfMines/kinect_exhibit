package edu.mines.csci598.recycler.frontend;

import java.util.LinkedList;

import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;

public class ConveyorBelt {
	private Path p;
	private LinkedList<Recyclable> recyclables;
	
    private Line bottomLine = new Line(GameConstants.BOTTOM_PATH_START_X,GameConstants.BOTTOM_PATH_START_Y,
            GameConstants.BOTTOM_PATH_END_X,GameConstants.BOTTOM_PATH_END_Y,GameConstants.BOTTOM_PATH_TIME);
    private Line verticalLine = new Line(GameConstants.VERTICAL_PATH_START_X,GameConstants.VERTICAL_PATH_START_Y,
    		GameConstants.VERTICAL_PATH_END_X,GameConstants.VERTICAL_PATH_END_Y,GameConstants.VERTICAL_PATH_TIME);
    private Line topLine = new Line(GameConstants.TOP_PATH_START_X,GameConstants.TOP_PATH_START_Y,
    		GameConstants.TOP_PATH_END_X,GameConstants.TOP_PATH_END_Y,GameConstants.TOP_PATH_TIME);

	public ConveyorBelt(){
		recyclables = new LinkedList<Recyclable>();
		
		p = new Path();
		p.addLine(bottomLine);
		p.addLine(verticalLine);
		p.addLine(topLine);
	}
	
	public synchronized LinkedList<Recyclable> getRecyclables(){
		return recyclables;
	}
	
	public synchronized void addRecyclable(Recyclable r){
		recyclables.add(r);
		r.getSprite().setPath(p);
	}
	
	public synchronized void removeRecyclable(Recyclable r){
		recyclables.remove(r);
	}
}



