package com.golf2k18.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;
import com.golf2k18.states.menu.endStates.WinState;

/**
 * The 2nd ai that can play the game. It tries to find the hole while always hitting the ball from the starting position.
 */
public class AI2 extends Bot {

    private static Vector3 addVectors;
/*
    public AI2() {
        addVectors = new Vector3();
    }

    /*
    public Vector3 holeInOne(Game game) {
        addVectors = new Vector3();
        Ball ball = game.getBall();
        Terrain terrain = game.getTerrain();
        return addVectors;

    }
    */

    private Vector3 fitness(Terrain terrain, Ball ball) {
        Vector3 coordinates = new Vector3();
        Vector3 hole = terrain.getHole();
        coordinates.x = hole.x - ball.getX();
        coordinates.y = hole.y - ball.getY();
        return coordinates;
    }

    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        if(!game.isHit(game.getBall())) {
            Terrain terrain = game.getTerrain();
            Ball ball = game.getBall();
            Vector3 tryBall = fitness(terrain, ball);
            // addVectors = addVectors.add(tryBall);
            ball.hit(tryBall);
        }
        else{
            game.getStateManager().push(new WinState(game.getStateManager()));
        }
        /*
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {

            while (!game.isHit(game.getBall())) {
                Terrain terrain = game.getTerrain();
                Ball ball = game.getBall();
                Vector3 tryBall = fitness(terrain, ball);
                // addVectors = addVectors.add(tryBall);
                ball.hit(tryBall);
            }
            if(game.isHit(game.getBall())){
                game.getStateManager().push(new WinState(game.getStateManager()));

            }
        }
        */
    }
}