package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class IOMenu extends GameState
{
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private Array<String> saves;
    public IOMenu(GameStateManager gsm, SpriteBatch batch)
    {
        super(gsm);
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/flat-earth/skin/flat-earth-ui.json"));
        this.batch = batch;
        createStage();
        saves = new Array<String>();
        this.gsm = gsm;
    }

    private void createStage()
    {
        stage = new Stage(new ScreenViewport(),batch);
        Table table = new Table();
        table.setFillParent(true);

        //Return button
        TextButton returnBtn = new TextButton("Return", skin);
        table.add(returnBtn).expand().right().bottom().pad(20f);
        returnBtn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        stage.addActor(table);
    }

    @Override
    protected void handleInput()
    {

    }

    @Override
    public void update(float dt)
    {

    }

    @Override
    public void render(SpriteBatch sb)
    {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose()
    {
        skin.dispose();
        stage.dispose();
        batch.dispose();
    }
    public void addCourse(String path)
    {
        saves.add(path);
    }
    public Array<String> getCourses()
    {
        return this.saves;
    }
}
