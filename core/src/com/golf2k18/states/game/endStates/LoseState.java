package com.golf2k18.states.game.endStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.StateManager;
import com.golf2k18.states.MenuState;

/**
 * Desccribes the state of the game if the user loses, offering him the option to retry
 */
public class LoseState extends MenuState {
    private Stage stage;
    public LoseState(StateManager manager) {
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
        Label win = new Label("You lost!", StateManager.skin, "Title");
        table.add(win).expand().center().top().padTop(50f);
        table.row();

        //RetryButton Button
        Button retry = new TextButton("Retry", StateManager.skin, "default");
        table.add(retry);
        table.row();

        //returnButton Button
        Button enter = new TextButton("Enter", StateManager.skin, "default");
        table.add(enter);
        table.row();

        stage.addActor((table));
    }
}
