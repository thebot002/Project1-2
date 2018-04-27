package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.game.golf_2k18;
import com.golf2k18.states.StateManager;

public abstract class GameHUD extends GameState {

    private float hudWidth;
    private float hudHeight;

    private Stage hud;
    private Skin skin;

    public GameHUD(StateManager manager) {
        super(manager);
    }

    @Override
    public void create() {
        super.create();

        createHUD();

        Gdx.input.setInputProcessor(new InputMultiplexer(hud, this, controller));
    }

    private void createHUD(){
        hud = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        hudWidth = hud.getWidth();
        hudHeight = hud.getHeight();
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));

        //Image arrow = new Image(new Texture("arrow-975990_960_720.png"));
        //arrow.setPosition(hudWidth-arrow.getImageX(),hudHeight);
        //hud.addActor(arrow);
        /*Label txt = new Label("test",skin);
        txt.setFontScale(9f);
        txt.setVisible(true);
        hud.addActor(txt);*/
    }

    @Override
    public void render() {
        super.render();
        hud.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        hud.act();
        hud.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
