package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.game.golf_2k18;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Engine;

public class Game extends GameState
{
    private Texture grass;
    private OrthographicCamera camera;
    private Texture ball;
    private Sprite course;

    protected Game(GameStateManager gsm)
    {
        super(gsm);
    }

    @Override
    public void create()
    {
        perspective.setToOrtho(false,golf_2k18.WIDTH,golf_2k18.HEIGHT);
        grass = new Texture("1200px-Grass-JW.jpg");
        Ball b = new Ball();
        ball = b.getTexture();
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
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(perspective.combined);

        sb.begin();
        sb.draw(grass,0,0, golf_2k18.HEIGHT, golf_2k18.WIDTH);
        sb.draw(ball,50,50);
        sb.end();
        Engine vroom = new Engine();
    }

    @Override
    public void dispose()
    {
        grass.dispose();
    }
}
