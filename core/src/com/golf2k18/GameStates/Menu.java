package com.golf2k18.GameStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu extends GameState
{
	private Texture backgroundMenu;
	public Menu(GameStateManager gsm)
	{
		super(gsm);
		backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
	}
	public void handleInput()
	{
		
	}
	public void update(float dt)
	{
		
	}
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(backgroundMenu, 0, 0, golf2k18.HEIGHT, golf2k18.WIDTH);
	}
}
