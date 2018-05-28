package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.objects.CourseIO;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.StateManager;
import com.golf2k18.states.editor.TerrainEditor;

public class EditorMenu extends SubMenu {

    private final String TITLE = "EDITOR MENU";
    private Table content;

    EditorMenu(StateManager manager) {
        super(manager);
        content = createContent();
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }

    private Table createContent() {
        Table table = new Table();
        TextButton fnctTerrain = new TextButton("Create terrain from function",StateManager.skin);
        fnctTerrain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
                manager.push(new TerrainCreatorMenu(manager));
            }
        });
        table.add(fnctTerrain).pad(10f).fillX().expandX();
        table.row();

        TextButton splineTerrain = new TextButton("Create custom terrain",StateManager.skin);
        splineTerrain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
                Terrain defaultTerrain = CourseIO.getCourse("Plane");
                defaultTerrain.toSpline(1);
                manager.push(new TerrainEditor(manager,defaultTerrain));
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

    @Override
    public Table getContent() {
        return content;
    }
}
