package com.golf2k18.states.game.endStates;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.StateManager;
import com.golf2k18.states.game.Game;

/**
 * Describes the state of the game if the player wins. If he does, a message "You won!" will be displayed,
 * and the user will be asked to enter his username so the game can save it.
 */
public class WinState extends EndState{
    private static final String TITLE = "YOU WON!";
    private Table content;

    public WinState(StateManager manager, Game game) {
        super(manager, game);
    }

    @Override
    protected void createContent() {
        content = new Table();

        //TextField text
        TextField textField = new TextField("Enter username for highscores", StateManager.skin);
        content.add(textField).fillX().expandX().pad(10f).colspan(2);
        content.row();

        //replay button
        TextButton replay = new TextButton("Replay",StateManager.skin);
        replay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new Game(manager,game.getCourse(),game.getPlayer()));
            }
        });
        content.add(replay).pad(10f).fillX();

        //returnButton Button
        Button enter = new TextButton("Back to Start", StateManager.skin);
        enter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.home();
            }
        });
        content.add(enter).fillX().pad(10f);
    }

    @Override
    protected Table getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }
}
