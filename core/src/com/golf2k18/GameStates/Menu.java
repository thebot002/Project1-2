package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.game.golf_2k18;

public class Menu extends GameState
{
	private Texture backgroundMenu;
	public Menu(GameStateManager gsm)
	{
		super(gsm);
		perspective.setToOrtho(false, golf_2k18.WIDTH,golf_2k18.HEIGHT);
		backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
	}
	public void handleInput()
	{
		if(Gdx.input.justTouched())
        {
            gsm.push(new Game(gsm));
        }
	}
	public void update(float dt)
	{
		handleInput();
	}
	public void render(SpriteBatch sb)
	{
	    sb.setProjectionMatrix(perspective.combined);
		sb.begin();
		sb.draw(backgroundMenu, 0, 0, golf_2k18.HEIGHT, golf_2k18.WIDTH);
		sb.end();
	}

    @Override
    public void dispose()
    {
        backgroundMenu.dispose();
    }
}
