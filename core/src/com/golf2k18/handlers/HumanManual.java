package com.golf2k18.handlers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.StateManager;
import com.golf2k18.states.game.Game;

/**
 * Defines the properties the different games commands if it is being played by a human.
 */
public class HumanManual extends Human {
    private Stage inputStage;

    private Slider directionInput;
    private Slider intensityInput;

    public HumanManual() {
        super();
        createStage();
    }

    private void createStage(){
        inputStage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Table table = new Table();
        table.setFillParent(true);

        VerticalGroup inputGroup = new VerticalGroup();

        //direction field
        HorizontalGroup directionGroup = new HorizontalGroup();
        Label directionText = new Label("Direction: ", StateManager.skin);
        directionGroup.addActor(directionText);
        directionInput = new Slider(-180f, 180f, 1, false, StateManager.skin);
        directionInput.setValue(0);
        directionGroup.addActor(directionInput);
        inputGroup.addActor(directionGroup);

        //intensity field
        HorizontalGroup intensityGroup = new HorizontalGroup();
        Label intensityText = new Label("Intensity: ", StateManager.skin);
        intensityGroup.addActor(intensityText);
        intensityInput = new Slider(1f, 20f, 1, false, StateManager.skin);
        intensityGroup.addActor(intensityInput);
        inputGroup.addActor(intensityGroup);

        //hit button
        TextButton hitButton = new TextButton("Hit", StateManager.skin);
        hitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double dir = directionInput.getValue();
                double intensity = intensityInput.getValue();

                gameState.getBall().hit(new Vector3((float) (Math.cos(Math.toRadians(dir)) * intensity), (float) (Math.sin(Math.toRadians(dir)) * intensity), 0));
                hitCount++;
            }
        });
        inputGroup.addActor(hitButton);
        table.add(inputGroup).expand().center().bottom().pad(10f);

        inputStage.addActor(table);
    }

    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            double dir = directionInput.getValue();
            double intensity = intensityInput.getValue();
            game.getBall().hit(new Vector3((float)(Math.cos(Math.toRadians(dir))*intensity) , (float)(Math.sin(Math.toRadians(dir))*intensity) , 0));
            hitCount++;
        }
    }

    @Override
    public void renderInput() {
        inputStage.act();
        inputStage.draw();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return new InputMultiplexer(super.getInputProcessor(),inputStage);
    }
}
