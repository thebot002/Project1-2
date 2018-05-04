package com.golf2k18.states;

import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.golf2k18.states.menu.Menu;

public class StateManager extends ApplicationAdapter {
	private Stack<State> states;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;
    public static Music music;
    public static Skin skin;

    public StateManager() {
		states = new Stack<State>();
	}

	@Override
    public void create () {
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));
        State start = new Menu(this);
        start.create();
        states.push(start);
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }

    public void push(State state)
	{
        //states.peek().dispose();
        state.create();
		states.push(state);
	}
	public void pop()
	{
	    states.peek().dispose();
		states.pop();
	}
	public void set(State state)
	{
	    //states.peek().dispose();
		states.pop();
		state.create();
		states.push(state);
	}

	@Override
	public void render()
	{
        //states.peek().update(1);
        states.peek().render();
	}

	@Override
    public void dispose(){
        skin.dispose();
    }
}
