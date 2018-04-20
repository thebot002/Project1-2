package com.golf2k18.states.menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;

public abstract class MenuState extends State {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public MenuState(StateManager manager) {
        super(manager);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
    }

    @Override
    public void render() {
        render(batch);
    }

    public abstract void render(SpriteBatch batch);

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
