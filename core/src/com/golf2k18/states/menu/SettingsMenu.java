package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.StateManager;
import com.golf2k18.io.DataIO;
import com.golf2k18.io.Settings;
import com.golf2k18.states.SubMenu;

import javax.swing.*;
import java.util.ArrayList;

public class SettingsMenu extends SubMenu {

    private final String TITLE = "SETTINGS";
    private Table content;

    private boolean change = false;

    public SettingsMenu(StateManager manager) {
        super(manager);
    }

    protected void createContent() {
        content = new Table();

        //volume
        Label volumeLabel = new Label("Volume",StateManager.skin);
        content.add(volumeLabel).pad(10f);

        Slider volume = new Slider(0,1,0.1f,false,StateManager.skin);
        volume.setValue(StateManager.settings.getMusicVolume());
        content.add(volume).fillX().pad(10f);
        content.row();

        //solvers
        Label solversLabel = new Label("Solver",StateManager.skin);
        content.add(solversLabel).pad(10f);

        ArrayList<String> solver_arrayList = StateManager.settings.getSolvers();
        Array<String> solver_array = new Array<>();
        for (String s: solver_arrayList) solver_array.add(s);

        SelectBox<String> solvers = new SelectBox<>(StateManager.skin);
        solvers.setItems(solver_array);
        solvers.setSelectedIndex(StateManager.settings.getSelectedSolver());
        content.add(solvers).fillX().pad(10f);
        content.row();

        //input modes
        content.add(new Label("Input",StateManager.skin)).pad(10f);
        Array<String> inputmodes_array = new Array<>();
        ArrayList<String> inputmodes_arrayList = StateManager.settings.getSources();
        for (String s:inputmodes_arrayList) inputmodes_array.add(s);

        SelectBox<String> inputmodes = new SelectBox<>(StateManager.skin);
        inputmodes.setItems(inputmodes_array);
        inputmodes.setSelectedIndex(StateManager.settings.getSelectedSource());
        content.add(inputmodes).pad(10f).fillX();
        content.row();

        //water shown
        content.add(new Label("Water",StateManager.skin));
        SelectBox<String> water = new SelectBox<>(StateManager.skin);
        Array<String> waterStates = new Array<>();
        waterStates.add("On");
        waterStates.add("Off");
        water.setItems(waterStates);
        water.setSelectedIndex(StateManager.settings.hasWater()?0:1);
        content.add(water).pad(10f).fillX();
        content.row();

        //noise shown
        content.add(new Label("Noise",StateManager.skin));
        SelectBox<String> noise = new SelectBox<>(StateManager.skin);
        Array<String> noiseStates = new Array<>();
        noiseStates.add("On");
        noiseStates.add("Off");
        noise.setItems(noiseStates);
        noise.setSelectedIndex(StateManager.settings.hasNoise()?0:1);
        content.add(noise).pad(10f).fillX();
        content.row();

        //apply button
        TextButton apply = new TextButton("Apply",StateManager.skin);
        apply.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StateManager.settings.setMusicVolume(volume.getValue());
                StateManager.music.setVolume(volume.getValue());
                StateManager.settings.setSelectedSolver(solvers.getSelectedIndex());
                StateManager.settings.setSelectedSource(inputmodes.getSelectedIndex());
                StateManager.settings.setWater(water.getSelectedIndex() == 0);
                StateManager.settings.setNoise(noise.getSelectedIndex() == 0);
                DataIO.writeSettings(StateManager.settings);
                manager.pop();
            }
        });
        content.add(apply).expand().fillX().bottom().colspan(2);

        content.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                change = true;
            }
        });
    }

    @Override
    protected Table getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }

    protected void returnAction(){
        if(change){
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "There are some unsaved changes.\nReturn?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION);
            if(n == 0) super.returnAction();
        }
        else{
            super.returnAction();
        }
    }
}
