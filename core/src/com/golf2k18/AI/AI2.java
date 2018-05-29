package com.golf2k18.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Goal;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;

/**
 * The 2nd AI that can play the game. It tries to find the hole while always hitting the ball from the starting position.
 */
public class AI2 extends Bot {
    public Vector3 holeInOne(Game game){
        System.out.println("hey im here!");
        Vector3 addVectors = new Vector3(1f,2f,0f);
        Ball ball = game.getBall();
        Terrain terrain = game.getTerrain();
        Vector3 ballOrigin = ball.getPosition();

        while(!game.isHit(ball,true))
        {
            System.out.println("loooop");
            Vector3 tryBall = new Vector3(3f,3f,0f);//fitness(terrain,ball);
           // addVectors = addVectors.add(tryBall);
            ball.hit(tryBall);
        }

        ball.setLocation(ballOrigin);
        return addVectors;
    }
    private Vector3 fitness(Terrain terrain, Ball ball){
        Vector3 coordinates = new Vector3();
        Vector3 hole = terrain.getHole();
        coordinates.x = hole.x - ball.getX();
        coordinates.y = hole.y - ball.getY();
        return coordinates;
    }

    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
           Vector3 holeInOne = this.holeInOne(game);
            Ball ball = game.getBall();
            ball.hit(holeInOne);
            System.out.println(holeInOne.toString());
        }
    }
}