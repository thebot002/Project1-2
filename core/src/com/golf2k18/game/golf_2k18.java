package com.golf2k18.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.GameStates.GameStateManager;

public class golf_2k18 extends ApplicationAdapter 
{
	private SpriteBatch batch;
	private GameStateManager gsm;

	public static final String title = "Golf2k18";
	@Override
	public void create () 
	{
		gsm = new GameStateManager();
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose () 
	{
		batch.dispose();
	}
}
