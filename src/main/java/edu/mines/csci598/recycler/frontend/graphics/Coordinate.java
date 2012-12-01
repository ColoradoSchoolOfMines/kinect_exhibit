package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Point2D;

public final class Coordinate extends Point2D{
	private double x;
	private double y;
	private double rotation;
	public Coordinate(double x, double y){
		this.x = x;
		this.y = y;
        rotation=0;
	}
    public Coordinate(double x, double y, double rotation){
        this.x = x;
        this.y = y;
        this.rotation=rotation;
    }
	@Override
    public String toString(){
        return "("+x+","+y+")";
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

    public void setRotation(double r){
        rotation=r;
    }
	public double getRotation(){
        return rotation;
    }
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Coordinate)){
			return false;
		}
		Coordinate c = (Coordinate)o;
		return c.x == this.x && c.y == this.y;
	}
    public void setX(double x){
        this.x=x;

    }
    public void setY(double y){
        this.y=y;
    }

}
