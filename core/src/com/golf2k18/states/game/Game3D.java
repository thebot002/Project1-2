package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Course;
import com.golf2k18.objects.Engine;
import com.golf2k18.states.StateManager;

public class Game3D extends GameHUD {

    //variables
    private Engine engine;
    private Course course;

    private Environment environment;
    private Ball ball;
    private ModelInstance ballInstance;

    public Game3D(StateManager gsm) {
        super(gsm);
        engine = new Engine();
        course = engine.getCourse();
    }

    @Override
    public void create() {
        super.create();

        //PointLight light = new PointLight();
        //light.set(.8f,.8f,.8f,0,10,20,200f);
        DirectionalLight light = new DirectionalLight();
        light.set(.8f,.8f,.8f, -1f,-1f,-1f);

        environment = new Environment();
        environment.clear();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
        environment.add(light);

        ball = new Ball();
        ballInstance = ball.getModel();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void render(ModelBatch batch, Array<ModelInstance> instances) {
        //batch.render(instances,environment);
        batch.render(course.world,environment);
        batch.render(ballInstance,environment);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(!ball.isStopped()) engine.updateBall(ball);
        ball.setZ(course.getFunction().evaluateF(ball.getX(),ball.getY()));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            ball.setX(ball.getX()+0.15);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            ball.setX(ball.getX()-0.15);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            ball.setY(ball.getY()+0.15);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            ball.setY(ball.getY()-0.15);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            ball.stopped(!ball.isStopped());
        }
    }
}
