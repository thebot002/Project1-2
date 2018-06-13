package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.io.DataIO;
import com.golf2k18.StateManager;

/**
 * Class that describes the user interface for the course creator menu.
 */
public class CourseCreatorMenu extends SubMenu {

    private Table content;
    private final String TITLE = "COURSE CREATOR";
    /**
     * Constructor for the CourseCreatorMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    CourseCreatorMenu(StateManager manager) {
        super(manager);
    }

    protected void createContent() {
        content = new Table();

        List<CheckBox> terrains = new List<CheckBox>(StateManager.skin);
        Array<String> terrain = DataIO.getTerrainNames();
        Array<CheckBox> boxes = new Array<CheckBox>();
        for (String t: terrain) {
            boxes.add(new CheckBox(t,StateManager.skin));
        }
        terrains.setItems(boxes);
        ScrollPane scrollPane = new ScrollPane(terrains);
        scrollPane.setHeight(100f);
        content.add(scrollPane).center().pad(10f).expand();


        /*int areaSize = 10;

        TextArea courseArea = new TextArea("",StateManager.skin);
        courseArea.setPrefRows(areaSize);
        for (int i = 0; i < 20; i++) {
            courseArea.appendText(String.valueOf(i) + "\n");
        }
        table.add(new ScrollPane(courseArea)).pad(10f).fillX();
        table.row();


        TextArea terrainArea = new TextArea("",StateManager.skin);
        table.add(new ScrollPane(terrainArea)).pad(10f).fillX();*/
    }

    @Override
    public Table getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }
}
