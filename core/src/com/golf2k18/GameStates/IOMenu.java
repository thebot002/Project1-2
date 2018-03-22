package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
        //skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/flat-earth/skin/flat-earth-ui.json"));
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));
        this.batch = batch;
        saves = new Array<String>();
        this.gsm = gsm;
       //temp
        saves.add("Under");
        saves.add("Construction");
        //temp
        createStage();
    }

    private void createStage()
    {
        stage = new Stage(new ScreenViewport(),batch);
        Table table = new Table();
        table.setFillParent(true);
        //Title
        Label ioMenu = new Label("IO-MENU", skin, "title");
        table.add(ioMenu).center().pad(100f);
        table.row();

        //ImportLabel
        Label importLabel = new Label("Enter path for import file:", skin);
        table.add(importLabel).center().padTop(10f);
        table.row();

        //ImportTextField
        TextField path = new TextField("C:", skin);
        table.add(path).center().fillX().pad(10f);
        table.row();

        //ImportButton
        TextButton importBtn = new TextButton("Import", skin);
        table.add(importBtn).center().pad(10f);
        table.row();

        //Export table
        List<String> list = new List<String>(skin);
        list.setItems(saves);
        table.add(list).center().fillX().pad(10f);
        table.row();

        //ExportButton
        TextButton exportBtn = new TextButton("Export", skin);
        table.add(exportBtn).center().pad(10f);
        table.row();

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
