package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.states.MenuState;
import com.golf2k18.states.StateManager;

import javax.swing.*;

public class AISelectMenu extends MenuState {
    private Stage stage;
    public static String AI;
    public AISelectMenu(StateManager manager) {
        super(manager);
        AI = "";
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    @Override
    public void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()));

        Table table = new Table();
        table.setFillParent(true);

        SelectBox<String> dropDown = new SelectBox<String>(StateManager.skin);
        Array<String> input = new Array<String>();
        input.add("AI1");
        input.add("AI2");
        dropDown.setItems(input);
        dropDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AI = dropDown.getSelected();
                JOptionPane.showMessageDialog(null,AI);
            }
        });
        table.add(dropDown);

        TextButton back = new TextButton("return", StateManager.skin, "default");
        back.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });
        table.add(back);
        stage.addActor(table);
    }

    @Override
    public void render() {
        super.render();
    }
/*
    private void setVisPath(boolean bool) {
        if(bool)
        {
            filePath.setVisible(true);
            path.setVisible(true);
        }
        else
        {
            filePath.setVisible(false);
            path.setVisible(false);
        }
    }
    */

    @Override
    public void dispose() {
       super.dispose();
    }
}
