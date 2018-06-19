package com.golf2k18.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.golf2k18.states.game.Game;

public abstract class Human extends Player{
    private boolean manualMovement = false;

    /**
     * Manual movements that can be executed by a player, using the keyboard and the mouse.
     * @param game the game that is played
     */
    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            game.getBall().getPosition().x += 0.1;
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            game.getBall().getPosition().x -= 0.1;
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            game.getBall().getPosition().y += 0.1;
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            game.getBall().getPosition().y -= 0.1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            if(manualMovement) game.setProcessors();
            else Gdx.input.setInputProcessor(new InputMultiplexer(game.hud, game));
            manualMovement = !manualMovement;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            if(game.controller.isFocused()){
                game.labels.get("focus").setText("");
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            if(!game.controller.isFocused())game.controller.focus(game.getBall().getPosition());
            else game.controller.unfocus();
        }
    }
}
