package com.golf2k18.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.StateManager;
import com.golf2k18.engine.Engine;
import com.golf2k18.engine.solver.RK4;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;
import com.sun.istack.internal.NotNull;

//Shoot at the goal with arbitrary velocity vectors that are selected from a cone shape.
public class WIP extends Bot {

    private Ball ball;
    private Terrain terrain;
    private Engine engine;
    private boolean executed = false;


    private Vector3 simulate(@NotNull Game game){
        terrain = game.getTerrain();
        ball = new Ball(terrain.getStart());
        engine = new Engine(terrain, ball, StateManager.settings.getSolver());

        Vector3 tryV = terrain.getHole().sub(terrain.getStart());
        System.out.println(tryV);
        while(!engine.isGoal()){
            ball.setVelocity(tryV);
            if(ball.isStopped()) {
                engine.updateBall(Gdx.graphics.getDeltaTime());
            }
            if(ball.isRolledOver()){
                //tryV.scl(1/game.distanceFromHole());
                //tryV.add((terrain.getHole().add(ball.getPosition().scl(-1))).scl(-1));
                tryV.sub(terrain.getHole().sub(ball.getPosition()));
            }
            else{
               tryV.add(terrain.getHole().sub(ball.getPosition()));
            }

        }
        return tryV;

    }


    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!executed) {
                game.getBall().hit(simulate(game));
                this.executed = true;
            }
        }
    }
}
