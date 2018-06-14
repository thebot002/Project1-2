
package com.golf2k18.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;
import com.golf2k18.engine.solver.Euler;
import com.golf2k18.engine.solver.Solver;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;


public class AI extends Bot {
    private float time =0;
    private Ball ball;
    private Terrain terrain;


    public Vector3 solvePos(Vector3 position, Vector3 velocity, Engine engine){
        return new Vector3(position.x + engine.getDt()*velocity.x,position.y + engine.getDt()*velocity.y,0);
    }


    public Vector3 solveVel(Vector3 position, Vector3 velocity, Engine engine){
        float aX = -engine.getAcceleration(position,velocity).x;
        float aY = -engine.getAcceleration(position,velocity).y;
        return new Vector3(velocity.x + engine.getDt()*aX, velocity.y + engine.getDt()*aY, 0);
    }
    @Override
    public void handleInput(Game game) {

        super.handleInput(game);
        if(time == 0){
            game.getBall().getPosition().set(game.getTerrain().getHole());
        }
        if(!(game.getBall().getPosition().dst(game.getTerrain().getStart()) < .1)){

            float dt = Gdx.graphics.getDeltaTime();

            Vector3 position = game.getBall().getPosition();
            Vector3 velocity = game.getBall().getVelocity();

            game.getBall().getPosition().set(this.solvePos(position,velocity,game.getEngine()));
            game.getBall().getVelocity().set(this.solveVel(position,velocity,game.getEngine()));

//            Vector3 newVel = watson.solveVel(new Vector3(position),new Vector3(velocity));
//            ball.getVelocity().set(newVel);
//            Vector3 newPos = watson.solvePos(new Vector3(position),new Vector3(velocity));
//            ball.getPosition().set(newPos);

        }
    }
}