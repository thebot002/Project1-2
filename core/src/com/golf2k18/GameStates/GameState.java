package com.golf2k18.GameStates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class GameState 
{
	protected OrthographicCamera perspective;
	protected Vector3 cursor;
	protected GameStateManager gsm;
	protected SpriteBatch batch;
	
	protected GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
		perspective = new OrthographicCamera();
		cursor = new Vector3();
	}
	protected GameState(GameStateManager gsm, SpriteBatch batch)
	{
		this.gsm = gsm;
		perspective = new OrthographicCamera();
		cursor = new Vector3();
		this.batch = batch;
	}

	public abstract void create();
	protected abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();
}
