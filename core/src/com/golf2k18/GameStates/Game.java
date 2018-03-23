package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.game.golf_2k18;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Engine;

public class Game extends GameState
{
    private Texture grass;
    private Ball ball;

    private Engine vroom;

    protected Game(GameStateManager gsm)
    {
        super(gsm);
    }

    @Override
    public void create()
    {
        perspective.setToOrtho(false,golf_2k18.WIDTH*2,golf_2k18.HEIGHT*2);
        //grass = new Texture("1200px-Grass-JW.jpg");
        ball = new Ball();
        vroom = new Engine();
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
        handleInput();
        if(ball.getVelocity().x != 0.0 || ball.getVelocity().y != 0.0 || ball.getVelocity().z != 0.0) vroom.updateBall(ball);
    }

    @Override
    public void render(SpriteBatch sb)
    {
        Gdx.gl.glClearColor(.58f, .839f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(perspective.combined);

        sb.begin();
        //sb.draw(new Texture(),0,0);
        //sb.draw(grass,0,0, golf_2k18.HEIGHT, golf_2k18.WIDTH);
        sb.draw(ball.getTexture(),(int)(ball.getX()/0.025),(int)(ball.getY()/0.025)); //to be updated
        sb.end();
    }

    @Override
    public void dispose()
    {
        ball.getTexture().dispose();
        //grass.dispose();
    }
}
