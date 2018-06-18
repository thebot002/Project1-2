package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.StateManager;
import com.golf2k18.io.DataIO;
import com.golf2k18.objects.Course;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.SubMenu;

public class TerrainSelection extends SubMenu {
    private static final String TITLE = "TERRAIN SELECTION";
    private Table content;

    public TerrainSelection(StateManager manager) {
        super(manager);
    }

    @Override
    protected void createContent() {
        content = new Table();

        ButtonGroup<CheckBox> selector = new ButtonGroup<>();
        Array<String> terrain_names = DataIO.getTerrainNames();
        Array<CheckBox> terrain_buttons = new Array<>();
        for (String s: terrain_names) {
            terrain_buttons.add(new CheckBox(s,StateManager.skin));
        }
        Table terrains = new Table();
        for (CheckBox b: terrain_buttons) {
            terrains.add(b).expandX().left().padTop(5f);
            terrains.row();
            selector.add(b);
        }

        ButtonGroup<CheckBox> selector2 = new ButtonGroup<>();
        Array<String> course_names = DataIO.getCourseNames();
        Array<CheckBox> course_buttons = new Array<>();
        for (String s: course_names) {
            course_buttons.add(new CheckBox(s,StateManager.skin));
        }
        Table courses = new Table();
        for (CheckBox b: course_buttons) {
            courses.add(b).expandX().left().padTop(5f);
            courses.row();
            selector2.add(b);
        }

        ScrollPane pane = new ScrollPane(terrains);
        pane.setScrollBarPositions(false,true);
        content.add(pane).expand().fill();

        Table rightCol = new Table();

        SelectBox<String> terrain_course = new SelectBox<>(StateManager.skin);
        terrain_course.setItems("Terrain","Course");
        terrain_course.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(terrain_course.getSelected().equals("Terrain")){
                    pane.setActor(terrains);
                }
                else{
                    pane.setActor(courses);
                }
            }
        });
        rightCol.add(terrain_course);
        rightCol.row();

        TextButton next = new TextButton("Next",StateManager.skin);
        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Course course;

                if(terrain_course.getSelected().equals("Terrain")){
                    Terrain t = DataIO.getTerrain(selector.getChecked().getLabel().getText().toString());
                    course = new Course(t);
                }
                else course = DataIO.getCourse(selector2.getChecked().getLabel().getText().toString());
                manager.push(new ModeSelection(manager,course));
            }
        });
        rightCol.add(next).padTop(30f);

        content.add(rightCol);
    }

    @Override
    protected Table getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }
}
