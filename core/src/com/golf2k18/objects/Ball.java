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

    private final float DIAMETER = .42f;
	private final float MASS = 0.45f;
	private Vector3 velocity;

	private Vector3 position;

    private Texture ballTexture = new Texture("Textures/ball_texture.jpg");

	private boolean stopped = true;

    private ModelInstance model;

    public Ball(Vector3 position) {
        this.position = position;
        createModel();
    }

    public void hit(Vector3 vector) {
	    stopped = false;
		this.velocity = vector.cpy();
	}

	public void updateVelocity(Vector3 velocity){
        this.velocity = velocity;
    }

    public void updateLocation(Vector3 position){
        this.position = position;
    }

	public float getMass() {
		return MASS;
	}

    public Vector3 getPosition() {
        return position;
    }

	public float getX() {
		return position.x;
	}

    public float getY() {
		return position.y;
	}
    public float getZ() {
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

    public float getDiameter() {
        return DIAMETER;
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

        model = new ModelInstance(tempSphere,position.x,position.y,position.z+(DIAMETER/2));
    }

    private void updateInstance(){
        model.transform.setTranslation(position.x,position.y,position.z+(DIAMETER/2));
    }

    public void stopped(boolean stopped){
	    this.stopped = stopped;
    }
}

