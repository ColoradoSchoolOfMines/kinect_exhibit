package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Point2D;

public final class Coordinate extends Point2D{
	private double x;
	private double y;
	
	public Coordinate(double x, double y){
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
		throw new IllegalArgumentException("Don't setLocation.  Use the constructor instead!");
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
