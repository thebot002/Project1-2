package com.golf2k18.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.golf2k18.states.game.Game;

/**
 * This abstract class is a super-class for the 2 different kinds of players that can play the game, a human player or a bot.
 */
public abstract class Player extends InputAdapter {
    Game game;

    public void handleInput(Game game){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(!game.paused) game.pause();
            else game.resume();
        }
    }

    public void setState(Game game){
        this.game = game;
    }
}
