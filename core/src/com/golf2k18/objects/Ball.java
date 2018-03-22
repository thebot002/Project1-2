package com.golf2k18.objects;

public class Ball 
{
	private double mass;
	private Vector velocity;
	private int x;
	private int y;
	private int z;
	
	public Ball(int x, int y, Vector v, double mass){
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
	
	private void update() {
		this.x = velocity.getX();
		this.y = velocity.getY();
		
	}
	
	public double getMass() {
		return mass;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public Vector getVelocity()
	{
		return this.velocity;
	}

	
	
	
	
}

