package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.golf2k18.game.golf_2k18;


public class Menu extends GameState
{
    private Button button;
    private Button.ButtonStyle buttonStyle;
 	private Texture backgroundMenu;
    public Menu(GameStateManager gsm)
	{
		super(gsm);
		backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
		button = new Button(new Button.ButtonStyle());
	}
//	public Stage Create()
//	{
//		buttonStyle = new TextButton.TextButtonStyle();
//		buttonStyle.up = skin.getDrawable("button");
//		buttonStyle.over = skin.getDrawable("buttonpressed");
//		buttonStyle.down = skin.getDrawable("buttonpressed");
//		buttonStyle.font = font;
//		button = new TextButton("START", buttonStyle);
//	}
	public void handleInput()
	{

	}
	public void update(float dt)
	{

	}
	public void render(SpriteBatch sb)
	{
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
