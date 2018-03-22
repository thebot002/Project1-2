package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;

public class Ball
{
	private final double MASS = 0.45;
	private Vector velocity;

	private Vector position;
	private Texture ball;

	public Ball(double x, double y, Vector v){
		velocity = v;
        position = new Vector(x,y,0);
        ball = new Texture("golfBall.png");
    }

    public Ball(){
        velocity = new Vector(1,1,0);
        position = new Vector(0,0,0);
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
        position.x = x;
		position.y = y;
	}
	
	public double getMass() {
		return MASS;
	}
	
	public double getX() {
		return position.x;
	}
	public double getY() {
		return position.y;
	}
	public double getZ() {
		return position.z;
	}
	public void setX(double x) {
		position.x = x;
	}
	public void setY(double y) {
		position.y = y;
	}
	public void setZ(double z) {
		position.z = z;
	}
	public Vector getVelocity()
	{
		return velocity;
	}

	
	
	
	
}

