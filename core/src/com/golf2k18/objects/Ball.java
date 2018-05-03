package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Ball
{
    private final boolean DEBUG = false;

    //private final float DIAMETER = 2f; //test diameter
    private final float DIAMETER = .42f;
	private final double MASS = 0.45;
	private Vector3 velocity;

	private Vector3 position;
	private Texture ballTexture;

	private boolean stopped;

    private ModelInstance model;

	public Ball(double x, double y, Vector3 v){
		velocity = v;
        position = new Vector3((float)x,(float)y,0);
        ballTexture = new Texture("8wzgvr.jpg");
    }

    public Ball(){
        velocity = new Vector3(2,2,0);
        stopped = true;
        position = new Vector3(10,10,0);
        ballTexture = new Texture("8wzgvr.jpg");

        createModel();
    }

    public void hit(Vector3 vector) {
	    stopped = false;
		this.velocity = vector.cpy();
	}

	public void updateVelocityX(double x) {
	    if(DEBUG) System.out.println("x: " + x);
        velocity.x = (float) x;
	}

    public void updateVelocityY(double y) {
        if(DEBUG) System.out.println("y: " + y);
        velocity.y = (float)y;
    }
    public void updateLocation(double x, double y) {
        position.x = (float)x;
        position.y = (float)y;
        updateInstance();
	}

	public double getMass() {
		return MASS;
	}

    public Vector3 getPosition() {
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
		position.x = (float)x;
		updateInstance();
	}
    public void setY(double y) {
		position.y = (float)y;
		updateInstance();
	}
    public void setZ(double z) {
		position.z = (float)z;
		updateInstance();
	}

    public Vector3 getVelocity()
	{
		return velocity.cpy();
	}

    public void setStopped() {
	    velocity = new Vector3(0,0,0);
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

