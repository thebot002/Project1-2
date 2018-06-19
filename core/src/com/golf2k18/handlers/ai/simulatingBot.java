package com.golf2k18.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.StateManager;
import com.golf2k18.engine.Engine;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.states.game.Game;

public class simulatingBot extends Bot {
    private Ball simuBall;
    private Engine engine;

    private Vector3 velocity;

    private final float SCALAR = 1.01f;

    private Stage running;
    private Label running_label;

    private int ballCounter = 0;

    public simulatingBot() {
        this.simuBall = new Ball(new Vector3());
        this.velocity = new Vector3();
        createStage();
    }

    private void createStage(){
        running = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Table table = new Table();
        table.setFillParent(true);

        running_label = new Label("", StateManager.skin);
        table.add(running_label).expand().center().bottom().pad(10f);

        running.addActor(table);
    }


    @Override
    public void handleInput(Game gameState) {
        if(simuBall.isStopped()){
            System.out.println(simuBall.getPosition());
            running_label.setText("Simulating..."+String.valueOf(ballCounter));
            if(engine != null && engine.isGoal()){
                running_label.setText("");
                gameState.getBall().hit(velocity);
                hitCount++;
                return;
            }
            simuBall = new Ball(gameState.getBall().getPosition().cpy());
            engine = new Engine(gameState.getTerrain(),simuBall, StateManager.settings.getSolver());
            ballCounter++;
            if(velocity.len() == 0){
                velocity = gameState.getTerrain().getHole().cpy().sub(gameState.getBall().getPosition());
            }
            else {
                velocity.scl(SCALAR);
            }
            simuBall.hit(velocity);
        }
        else {
            engine.updateBall(Gdx.graphics.getDeltaTime());
        }
        super.handleInput(gameState);
    }

    @Override
    public void renderInput() {
        running.act();
        running.draw();
        super.renderInput();
    }
}
