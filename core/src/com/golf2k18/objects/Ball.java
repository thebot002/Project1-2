package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Ball
{
    private final boolean DEBUG = false;

    //private final float DIAMETER = 2f; //test diameter
    private final float DIAMETER = .42f;
	private final double MASS = 0.45;
	private Vector velocity;

	private Vector position;
	private Texture ballTexture;

	private boolean stopped;

    private ModelInstance model;

	public Ball(double x, double y, Vector v){
		velocity = v;
        position = new Vector(x,y,0);
        ballTexture = new Texture("8wzgvr.jpg");
    }

    public Ball(){
        velocity = new Vector(2,2,0);
        stopped = true;
        position = new Vector(10,10,0);
        ballTexture = new Texture("8wzgvr.jpg");

        createModel();
    }

    public void hit(Vector vector) {
	    stopped = false;
		this.velocity = vector.copy();
	}

	public void updateVelocityX(double x) {
	    if(DEBUG) System.out.println("x: " + x);
        velocity.setX(x);
	}

    public void updateVelocityY(double y) {
        if(DEBUG) System.out.println("y: " + y);
        velocity.setY(y);
    }
    public void updateLocation(double x, double y) {
        position.x = x;
        position.y = y;
        updateInstance();
	}

	public double getMass() {
		return MASS;
	}

    public Vector getPosition() {
        return position;
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
		updateInstance();
	}
    public void setY(double y) {
		position.y = y;
		updateInstance();
	}
    public void setZ(double z) {
		position.z = z;
		updateInstance();
	}

    public Vector getVelocity()
	{
		return velocity.copy();
	}

    public void setStopped() {
	    velocity = new Vector(0,0,0);
        this.stopped = true;
    }

    public ModelInstance getModel() {
        return model;
    }

    public boolean isStopped(){
	    return stopped;
    }

    private void createModel(){
        ModelBuilder builder = new ModelBuilder();

        Model tempSphere;
        tempSphere = builder.createSphere(DIAMETER, DIAMETER, DIAMETER,
                50,50,
                new Material(TextureAttribute.createDiffuse(ballTexture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );

        model = new ModelInstance(tempSphere,(float)(position.x),(float)(position.y),(float)(position.z+(DIAMETER/2)));
    }

    private void updateInstance(){
        model.transform.setTranslation((float)(position.x),(float)(position.y),(float)(position.z+(DIAMETER/2)));
    }

    public void stopped(boolean stopped){
	    this.stopped = stopped;
    }
}

