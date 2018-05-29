package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.objects.CourseIO;
import com.golf2k18.StateManager;

public class CourseCreatorMenu extends SubMenu {

    private Table content;
    private final String TITLE = "COURSE CREATOR";

    CourseCreatorMenu(StateManager manager) {
        super(manager);
        content = createContent();
    }

    private Table createContent() {
        Table table = new Table();

        List<CheckBox> terrains = new List<CheckBox>(StateManager.skin);
        Array<String> terrain = CourseIO.getCoursesNames();
        Array<CheckBox> boxes = new Array<CheckBox>();
        for (String t: terrain) {
            boxes.add(new CheckBox(t,StateManager.skin));
        }
        terrains.setItems(boxes);
        ScrollPane scrollPane = new ScrollPane(terrains);
        scrollPane.setHeight(100f);
        table.add(scrollPane).center().pad(10f).expand();


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
        return table;
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
