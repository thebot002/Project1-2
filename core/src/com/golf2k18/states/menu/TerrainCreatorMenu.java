package com.golf2k18.states.menu;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.objects.Terrain;
import com.golf2k18.objects.CourseIO;
import com.golf2k18.states.StateManager;

public class TerrainCreatorMenu extends SubMenu {

    private final String TITLE = "TERRAIN CREATOR";

    //to have startpoint and Goal labels take the same amount of space
    private final float widthLabel = 100f;

    TerrainCreatorMenu(StateManager manager) {
        super(manager);
    }

    @Override
    protected Table createContent() {
        Table table = new Table();

        //Name field
        Table nameGroup = new Table();
        Label nameLabel = new Label("Name:",StateManager.skin);
        nameGroup.add(nameLabel).pad(10f).prefWidth(widthLabel);

        TextField nameField = new TextField("", StateManager.skin);
        nameGroup.add(nameField).expandX().fillX().pad(10f);

        table.add(nameGroup).fillX().pad(10f);
        table.row();

        //Startpoint field
        Table startGroup = new Table();

        Label startLabel = new Label("Startpoint:",StateManager.skin);
        startGroup.add(startLabel).pad(10f).padRight(30f).prefWidth(widthLabel);

        Label xLabel = new Label("X",StateManager.skin);
        startGroup.add(xLabel).pad(10f);
        TextField xStart = new TextField("",StateManager.skin);
        startGroup.add(xStart).fillX().pad(10f).padRight(30f);

        Label yLabel = new Label("Y",StateManager.skin);
        startGroup.add(yLabel).expandX().right().pad(10f);
        TextField yStart = new TextField("",StateManager.skin);
        startGroup.add(yStart).pad(10f);
        table.add(startGroup).fillX();
        table.row();

        //Endpoint field
        Table endGroup = new Table();
        Label endLabel = new Label("Goal:",StateManager.skin);
        endGroup.add(endLabel).pad(10f).padRight(30f).prefWidth(widthLabel);

        Label xEndLabel = new Label("X",StateManager.skin);
        endGroup.add(xEndLabel).pad(10f);
        TextField xEnd = new TextField("",StateManager.skin);
        endGroup.add(xEnd).fillX().pad(10f).padRight(30f);

        Label yEndLabel = new Label("Y",StateManager.skin);
        endGroup.add(yEndLabel).expandX().right().pad(10f);
        TextField yEnd = new TextField("",StateManager.skin);
        endGroup.add(yEnd).pad(10f);
        table.add(endGroup).fillX();
        table.row();

        //formula TextField
        Table formulaGroup = new Table();
        Label fx = new Label("Enter formula:",StateManager.skin);
        formulaGroup.add(fx).pad(10f).prefWidth(widthLabel);

        TextField fxInput = new TextField("",StateManager.skin);
        formulaGroup.add(fxInput).expandX().fillX().pad(10f);

        table.add(formulaGroup).fillX().pad(10f);
        table.row();

        //Buttons
        TextButton create = new TextButton("Create terrain",StateManager.skin);
        table.add(create).center().fillX().pad(10f).padBottom(50f);
        create.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float startX = Float.parseFloat(xStart.getText());
                float startY = Float.parseFloat(yStart.getText());
                float endX = Float.parseFloat(xEnd.getText());
                float endY = Float.parseFloat(yEnd.getText());
                String name = nameField.getText();
                String[] formula = {"x","cos"};
                Terrain newC = new Terrain(20,20, new Vector3(startX,startY,0),new Vector3(endX,endY,0),formula,name);
                CourseIO.writeFile(newC);

            }
        });
        return table;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }
}
