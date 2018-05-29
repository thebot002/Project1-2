package com.golf2k18.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 *This is a super-class for every class that can be pushed onto the stack.
 */
public abstract class State extends InputAdapter implements ApplicationListener {
	protected Vector3 cursor;
	protected StateManager manager;

	protected State(StateManager manager){
		this.manager = manager;
		cursor = new Vector3();
	}

	@Override
	public abstract void create();
	public abstract void render();
    public abstract void resize(int width, int height);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
}
