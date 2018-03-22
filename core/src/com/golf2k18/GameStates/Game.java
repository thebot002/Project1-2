package com.golf2k18.GameStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.objects.Engine;

public class Game extends GameState
{
    private Texture grass;
    protected Game(GameStateManager gsm)
    {
        super(gsm);
        perspective.setToOrtho(false, 300, 300);
        grass = new Texture("1200px-Grass-JW.jpg");
    }


    @Override
    public void render(SpriteBatch sb)
    {
        Engine vroom = new Engine();
    }

    @Override
    public void dispose()
    {
        grass.dispose();
    }
}
