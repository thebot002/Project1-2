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
	
	private void update() {
		position.x = velocity.getX();
		position.y = velocity.getY();
		
	}
	
	public double getMass() {
		return mass;
	}
	
	public int getX() {
		return (int)position.x;
	}
	public int getY() {
		return (int)position.y;
	}
	public int getZ() {
		return (int)position.z;
	}
	public void setX(int x) {
		position.x = x;
	}
	public void setY(int y) {
		position.y = y;
	}
	public void setZ(int z) {
		position.z = z;
	}
	public Vector getVelocity()
	{
		return this.velocity;
	}

	
	
	
	
}

