package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.states.MenuState;
import com.golf2k18.states.StateManager;

public abstract class SubMenu extends MenuState {
    private Stage stage;
    private boolean debug = false;

    SubMenu(StateManager manager) {
        super(manager);
    }

    @Override
    protected void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()));
        Table table = new Table();
        if(debug) table.debug();
        table.setFillParent(true);

        //title field
        Label title = new Label(getTitle(),StateManager.skin,"title");
        table.add(title).center().top().pad(10f).padTop(100f).padBottom(100f).expandX();
        table.row();

        //content field
        table.add(createContent()).expandX().fillX();
        table.row();

        //back button
        TextButton back = new TextButton("Return",StateManager.skin);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });
        table.add(back).pad(10f).fillX().expandY().padBottom(100f).bottom();

        stage.addActor(table);
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    protected abstract Table createContent();

    protected abstract String getTitle();
}
