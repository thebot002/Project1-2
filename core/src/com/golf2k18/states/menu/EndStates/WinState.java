package com.golf2k18.states.menu.EndStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.states.MenuState;
import com.golf2k18.states.StateManager;

public class WinState extends MenuState {
    private Stage stage;
    public WinState(StateManager manager) {
        super(manager);
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    @Override
    protected void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()));

        Table table = new Table();
        table.setFillParent(true);

        //WinTitle label
        Label win = new Label("You win!", StateManager.skin, "Title");
        table.add(win).expand().center().top().padTop(50f);
        table.row();

        //TextField text
        TextField textField = new TextField("Enter username for heighscores", StateManager.skin, "default");
        table.add(textField);
        table.row();

        //returnButton Button
        Button enter = new TextButton("Enter", StateManager.skin, "default");
        table.add(enter);
        table.row();

        stage.addActor((table));
    }
}
