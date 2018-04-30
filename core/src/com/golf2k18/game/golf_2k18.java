package com.golf2k18.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.states.*;
import com.golf2k18.states.menu.Menu;

public class golf_2k18 extends ApplicationAdapter {
	private SpriteBatch batch;
	private StateManager gsm;
	public static final int HEIGHT = 800;
	public static final int WIDTH = 1200;
	public static final String TITLE = "Golf2k18";
	public static Music music;
	@Override
	public void create () {
	    gsm = new StateManager();
		batch = new SpriteBatch();
		gsm.push(new Menu(gsm,batch));
        //gsm.push(new Game(gsm,new Engine().getCourse()));
        Gdx.gl.glClearColor(1, 1, 1, 1);
	}
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//music.dispose();
	}
}
