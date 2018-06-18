package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.StateManager;
import com.golf2k18.io.DataIO;
import com.golf2k18.io.Settings;
import com.golf2k18.states.SubMenu;

import javax.swing.*;
import java.util.ArrayList;

public class SettingsMenu extends SubMenu {

    private final String TITLE = "SETTINGS";
    private Table content;

    private Settings settings;

    private boolean change = false;

    public SettingsMenu(StateManager manager) {
        super(manager);
        settings = Settings.load();
    }

    protected void createContent() {
        content = new Table();

        Label volumeLabel = new Label("Volume",StateManager.skin);
        content.add(volumeLabel);

        Slider volume = new Slider(0,1,0.1f,false,StateManager.skin);
        content.add(volume).fillX();
        content.row();

        Label solversLabel = new Label("Solver",StateManager.skin);
        content.add(solversLabel);

        ButtonGroup<CheckBox> solvers = new ButtonGroup<>();
        ArrayList<String> solver_array = settings.getSolvers();
        boolean first = true;
        for (String s: solver_array) {
            CheckBox c = new CheckBox(s,StateManager.skin);
            if(first) first = false;
            else content.add();
            content.add(c).pad(10f).left();
            content.row();
            solvers.add(c);
        }
        solvers.setChecked(solver_array.get(settings.getSelectedSolver()));

        TextButton apply = new TextButton("Apply",StateManager.skin);
        apply.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DataIO.writeSettings(settings);
                StateManager.music.setVolume(volume.getValue());
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
