package com.golf2k18.handlers.ai;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;

/**
 * The 2nd ai that can play the game. It tries to find the hole while always hitting the ball from the starting position.
 */
public class AI2 extends Bot {

    private Vector3 fitness(Terrain terrain, Ball ball) {
        Vector3 coordinates = new Vector3();
        Vector3 hole = terrain.getHole();
        coordinates.x = hole.x - ball.getPosition().x -1.8f;
        coordinates.y = hole.y - ball.getPosition().y -1.8f;
        return coordinates;
    }

    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        Terrain terrain = game.getTerrain();
        Ball ball = game.getBall();
        Vector3 tryBall = fitness(terrain, ball);
        // addVectors = addVectors.add(tryBall);
        ball.hit(tryBall);
        hitCount++;
    }
}