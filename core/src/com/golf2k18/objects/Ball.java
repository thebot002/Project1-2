package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.states.game.Game;

/**
 * Class that contains all the necessary information about the ball
 */
public class Ball {
    private final float DIAMETER = 0.42f; //dm
	private final float MASS = 0.45f; //g

	private Vector3 velocity = new Vector3();
	private Vector3 position;

    private boolean stopped = true;
    private boolean rolledOver = false;

    private Texture ballTexture = new Texture("Textures/ball_texture.jpg");
    private ModelInstance model;



    /**
     * Constructor for the ball class, this class creates the model and takes a Vector3 for position.
     * @param position variable that hold the x and y coordinate for the ball.
     */
    public Ball(Vector3 position) {
        this.position = position;
        createModel();
    }

    /**
     * Method which is used to apply a given force(Vector3) to this ball object.
     * @param vector variable which specifies the force that should be applied to the ball.
     */
    public void hit(Vector3 vector) {
	    stopped = false;
		this.velocity = vector.cpy();
	}

    /**
     * Method used to know the mass of the ball.
     * @return the constant mass of the ball.
     */
	public float getMass() {
		return MASS;
	}

    public float getDiameter() {
        return DIAMETER;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity()
	{
		return velocity;
	}

    /**
     * Method that gives the ball's movement vector when it's stopped (0,0,0)
     */
    public void setStopped() {
	    velocity = new Vector3(0,0,0);
        this.stopped = true;
    }

    public boolean isStopped(){
        return stopped;
    }

    public ModelInstance getModel() {
        return model;
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

    public void updateInstance(float z){
        position.z = z;
        model.transform.setTranslation(position.x,position.y,position.z+(DIAMETER/2));
    }
    public void setPosition(Vector3 v){
        this.position=v;
    }
    public void setVelocity(Vector3 velocity){
        this.velocity = velocity;
    }
    public void setRolledOver(boolean b){this.rolledOver = b;}
    public boolean isRolledOver() {return rolledOver;}
}
