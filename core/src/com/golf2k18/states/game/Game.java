package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Course;
import com.golf2k18.objects.Engine;
import com.golf2k18.objects.Vector;
import com.golf2k18.states.StateManager;

import java.util.HashMap;

public class Game extends State3D {

    private Engine engine;
    private Ball ball;
    private Stage hud;
    private Skin skin;

    private Stage pause;
    private boolean paused = false;

    private Vector3 distanceCamBall = new Vector3();

    private boolean followBall = false;
    private boolean manualMovement = false;

    private Slider directionInput;
    private Slider intensityInput;

    private HashMap<String,Label> labels;

    public Game(StateManager manager, Course course) {
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

        //creation of labels
        labels = new HashMap<>();
        labels.put("score",new Label("Score",skin));
        labels.put("par", new Label("Par: ", skin));
        labels.put("title", new Label("Hole #", skin,"title"));
        labels.put("focus", new Label("",skin));
        labels.put("distance", new Label("Distance to hole:",skin));
        labels.put("speed", new Label("Speed:",skin));

        //used to organise the different input elements
        Table table = new Table();
        table.setFillParent(true);

        VerticalGroup scoreGroup = new VerticalGroup();
        scoreGroup.addActor(labels.get("score"));
        scoreGroup.addActor(labels.get("par"));
        table.add(scoreGroup).top().left().uniform().pad(10f);

        table.add(labels.get("title")).center().expand().top().pad(10f);

        Image windrose = new Image(new Texture("windrose.png"));
        table.add(windrose).top().right().uniform().pad(10f);

        table.row();

        table.add(labels.get("focus")).left().bottom().pad(10f);

        VerticalGroup inputGroup = new VerticalGroup();

        //direction field
        HorizontalGroup directionGroup = new HorizontalGroup();
        Label directionText = new Label("Direction: ",skin);
        directionGroup.addActor(directionText);
        directionInput = new Slider(-180f,180f,1,false,skin);
        directionInput.setValue(0);
        directionGroup.addActor(directionInput);
        inputGroup.addActor(directionGroup);

        //intensity field
        HorizontalGroup intensityGroup = new HorizontalGroup();
        Label intensityText = new Label("Intensity: ",skin);
        intensityGroup.addActor(intensityText);
        intensityInput = new Slider(1f,20f,1,false,skin);
        intensityGroup.addActor(intensityInput);
        inputGroup.addActor(intensityGroup);

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
        inputGroup.addActor(hitButton);
        table.add(inputGroup).center().bottom().pad(10f);

        VerticalGroup ballInfo = new VerticalGroup();
        Label distance = new Label("Distance to hole: ",skin);
        ballInfo.addActor(distance);

        Label speed = new Label("Ball speed: ",skin);
        ballInfo.addActor(speed);

        table.add(ballInfo).bottom().right().pad(10f);

        hud.addActor(table);
    }

    private void createPause(){
        pause = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Table organizer = new Table();
        organizer.setFillParent(true);

        Label pauseText = new Label("Paused",skin,"title");
        organizer.add(pauseText).center().pad(20f);

        TextButton settings = new TextButton("Settings",skin);
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        organizer.row();
        organizer.add(settings).top().pad(10f);

        TextButton menu = new TextButton("Menu", skin);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });
        organizer.row();
        organizer.add(menu).top().pad(10f);

        TextButton resume = new TextButton("Resume", skin);
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume();
            }
        });
        organizer.row();
        organizer.add(resume).top().pad(10f);

        pause.addActor(organizer);
    }

    @Override
    public void render() {
        super.render();
        hud.act();
        hud.draw();
        if(paused){
            pause.getBatch().begin();
            pause.getBatch().draw(new Texture("grey_background.png"),0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            pause.getBatch().end();

            pause.act();
            pause.draw();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        paused = !paused;
        createPause();
        Gdx.input.setInputProcessor(pause);
    }

    @Override
    public void resume() {
        paused = !paused;
        setProcessors();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(!ball.isStopped()) engine.updateBall(ball);
        /*if(followBall){
            Vector3 newPos = ball.getPosition().toVector3();
            newPos.add(distanceCamBall);
            camera.position.set(newPos);
        }*/
        ball.setZ(engine.getCourse().getFunction().evaluateF(ball.getX(),ball.getY()));

        ball.getModel();
    }

    @Override
    public void handleInput() {
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            ball.setX(ball.getX()+0.15);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            ball.setX(ball.getX()-0.15);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            ball.setY(ball.getY()+0.15);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            ball.setY(ball.getY()-0.15);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            followBall = !followBall;
            if(followBall){
                labels.get("focus").setText("Ball focus ON");
                //updateCamBallDistance();
                //lookAtBall();
                controller.focus(ball.getPosition().toVector3());
                //setProcessors();
            }
            else{
                controller.unfocus();
                labels.get("focus").setText("");
                //setProcessors();
            }
        }
        /*if(followBall){
            if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
                camera.rotateAround(ball.getPosition().toVector3(),camera.up,1f);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
                camera.rotateAround(ball.getPosition().toVector3(),camera.up,-1f);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
                Vector3 normal = new Vector3(camera.up.x,camera.up.y,camera.up.z);
                normal.rotate(camera.direction,90);
                camera.rotateAround(ball.getPosition().toVector3(),normal,1f);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
                Vector3 normal = new Vector3(camera.up.x,camera.up.y,camera.up.z);
                normal.rotate(camera.direction,90);
                camera.rotateAround(ball.getPosition().toVector3(),normal,-1f);
            }
            updateCamBallDistance();
        }*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            double dir = directionInput.getValue();
            double intensity = intensityInput.getValue();

            ball.hit(new Vector(Math.cos(Math.toRadians(dir))*intensity , Math.sin(Math.toRadians(dir))*intensity , 0));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(!paused) pause();
            else resume();
        }
    }

    private void setProcessors(){
        if(followBall){
            Gdx.input.setInputProcessor(hud);
        }
        else{
            Gdx.input.setInputProcessor(new InputMultiplexer(hud, this, controller));
        }
    }

    private void lookAtBall(){
        camera.lookAt(ball.getPosition().toVector3());
    }

    private void updateCamBallDistance(){
        Vector3 posBall = ball.getPosition().toVector3().scl(-1f);
        distanceCamBall.set(posBall.add(camera.position));
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
