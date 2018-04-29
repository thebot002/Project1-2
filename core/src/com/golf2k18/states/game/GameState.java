package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Course;
import com.golf2k18.objects.Engine;
import com.golf2k18.objects.Vector;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;

public class GameState extends State3D {

    private Engine engine;
    private Ball ball;
    private Stage hud;
    private Skin skin;

    public GameState(StateManager manager, Course course) {
        super(manager, course);
    }

    @Override
    public void create() {
        super.create();

        ball = new Ball();
        instances.add(ball.getModel());

        engine = new Engine();
        createHUD();

        Gdx.input.setInputProcessor(new InputMultiplexer(hud, this, controller));
    }

    private void createHUD(){
        hud = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));

        //used to organise the different input elements
        Table table = new Table();
        table.setFillParent(true);

        //direction field
        HorizontalGroup directionGroup = new HorizontalGroup();

        Label directionText = new Label("Direction: ",skin);
        Slider directionInput = new Slider(-180f,180f,1,false,skin);
        directionInput.setValue(0);

        directionGroup.addActor(directionText);
        directionGroup.addActor(directionInput);
        table.add(directionGroup).expand().center().bottom().padBottom(10f);

        //intensity field
        HorizontalGroup intensityGroup = new HorizontalGroup();

        Label intensityText = new Label("Intensity: ",skin);
        //TextField intensityInput = new TextField("",skin);
        Slider intensityInput = new Slider(1f,20f,1,false,skin);

        intensityGroup.addActor(intensityText);
        intensityGroup.addActor(intensityInput);
        intensityGroup.setPosition(100,50);

        table.row();
        table.add(intensityGroup).pad(10f);

        //hit button
        TextButton hitButton = new TextButton("Hit",skin);
        hitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double dir = directionInput.getValue();
                double intensity = intensityInput.getValue();

                ball.hit(new Vector(Math.cos(Math.toRadians(dir))*intensity , Math.sin(Math.toRadians(dir))*intensity , 0));
            }
        });

        table.row();
        table.add(hitButton).pad(10f);

        hud.addActor(table);
    }

    @Override
    public void render() {
        super.render();
        hud.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        hud.act();
        hud.draw();
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
    public void update(float dt) {
        super.update(dt);
        if(!ball.isStopped()) engine.updateBall(ball);
        ball.setZ(engine.getCourse().getFunction().evaluateF(ball.getX(),ball.getY()));
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


    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
