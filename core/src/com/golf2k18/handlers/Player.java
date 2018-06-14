package com.golf2k18.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.golf2k18.states.game.Game;

/**
 * This abstract class is a super-class for the 2 different kinds of players that can play the gameState, a human player or a bot.
 */
public abstract class Player extends InputAdapter {
    protected Game gameState;
    protected int hitCount;

    public Player() {
        hitCount = 0;
    }

    public void handleInput(Game gameState){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(!gameState.paused) gameState.pause();
            else gameState.resume();
        }
    }

    public int getHitCount() {
        return hitCount;
    }

    public void resetCount() {
        this.hitCount = 0;
    }

    public void setState(Game gameState){
        this.gameState = gameState;
    }
}
