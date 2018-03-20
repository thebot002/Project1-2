package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.golf2k18.game.golf_2k18;


public class Menu extends GameState
{
    private Button button;
 	private Texture backgroundMenu;
    public Menu(GameStateManager gsm)
	{
		super(gsm);
		backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
		button = new Button(new Button.ButtonStyle());
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
       	sb.begin();
       	sb.draw(backgroundMenu, 0 ,0, golf_2k18.WIDTH, golf_2k18.HEIGHT);
       	sb.end();
	}

    @Override
    public void dispose()
    {
        backgroundMenu.dispose();
    }
  }
