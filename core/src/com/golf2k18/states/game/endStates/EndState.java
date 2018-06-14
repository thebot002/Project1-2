package com.golf2k18.states.game.endStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.StateManager;
import com.golf2k18.states.MenuState;
import com.golf2k18.states.game.Game;

public abstract class EndState extends MenuState {
    private Stage stage;

    private boolean debug = false;
    protected Game game;

    public EndState(StateManager manager, Game game) {
        super(manager);
        this.game = game;
    }

    @Override
    protected void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Table table = new Table();
        if(debug) table.debug();
        table.setFillParent(true);

        //title field
        Label title = new Label(getTitle(),StateManager.skin,"title");
        table.add(title).center().top().pad(10f).padTop(100f).padBottom(100f).expandX();
        table.row();

        //content field
        createContent();
        table.add(getContent()).width(Gdx.graphics.getWidth()/2).expandX();

        stage.addActor(table);
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    protected abstract void createContent();

    protected abstract Table getContent();

    protected abstract String getTitle();
}
