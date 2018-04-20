package com.golf2k18.states.game.deprecated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.golf2k18.game.golf_2k18;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Engine;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;
import com.golf2k18.states.menu.MenuState;

public class Game extends State
{
    private OrthographicCamera perspective;
    private Texture grass;
    private Ball ball;

    private Engine vroom;
    private SpriteBatch sb;

    protected Game(StateManager gsm)
    {
        super(gsm);
    }

    @Override
    public void create()
    {
        sb = new SpriteBatch();
        perspective = new OrthographicCamera();
        perspective.setToOrtho(false,golf_2k18.WIDTH,golf_2k18.HEIGHT);
        //grass = new Texture("1200px-Grass-JW.jpg");
        ball = new Ball();
        vroom = new Engine();
    }

    @Override
    protected void handleInput()
    {
        if(Gdx.input.justTouched())
        {
            manager.pop();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(!ball.isStopped()) vroom.updateBall(ball);
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(.58f, .839f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(perspective.combined);

        sb.begin();
        //sb.draw(new Texture(),0,0);
        //sb.draw(grass,0,0, golf_2k18.HEIGHT, golf_2k18.WIDTH);
        sb.draw(ball.getTexture(),(int)(ball.getX()/0.025),(int)(ball.getY()/0.025),(int)(30*Math.pow(1.1,ball.getZ())),(int)(30*Math.pow(1.1,ball.getZ()))); //to be updated
        sb.end();
    }

    @Override
    public void dispose()
    {
        ball.getTexture().dispose();
        //grass.dispose();
    }
}
