package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.golf2k18.states.game.endStates.EndState;
import com.golf2k18.states.game.endStates.LoseState;
import com.golf2k18.states.game.endStates.TerrainWinState;
import com.golf2k18.states.game.endStates.WinState;
import com.golf2k18.states.menu.SettingsMenu;

import java.util.HashMap;

/**
 * Class where every object interacts with each other.
 */
public class Game extends State3D {
    private Engine engine;
    private Ball ball;
    public Stage hud;
    private Stage pause;
    private boolean paused = false;
    private boolean inSettings = false;
    private StateManager manager;

    public HashMap<String, Label> labels;

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
        hole_number = 1;

        ball = new Ball(terrain.getStart().cpy());
        instances.add(ball.getModel());

        this.player = player;
        player.setState(this);
        this.manager = manager;
    }

    /**
     * Method responsible for creating the menu, this is a part of the requirements of libGDX.
     */
    @Override
    public void create() {
        super.create();

        ball.updateInstance(terrain.getFunction().evaluateF(ball.getPosition().x, ball.getPosition().y));
        controller.initFocus(ball.getPosition());

        engine = new Engine(terrain, ball, StateManager.settings.getSolver());
        createHUD();

        setProcessors();
    }

    //Method which creates the HUD for the game.
    private void createHUD() {
        hud = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //creation of labels
        labels = new HashMap<>();
        labels.put("score", new Label("Score: 0", StateManager.skin));
        labels.put("par", new Label("Par: ", StateManager.skin));
        labels.put("title", new Label("Hole " + (course.getSize()>1? "#"+hole_number+":" : "")+ terrain.getName(), StateManager.skin, "title"));
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

        table.add(labels.get("focus")).left().bottom().pad(10f).colspan(2);

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
                manager.push(new SettingsMenu(manager));
                inSettings = true;
            }
        });
        organizer.row();
        organizer.add(settings).top().pad(10f);

        TextButton menu = new TextButton("MainMenu", StateManager.skin);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.home();
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

        TextButton givUp = new TextButton("Give up", StateManager.skin);
        Game g = this;
        givUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(course.getSize() != hole_number){
                    course.setScore(hole_number,17);
                    endState(new LoseState(manager,g));
                }
            }
        });
        organizer.row();
        organizer.add(givUp).top().pad(10f);


        TextButton restart = new TextButton("Restart", StateManager.skin);
        restart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
            }
        });
        organizer.row();
        organizer.add(restart).top().pad(10f);

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
        player.renderInput();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        paused = !paused;
        createPause();
        Gdx.input.setInputProcessor(new InputMultiplexer(pause, this));
    }

    @Override
    public void resume() {
        paused = !paused;
        setProcessors();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(inSettings){
            inSettings = false;
            setProcessors();
        }
        if (paused) return;
        if (!ball.isStopped()) {
            engine.updateBall(dt);
            ball.updateInstance(terrain.getFunction().evaluateF(ball.getPosition().x, ball.getPosition().y));
        } else {
            player.handleInput(this);
        }
        if(engine.isGoal()) {
            if(hole_number == course.getSize()) endState(new WinState(manager,this));
            else endState(new TerrainWinState(manager,this));
        }
        if(player.getHitCount() > 17) endState(new LoseState(manager,this));
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

    //Setting inputProcessor that processes the key-events and stuff like that.
    public void setProcessors() {
        Gdx.input.setInputProcessor(new InputMultiplexer(hud, player.getInputProcessor(), this, controller));
    }

    public Ball getBall() {
        return ball;
    }

    private void endState(EndState state){
        course.setScore(hole_number-1,player.getHitCount());
        this.player.resetCount();
        manager.push(state);
    }

    public Course getCourse() {
        return course;
    }

    public Player getPlayer() {
        return player;
    }

    public void restart(){
        ball.getPosition().set(terrain.getStart().cpy());
        ball.setStopped();
        ball.updateInstance(terrain.getFunction().evaluateF(ball.getPosition().x,ball.getPosition().y));
        player.resetCount();
    }

    public void nextTerrain(){
        hole_number++;
        terrain = course.getTerrain(hole_number-1);
        ball.getPosition().set(terrain.getStart().cpy());
        create();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE){
            if(!paused) pause();
            else resume();
        }
        return super.keyDown(keycode);
    }
}
