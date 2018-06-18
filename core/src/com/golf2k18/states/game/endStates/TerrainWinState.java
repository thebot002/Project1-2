package com.golf2k18.states.game.endStates;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.StateManager;
import com.golf2k18.states.game.Game;

public class TerrainWinState extends EndState {
    private final static String TITLE = "TERRAIN DONE";
    private Table content;

    public TerrainWinState(StateManager manager, Game game) {
        super(manager, game);
    }

    @Override
    protected void createContent() {
        content = new Table();

        TextButton next = new TextButton("Next terrain",StateManager.skin);
        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
                game.nextTerrain();
            }
        });
        content.add(next).expandX().fillX();
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
