package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Ball
{
	private double mass;
	private Vector velocity;

	private Vector3 position;
	private Texture ball;

	public Ball(int x, int y, Vector v, double mass){
		velocity = v;
        position = new Vector3(x,y,0);
        ball = new Texture("golfBall.png");
		this.mass = mass;
		
	}
	
	public Ball(double mass) {
		velocity = new Vector(0,0,0);
        position = new Vector3(0,0,0);
		this.mass = mass;
        ball = new Texture("golfBall.png");
    }

    public Ball(){
        velocity = new Vector(0,0,0);
        position = new Vector3(0,0,0);
        this.mass = 0.45;
        ball = new Texture("golfBall.png");
    }

    public Texture getTexture() {
        return ball;
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
		position.x = (float) x;
		position.y = (float) y;
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getX() {
		return (double)position.x;
	}
	public double getY() {
		return (double)position.y;
	}
	public double getZ() {
		return (double)position.z;
	}
	public void setX(double x) {
		position.x = (float)x;
	}
	public void setY(double y) {
		position.y = (float)y;
	}
	public void setZ(double z) {
		position.z = (float)z;
	}
	public Vector getVelocity()
	{
		return this.velocity;
	}

	
	
	
	
}

