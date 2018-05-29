package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.objects.CourseIO;
import com.golf2k18.objects.Terrain;
import com.golf2k18.StateManager;
import com.golf2k18.states.editor.TerrainEditor;

/**
 * Class that describes the user interface for the Editor Menu.
 */
public class EditorMenu extends SubMenu {

    private final String TITLE = "EDITOR MENU";
    private Table content;

    /**
     * Constructor for the EditorMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
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
        //"Create terrain from function" button
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

        //"Create custom terrain" button
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
        //"Create terrain" button
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
