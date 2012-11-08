package edu.mines.csci598.recycler.frontend;

import java.util.LinkedList;

import edu.mines.csci598.recycler.frontend.graphics.Path;

public class ConveyorBelt {
	private Path path;
	private LinkedList<Recyclable> recyclables;
	
	public ConveyorBelt(){
		recyclables = new LinkedList<Recyclable>();
	}
	
	public synchronized LinkedList<Recyclable> getRecyclables(){
		return recyclables;
	}
	
	public synchronized void addRecyclable(Recyclable r){
		recyclables.add(r);
	}
	
	public synchronized void removeRecyclable(Recyclable r){
		recyclables.remove(r);
	}
}
