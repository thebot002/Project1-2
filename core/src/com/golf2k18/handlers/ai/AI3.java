package com.golf2k18.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;

//Shoot at the goal with arbitrary velocity vectors that are selected from a cone shape.
public class AI3 extends Bot {

    private Ball ball;
    private Terrain terrain;
    private Engine engine;

    private Vector3 simulate(Game game){

        ball = game.getBall();
        terrain = game.getTerrain();
        engine = game.getEngine();

        Vector3 tryV = terrain.getHole().sub(terrain.getStart());

        while(!game.isGoal()){
            ball.setVelocity(tryV);
            engine.updateBall(Gdx.graphics.getDeltaTime());
            if(ball.isRolledOver()){
                //tryV.scl(1/game.distanceFromHole());
                //tryV.add((terrain.getHole().add(ball.getPosition().scl(-1))).scl(-1));
                tryV.sub(terrain.getHole().sub(ball.getPosition()));
            }
            else{
                tryV.add(terrain.getHole().sub(ball.getPosition()));
            }
        }
        ball.setPosition(terrain.getStart());
        ball.setVelocity(new Vector3());
        return tryV;
    }






    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        ball.hit(simulate(game));
    }
}
