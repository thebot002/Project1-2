package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.engine.Engine;
import com.golf2k18.handlers.Player;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Course;
import com.golf2k18.states.State3D;
import com.golf2k18.StateManager;
import com.golf2k18.states.menu.Settings;

import java.util.HashMap;

/**
 * Class where every object interacts with each other.
 */
public class Game extends State3D {

    private Engine engine;
    private Ball ball;
    public Stage hud;
    private Stage pause;
    public boolean paused = false;
    public Slider directionInput;
    public Slider intensityInput;
    private float radius;
    private float marginRadius;
    private StateManager manager;

    public HashMap<String, Label> labels;

    private Settings settings;
    private Course course;
    private int hole_number;

    private Player player;

    /**
     * Constructor for the Game class.
     *
     * @param manager Instance of the GameManager which is currently used.
     * @param course Instance of the Course class which was selected by the user in the menus.
     */
    public Game(StateManager manager, Course course, Player player) {
        super(manager, course.getTerrain(0));

        this.course = course;
        hole_number = 0;

        this.player = player;
        player.setState(this);
        marginRadius = .6f;
        radius = terrain.getHOLE_DIAM() / 2 + marginRadius;
        this.manager = manager;
    }

    /**
     * Method responsible for creating the menu, this is a part of the requirements of libGDX.
     */
    @Override
    public void create() {
        super.create();

        ball = new Ball(terrain.getStart());
        instances.add(ball.getModel());
        ball.updateInstance(terrain.getFunction().evaluateF(ball.getPosition().x, ball.getPosition().y));

        settings = Settings.load();
        engine = new Engine(terrain, ball, settings.getSolver());
        createHUD();

        Gdx.input.setInputProcessor(new InputMultiplexer(hud, player, controller));
    }

    //Method which creates the HUD for the game.
    private void createHUD() {
        hud = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //creation of labels
        labels = new HashMap<>();
        labels.put("score", new Label("Score", StateManager.skin));
        labels.put("par", new Label("Par: ", StateManager.skin));
        labels.put("title", new Label("Hole " + terrain.getName(), StateManager.skin, "title"));
        labels.put("focus", new Label("", StateManager.skin));
        labels.put("distance", new Label("Distance to hole:", StateManager.skin));
        labels.put("speed", new Label("Speed:", StateManager.skin));

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
        Label directionText = new Label("Direction: ", StateManager.skin);
        directionGroup.addActor(directionText);
        directionInput = new Slider(-180f, 180f, 1, false, StateManager.skin);
        directionInput.setValue(0);
        directionGroup.addActor(directionInput);
        inputGroup.addActor(directionGroup);

        //intensity field
        HorizontalGroup intensityGroup = new HorizontalGroup();
        Label intensityText = new Label("Intensity: ", StateManager.skin);
        intensityGroup.addActor(intensityText);
        intensityInput = new Slider(1f, 20f, 1, false, StateManager.skin);
        intensityGroup.addActor(intensityInput);
        inputGroup.addActor(intensityGroup);

        //hit button
        TextButton hitButton = new TextButton("Hit", StateManager.skin);
        hitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double dir = directionInput.getValue();
                double intensity = intensityInput.getValue();

                ball.hit(new Vector3((float) (Math.cos(Math.toRadians(dir)) * intensity), (float) (Math.sin(Math.toRadians(dir)) * intensity), 0));
            }
        });
        inputGroup.addActor(hitButton);
        table.add(inputGroup).center().bottom().pad(10f);

        Table ballInfo = new Table();
        ballInfo.add(labels.get("distance")).fillX().pad(10f);
        ballInfo.row();

        ballInfo.add(labels.get("speed")).expandX().fillX().pad(10f);

        table.add(ballInfo).bottom().right().pad(10f);

        hud.addActor(table);
    }

    //Method which creates the pause state to push to the gameManager to temporarily pause the game.
    private void createPause() {
        pause = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Table organizer = new Table();
        organizer.setFillParent(true);

        Label pauseText = new Label("Paused", StateManager.skin, "title");
        organizer.add(pauseText).center().pad(20f);

        TextButton settings = new TextButton("Settings", StateManager.skin);
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        organizer.row();
        organizer.add(settings).top().pad(10f);

        TextButton menu = new TextButton("MainMenu", StateManager.skin);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });
        organizer.row();
        organizer.add(menu).top().pad(10f);

        TextButton resume = new TextButton("Resume", StateManager.skin);
        resume.addListener(new ClickListener() {
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
        if (paused) {
            pause.getBatch().begin();
            pause.getBatch().draw(new Texture("Textures/grey_background.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

        if (!ball.isStopped()) {
            engine.updateBall(dt);
            ball.updateInstance(terrain.getFunction().evaluateF(ball.getPosition().x, ball.getPosition().y));
        } else {
            player.handleInput(this);
        }
        if(isGoal()) endState();
        updateLabels();
    }

    private void updateLabels(){
        labels.get("score").setText("Score: "+String.valueOf(player.getHitCount()));
        if (controller.isFocused()) labels.get("focus").setText("Ball focus ON");
        else labels.get("focus").setText("");
        String dist = "Distance to hole: " + String.valueOf(ball.getPosition().dst(terrain.getHole()) / 10) + "m";
        labels.get("distance").setText(dist);
        labels.get("speed").setText("Speed: " + String.valueOf(ball.getVelocity().len()/10) + "m/s");
    }

    public boolean isGoal() {
        Vector3 pos = ball.getPosition();
        boolean goal = false;
        if ((pos.dst(terrain.getHole()) < radius)) {
            if (ball.isStopped())
                goal = true;
        }
        return goal;
    }

    //Setting inputProcessor that processes the key-events and stuff like that.
    public void setProcessors() {
        Gdx.input.setInputProcessor(new InputMultiplexer(hud, this, controller));
    }

    public Ball getBall() {
        return ball;
    }

    public StateManager getStateManager(){
        return this.manager;
    }

    private void endState(){

    }
}
