package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.golf2k18.game.golf_2k18;

public class Game extends GameState
{
    private Texture grass;
    protected Game(GameStateManager gsm)
    {
        super(gsm);
        grass = new Texture("1200px-Grass-JW.jpg");
    }

    protected void create()
    {

    }

    @Override
    protected void handleInput()
    {
        if(Gdx.input.justTouched())
        {
            gsm.pop();
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.begin();
        sb.draw(grass,0,0, golf_2k18.HEIGHT, golf_2k18.WIDTH);
        sb.end();
    }

    @Override
    public void dispose()
    {
        grass.dispose();
    }
}
