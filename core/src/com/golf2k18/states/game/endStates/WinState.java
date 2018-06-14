package com.golf2k18.states.game.endStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.StateManager;
import com.golf2k18.states.MenuState;

/**
 * Describes the state of the game if the player wins. If he does, a message "You won!" will be displayed,
 * and the user will be asked to enter his username so the game can save it.
 */
public class WinState extends MenuState implements EndState{
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
        Label win = new Label("You won!", StateManager.skin, "title");
        table.add(win).expand().center().top().padTop(50f);
        table.row();

        //TextField text
        TextField textField = new TextField("Enter username for highscores", StateManager.skin, "default");
        table.add(textField).fillX().colspan(2);
        table.row();

        //returnButton Button
        Button enter = new TextButton("Enter", StateManager.skin, "default");
        table.add(enter).expand().center().fillX().padBottom(20f).colspan(3);
        table.row();

        enter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
                manager.pop();
            }
        });
        stage.addActor((table));
    }
}
