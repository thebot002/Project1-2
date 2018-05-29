package com.golf2k18.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Goal;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;

public class AI2 extends Bot {
    public Vector3 holeInOne(Game game){
        Vector3 addVectors = new Vector3();
        Ball ball = game.getBall();
        Terrain terrain = game.getTerrain();
        Vector3 ballOrigin = ball.getPosition();

        while(!game.isHit(ball,true))
        {
            Vector3 tryBall = fitness(terrain,ball);
            addVectors = addVectors.add(tryBall);
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
            AI2 bot = new AI2();
            Vector3 holeInOne = bot.holeInOne(game);
            Ball ball = game.getBall();
            ball.hit(holeInOne);
        }
    }
}