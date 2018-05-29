package com.golf2k18.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;

/**
 * This is a class that contains the templates for the menu.
 */
public abstract class MenuState extends State {

    public MenuState(StateManager manager) {
        super(manager);
    }

    @Override
    public void create() {
        createStage();
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Stage stage = getStage();
        if(stage != null){
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            Gdx.input.setInputProcessor(stage);
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        getStage().dispose();
    }

    protected abstract Stage getStage();
    protected abstract void createStage();
}
