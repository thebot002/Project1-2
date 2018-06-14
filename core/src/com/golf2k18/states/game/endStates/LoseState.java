package com.golf2k18.states.game.endStates;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.StateManager;
import com.golf2k18.states.game.Game;

/**
 * Desccribes the state of the game if the user loses, offering him the option to retry
 */
public class LoseState extends EndState{
    private final static String TITLE = "YOU LOST...";
    private Table content;

    public LoseState(StateManager manager, Game game) {
        super(manager,game);
    }

    @Override
    protected void createContent() {
        content = new Table();

        Label explanation = new Label("You got 17 hits so the hole is considered to be done.",StateManager.skin);
        content.add(explanation).expandX().fillX().left().colspan(2);
        content.row();

        //RetryButton Button
        Button giveUp = new TextButton("Give up (Back to Start)", StateManager.skin, "default");
        giveUp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.home();
            }
        });
        content.add(giveUp).fillX().expandX().pad(10f).colspan(2);
        content.row();

        //RetryButton Button
        Button retry = new TextButton("Restart terrain", StateManager.skin, "default");
        retry.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
                game.restart();
            }
        });
        content.add(retry).fillX().pad(10f);

        //returnButton Button
        Button next = new TextButton("Next terrain", StateManager.skin, "default");
        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
                game.nextTerrain();
            }
        });
        content.add(next).fillX().pad(10f);
        content.row();
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
