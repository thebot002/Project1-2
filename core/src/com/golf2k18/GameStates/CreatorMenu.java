package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CreatorMenu extends GameState
{
private Skin skin;
private Stage stage;
private SpriteBatch batch;

    protected CreatorMenu(GameStateManager gsm, SpriteBatch batch)
    {
        super(gsm, batch);
        this.batch = batch;
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));
        createStage();
    }

    private void createStage()
    {
        stage = new Stage(new ScreenViewport(), batch);
        Table table = new Table();
        table.setFillParent(true);

        //label title
        Label label = new Label("CREATORMENU", skin, "title");
        table.add(label).expand().center().top().padTop(10f);
        table.row();

        //FricLabel
        Label fricLabel = new Label("Enter friction coefficient:", skin);
        table.add(fricLabel).pad(10f);
        table.row();

        //FricTextField
        TextField fric = new TextField("", skin);
        table.add(fric).fillX().pad(10f);
        table.row();

        //MaxSpeed
        Label vMaxLabel = new Label("Enter Vmax:", skin);
        table.add(vMaxLabel).pad(10f);
        table.row();

        //VmaxTextField
        TextField vMax = new TextField("", skin);
        table.add(vMax).fillX().pad(10f);
        table.row();

        //startLabel
        Label startLabel = new Label("Startpoint:",skin);
        table.add(startLabel).pad(10f);
        table.row();

        //Startpoint
        TextField xStart = new TextField("X",skin);
        table.add(xStart).fillX().pad(10f);
        table.row();
        TextField yStart = new TextField("Y",skin);
        table.add(yStart).fillX().pad(10f);
        table.row();

        //endLabel
        Label endLabel = new Label("Endpoint:", skin);
        table.add(endLabel).pad(10f);
        table.row();

        //Endpoint
        TextField xEnd = new TextField("X",skin);
        table.add(xEnd).fillX().pad(10f);
        table.row();
        TextField yEnd = new TextField("Y",skin);
        table.add(yEnd).fillX().pad(10f);
        table.row();

        //LabelTolerance
        Label tolerance = new Label("Tolerance:",skin);
        table.add(tolerance).pad(10f);
        table.row();

        //TextField Tolerance
        TextField tol = new TextField("", skin);
        table.add(tol).fillX().pad(10F);
        table.row();

        //Label formula
        Label fx = new Label("Enter formula:",skin);
        table.add(fx).pad(10f);
        table.row();

        //TextField formula
        TextField fxInput = new TextField("",skin);
        table.add(fxInput).bottom().center().fillX().pad(10f);
        table.row();

        //Buttons
        TextButton create = new TextButton("Create course",skin);
        table.add(create).center().fillX().pad(10f);
        table.row();
        TextButton back = new TextButton("Return",skin);
        table.add(back).center().fillX().pad(10f).padBottom(20f);
        table.row();

//table.debug();
        stage.addActor(table);

        //Listeners
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });


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
        batch.dispose();
        skin.dispose();
    }
}
