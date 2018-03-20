package com.golf2k18.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.GameStates.*;

public class golf_2k18 extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;
	public static final int HEIGHT = 800;
	public static final int WIDTH = 600;
	public static final String TITLE = "Golf2k18";
	
	@Override
	public void create () {
	    gsm = new GameStateManager();
		batch = new SpriteBatch();
		gsm.push(new Menu(gsm));
        Gdx.gl.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		}
}
