package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.engine.Engine;
import com.golf2k18.engine.solver.*;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.State3D;
import com.golf2k18.states.StateManager;

import java.util.HashMap;

/**
 * Class where every object interacts with each other.
 */
public class Game extends State3D {

    private Engine engine;
    private Ball ball;
    private Stage hud;

    private Stage pause;
    private boolean paused = false;

    private boolean manualMovement = false;

    private Slider directionInput;
    private Slider intensityInput;

    private HashMap<String,Label> labels;

    private boolean down = false;

    /**
     * Constructor for the Game class.
     * @param manager Instance of the GameManager which is currently used.
     * @param terrain Instance of the Terrain class which was selected by the user in the menus.
     */
    public Game(StateManager manager, Terrain terrain) {
        super(manager, terrain);
    }

    /**
     * Method responsible for creating the menu, this is a part of the requirements of libGDX.
     */
    @Override
    public void create() {
        super.create();

        ball = new Ball(terrain.getStart());
        instances.add(ball.getModel());

        engine = new Engine(terrain, ball,new BS());
        createHUD();

        Gdx.input.setInputProcessor(new InputMultiplexer(hud, this, controller));
    }
    //Method which creates the HUD for the game.
    private void createHUD(){
        hud = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //creation of labels
        labels = new HashMap<>();
        labels.put("score",new Label("Score",StateManager.skin));
        labels.put("par", new Label("Par: ", StateManager.skin));
        labels.put("title", new Label("Hole "+terrain.getName(), StateManager.skin,"title"));
        labels.put("focus", new Label("",StateManager.skin));
        labels.put("distance", new Label("Distance to hole:",StateManager.skin));
        labels.put("speed", new Label("Speed:",StateManager.skin));

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
        Label directionText = new Label("Direction: ",StateManager.skin);
        directionGroup.addActor(directionText);
        directionInput = new Slider(-180f,180f,1,false,StateManager.skin);
        directionInput.setValue(0);
        directionGroup.addActor(directionInput);
        inputGroup.addActor(directionGroup);

        //intensity field
        HorizontalGroup intensityGroup = new HorizontalGroup();
        Label intensityText = new Label("Intensity: ",StateManager.skin);
        intensityGroup.addActor(intensityText);
        intensityInput = new Slider(1f,20f,1,false,StateManager.skin);
        intensityGroup.addActor(intensityInput);
        inputGroup.addActor(intensityGroup);

        //hit button
        TextButton hitButton = new TextButton("Hit",StateManager.skin);
        hitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double dir = directionInput.getValue();
                double intensity = intensityInput.getValue();

                ball.hit(new Vector3((float)(Math.cos(Math.toRadians(dir))*intensity) , (float)(Math.sin(Math.toRadians(dir))*intensity) , 0));
            }
        });
        inputGroup.addActor(hitButton);
        table.add(inputGroup).center().bottom().pad(10f);

        VerticalGroup ballInfo = new VerticalGroup();
        Label distance = new Label("Distance to hole: ",StateManager.skin);
        ballInfo.addActor(distance);

        Label speed = new Label("Ball speed: ",StateManager.skin);
        ballInfo.addActor(speed);

        table.add(ballInfo).bottom().right().pad(10f);

        hud.addActor(table);
    }
    //Method which creates the pause state to push to the gameManager to temporarily pause the game.
    private void createPause(){
        pause = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Table organizer = new Table();
        organizer.setFillParent(true);

        Label pauseText = new Label("Paused",StateManager.skin,"title");
        organizer.add(pauseText).center().pad(20f);

        TextButton settings = new TextButton("Settings",StateManager.skin);
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        organizer.row();
        organizer.add(settings).top().pad(10f);

        TextButton menu = new TextButton("MainMenu", StateManager.skin);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });
        organizer.row();
        organizer.add(menu).top().pad(10f);

        TextButton resume = new TextButton("Resume", StateManager.skin);
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
            pause.getBatch().draw(new Texture("Textures/grey_background.png"),0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        if(!ball.isStopped()) engine.updateBall();
        if(controller.isFocused()) labels.get("focus").setText("Ball focus ON");
        else labels.get("focus").setText("");
        ball.setZ(engine.getTerrain().getFunction().evaluateF(ball.getX(),ball.getY()));
        if(ball.getPosition().x + (ball.getDiameter()/2) < 0){
            return;
        }
    }

    @Override
    public void handleInput() {
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            ball.setX(ball.getX()+0.1);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            ball.setX(ball.getX()-0.1);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            ball.setY(ball.getY()+0.1);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            ball.setY(ball.getY()-0.1);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            if(!controller.isFocused()){
                controller.focus(ball.getPosition());
            }
            else{
                controller.unfocus();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            if(manualMovement) setProcessors();
            else Gdx.input.setInputProcessor(new InputMultiplexer(hud, this));
            manualMovement = !manualMovement;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            double dir = directionInput.getValue();
            double intensity = intensityInput.getValue();

            ball.hit(new Vector3((float)(Math.cos(Math.toRadians(dir))*intensity) , (float)(Math.sin(Math.toRadians(dir))*intensity) , 0));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(!paused) pause();
            else resume();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            if(controller.isFocused()){
                labels.get("focus").setText("");
            }
        }
    }
    //Setting inputProcessor that processes the key-events and stuff like that.
    private void setProcessors(){
        Gdx.input.setInputProcessor(new InputMultiplexer(hud, this, controller));
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(getObject(screenX, screenY)==4) down = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(down){
            ModelBuilder builder = new ModelBuilder();
            Model line = builder.createArrow(getTerrainMousePos(screenX,screenY,terrain.getFunction().evaluateF(ball.getX(),ball.getY())),ball.getPosition(),
                    new Material(ColorAttribute.createDiffuse(Color.BLACK)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
            );
            if(instances.size == 6) instances.removeIndex(instances.size - 1);
            instances.add(new ModelInstance(line,0,0,0));
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(down && instances.size == 6){
            instances.removeIndex(instances.size - 1);
            Vector3 currentPos = new Vector3(ball.getPosition());
            ball.hit(new Vector3(currentPos.add(new Vector3(getTerrainMousePos(screenX,screenY,terrain.getFunction().evaluateF(ball.getX(),ball.getY()))).scl(-1))).scl(2));
        }
        down = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    /**
     * Method that disposes the memory heavy objects(libGDX requirements).
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    private Vector3 getTerrainMousePos(int mouseX, int mouseY, float height){
        Ray ray = camera.getPickRay(mouseX, mouseY);

        Plane p = new Plane(new Vector3(0,0,1),height);
        Vector3 intersect = new Vector3();

        Intersector.intersectRayPlane(ray,p,intersect);

        return intersect;
    }

    private int getObject (int screenX, int screenY) {
        Vector3 position = new Vector3();
        Ray ray = camera.getPickRay(screenX, screenY);
        int result = -1;
        final ModelInstance instance = instances.get(4);
        instance.transform.getTranslation(position);
        //position.add(instance.tr);
        final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
        if (dist2 <= (ball.getDiameter()/2) * (ball.getDiameter()/2)) {
            result = 4;
        }
        return result;
    }
}
