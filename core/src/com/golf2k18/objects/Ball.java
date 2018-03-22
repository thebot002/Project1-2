package com.golf2k18.objects;

public class Ball 
{
	private double mass;
	private Vector velocity;
	private double x;
	private double y;
	private double z;
	
	public Ball(double x, double y, Vector v, double mass){
		velocity = v;
		this.x = x;
		this.y = y; 
		this.mass = mass;
		
	}
	
	public Ball(double mass) {
		velocity = new Vector(0,0,0);
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.mass = mass;
	}
	
	public void hit(Vector vector) {
		this.velocity = vector;
		
	}
	
	public void updateVelocityX(double x) {
		velocity.setX(x);
	}
	public void updateVelocityY(double y)
    {
        velocity.setY(y);
    }
	public void updateLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public Vector getVelocity()
	{
		return this.velocity;
	}

	
	
	
	
}

