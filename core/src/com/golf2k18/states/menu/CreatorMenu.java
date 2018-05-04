package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.states.StateManager;

public class CreatorMenu extends MenuState
{
    private Stage stage;

    CreatorMenu(StateManager manager) {
        super(manager);
    }

    @Override
    protected void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()));
        Table table = new Table();
        table.setFillParent(true);

        //label title
        Label label = new Label("CREATORMENU", StateManager.skin, "title");
        table.add(label).expand().center().top().padTop(10f);
        table.row();

        //FricLabel
        Label fricLabel = new Label("Enter friction coefficient:", StateManager.skin);
        table.add(fricLabel).pad(10f);
        table.row();

        //FricTextField
        TextField fric = new TextField("", StateManager.skin);
        table.add(fric).fillX().pad(10f);
        table.row();

        //MaxSpeed
        Label vMaxLabel = new Label("Enter Vmax:", StateManager.skin);
        table.add(vMaxLabel).pad(10f);
        table.row();

        //VmaxTextField
        TextField vMax = new TextField("", StateManager.skin);
        table.add(vMax).fillX().pad(10f);
        table.row();

        //startLabel
        Label startLabel = new Label("Startpoint:",StateManager.skin);
        table.add(startLabel).pad(10f);
        table.row();

        //Startpoint
        TextField xStart = new TextField("X",StateManager.skin);
        table.add(xStart).fillX().pad(10f);
        table.row();
        TextField yStart = new TextField("Y",StateManager.skin);
        table.add(yStart).fillX().pad(10f);
        table.row();

        //endLabel
        Label endLabel = new Label("Endpoint:", StateManager.skin);
        table.add(endLabel).pad(10f);
        table.row();

        //Endpoint
        TextField xEnd = new TextField("X",StateManager.skin);
        table.add(xEnd).fillX().pad(10f);
        table.row();
        TextField yEnd = new TextField("Y",StateManager.skin);
        table.add(yEnd).fillX().pad(10f);
        table.row();

        //LabelTolerance
        Label tolerance = new Label("Tolerance:",StateManager.skin);
        table.add(tolerance).pad(10f);
        table.row();

        //TextField Tolerance
        TextField tol = new TextField("", StateManager.skin);
        table.add(tol).fillX().pad(10F);
        table.row();

        //Label formula
        Label fx = new Label("Enter formula:",StateManager.skin);
        table.add(fx).pad(10f);
        table.row();

        //TextField formula
        TextField fxInput = new TextField("",StateManager.skin);
        table.add(fxInput).bottom().center().fillX().pad(10f);
        table.row();

        //Buttons
        TextButton create = new TextButton("Create course",StateManager.skin);
        table.add(create).center().fillX().pad(10f);
        table.row();
        TextButton back = new TextButton("Return",StateManager.skin);
        table.add(back).center().fillX().pad(10f).padBottom(20f);
        table.row();

        stage.addActor(table);

        //Listeners
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });
    }

    @Override
    protected Stage getStage() {
        return stage;
    }
}
