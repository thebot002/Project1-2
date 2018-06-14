package com.golf2k18.states.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.io.DataIO;
import com.golf2k18.StateManager;
import com.golf2k18.objects.Course;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.SubMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that describes the user interface for the course creator menu.
 */
public class CourseCreatorMenu extends SubMenu {

    private Table content;
    private final String TITLE = "COURSE CREATOR";
    private float widthField = 30f;
    /**
     * Constructor for the CourseCreatorMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    public CourseCreatorMenu(StateManager manager) {
        super(manager);
    }

    protected void createContent() {
        content = new Table();

        Label name = new Label("Name:", StateManager.skin);
        content.add(name).pad(10f);

        TextField nameField = new TextField("",StateManager.skin);
        content.add(nameField).expandX().fillX().pad(10f);
        content.row();

        Label terrainsLabel = new Label("Terrains:\n(Please put a number representing the order starting from 1 next to the terrains you want\nto add to the course)",StateManager.skin);
        content.add(terrainsLabel).colspan(2).pad(10f);
        content.row();

        Table terrainTable = new Table();
        ArrayList<TextField> fields = new ArrayList<>();
        Array<String> terrains = DataIO.getTerrainNames();
        for (String s: terrains) {
            TextField tf = new TextField("",StateManager.skin);
            fields.add(tf);
            terrainTable.add(tf).pad(10f).width(widthField);
            terrainTable.add(new Label(s,StateManager.skin)).expandX().left().pad(10f);
            terrainTable.row();
        }
        ScrollPane pane = new ScrollPane(terrainTable);
        content.add(pane).expand().colspan(2).fillX();
        content.row();

        TextButton create = new TextButton("Create",StateManager.skin);
        create.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try{
                    String name = nameField.getText();
                    if(name.length() > 0){
                        HashMap<Integer,Integer> terrainOrder = new HashMap<>();
                        for (int i = 0; i < fields.size(); i++) {
                            String s = fields.get(i).getText();
                            if(s.length() > 0){
                                terrainOrder.put(Integer.parseInt(s),i);
                            }
                        }
                        if(terrainOrder.isEmpty()) {
                            JOptionPane.showMessageDialog(null,"Please fill in values","Error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        ArrayList<Terrain> finalList = new ArrayList<>();
                        for (int i = 1; i < terrainOrder.size(); i++) {
                            if(!terrainOrder.containsKey(i)) {
                                JOptionPane.showMessageDialog(null,"A value is missing in the order of the terrains","Error",JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            finalList.add(DataIO.getTerrain(terrains.get(terrainOrder.get(i))));
                        }
                        DataIO.writeCourse(new Course(finalList,name));
                        manager.home();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please fill in a name","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Please fill in with appropriate values","Error",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        content.add(create).fillX().colspan(2).pad(10f);
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
