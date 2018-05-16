package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.states.StateManager;

public class EditorMenu extends SubMenu {

    private final String TITLE = "EDITOR MENU";

    EditorMenu(StateManager manager) {
        super(manager);
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }

    @Override
    protected Table createContent() {
        Table table = new Table();
        TextButton fnctTerrain = new TextButton("Create terrain from function",StateManager.skin);
        fnctTerrain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new TerrainCreatorMenu(manager));
            }
        });
        table.add(fnctTerrain).pad(10f).fillX().expandX();
        table.row();

        TextButton splineTerrain = new TextButton("Create custom terrain",StateManager.skin);
        splineTerrain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("To be implemented...");
                //manager.push(new CourseCreatorMenu(manager));
            }
        });
        table.add(splineTerrain).pad(10f).fillX();
        table.row();

        TextButton courseCreation = new TextButton("Create terrain",StateManager.skin);
        courseCreation.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new CourseCreatorMenu(manager));
            }
        });
        table.add(courseCreation).pad(10f).fillX();

        return table;
    }
}
