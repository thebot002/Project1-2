package com.golf2k18.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.golf2k18.states.game.Game;

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
