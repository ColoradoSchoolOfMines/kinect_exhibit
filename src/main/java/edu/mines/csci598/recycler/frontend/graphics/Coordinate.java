package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Point2D;

public final class Coordinate extends Point2D{
	// Our grid is in ints, so is our data structure
	// We rely on paths to set these correctly
	private int x;
	private int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		throw new IllegalArgumentException("Don't set Coordinates using doubles.  Use the int method instead!");
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Coordinate)){
			return false;
		}
		Coordinate c = (Coordinate)o;
		return c.x == this.x && c.y == this.y;
	}

}
