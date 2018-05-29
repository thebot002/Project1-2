package com.golf2k18.states.menu;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.function.Formula;
import com.golf2k18.objects.Terrain;
import com.golf2k18.objects.CourseIO;
import com.golf2k18.StateManager;

import javax.swing.*;

/**
 * Class that describes the user interface for the TerrainCreator menu.
 */
public class TerrainCreatorMenu extends SubMenu {

    private final String TITLE = "TERRAIN CREATOR";

    //to have start point and Goal labels take the same amount of space
    private final float widthLabel = 100f;
    private final float widthField = 30f;

    private boolean debug = false;
    private Table content;

    /**
     * Constructor for the TerrainCreatorMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    TerrainCreatorMenu(StateManager manager) {
        super(manager);
        content = createContent();
    }

    //Creating every element of the TerrainCreatorMenu UI and then spacing them to look nice.
    private Table createContent() {
        Table table = new Table();

        //formula TextField
        Table formulaGroup = new Table();
        Label fx = new Label("Formula:",StateManager.skin);
        formulaGroup.add(fx).pad(10f).prefWidth(widthLabel);

        TextField fxInput = new TextField("",StateManager.skin);
        formulaGroup.add(fxInput).pad(10f).expandX().fillX();

        table.add(formulaGroup).expandX().fillX();
        table.row();

        //Size field
        Table sizeGroup = new Table();

        Label sizeLabel = new Label("Size*:",StateManager.skin);
        sizeGroup.add(sizeLabel).left().pad(10f).prefWidth(widthLabel);

        Label wLabel = new Label("Width",StateManager.skin);
        sizeGroup.add(wLabel).pad(10f);
        final TextField wField = new TextField("",StateManager.skin);
        sizeGroup.add(wField).pad(10f).padRight(30f).width(widthField);

        Label dLabel = new Label("Depth",StateManager.skin);
        sizeGroup.add(dLabel).pad(10f);
        final TextField dField = new TextField("",StateManager.skin);
        sizeGroup.add(dField).pad(10f).width(widthField).expandX().left();

        table.add(sizeGroup).fillX();
        table.row();

        //Offset field
        Table offSetGroup = new Table();

        Label offSetLabel = new Label("OffSet:",StateManager.skin);
        offSetGroup.add(offSetLabel).pad(10f).prefWidth(widthLabel);

        Label xOffLabel = new Label("X",StateManager.skin);
        offSetGroup.add(xOffLabel).pad(10f);
        final TextField xOffField = new TextField("",StateManager.skin);
        offSetGroup.add(xOffField).pad(10f).padRight(30f).width(widthField);

        Label yOffLabel = new Label("Y",StateManager.skin);
        offSetGroup.add(yOffLabel).pad(10f);
        final TextField yOffField = new TextField("",StateManager.skin);
        offSetGroup.add(yOffField).pad(10f).padRight(30f).width(widthField);

        Label zOffLabel = new Label("Z",StateManager.skin);
        offSetGroup.add(zOffLabel).pad(10f);
        final TextField zOffField = new TextField("",StateManager.skin);
        offSetGroup.add(zOffField).pad(10f).width(widthField).expandX().left();

        table.add(offSetGroup).fillX();
        table.row();

        //scale field
        Table scaleGroup = new Table();

        Label scaleLabel = new Label("Scale:",StateManager.skin);
        scaleGroup.add(scaleLabel).pad(10f).prefWidth(widthLabel);

        final TextField scaleField = new TextField("1",StateManager.skin);
        scaleGroup.add(scaleField).pad(10f).width(widthField).expandX().left();
        table.add(scaleGroup).fillX();
        table.row();

        //Startpoint field
        Table startGroup = new Table();

        Label startLabel = new Label("Startpoint*:",StateManager.skin);
        startGroup.add(startLabel).pad(10f).prefWidth(widthLabel);

        Label xLabel = new Label("X",StateManager.skin);
        startGroup.add(xLabel).pad(10f);
        final TextField xStart = new TextField("",StateManager.skin);
        startGroup.add(xStart).pad(10f).padRight(30f).width(widthField);

        Label yLabel = new Label("Y",StateManager.skin);
        startGroup.add(yLabel).pad(10f);
        final TextField yStart = new TextField("",StateManager.skin);
        startGroup.add(yStart).pad(10f).width(widthField).expandX().left();
        table.add(startGroup).fillX();
        table.row();

        //Endpoint field
        Table endGroup = new Table();
        Label endLabel = new Label("Goal*:",StateManager.skin);
        endGroup.add(endLabel).pad(10f).prefWidth(widthLabel);

        Label xEndLabel = new Label("X",StateManager.skin);
        endGroup.add(xEndLabel).pad(10f);
        final TextField xEnd = new TextField("",StateManager.skin);
        endGroup.add(xEnd).pad(10f).padRight(30f).width(widthField);

        Label yEndLabel = new Label("Y",StateManager.skin);
        endGroup.add(yEndLabel).pad(10f);
        final TextField yEnd = new TextField("",StateManager.skin);
        endGroup.add(yEnd).pad(10f).width(widthField).expandX().left();
        table.add(endGroup).fillX();
        table.row();

        //Name field
        Table nameGroup = new Table();
        Label nameLabel = new Label("Name*:",StateManager.skin);
        nameGroup.add(nameLabel).pad(10f).prefWidth(widthLabel);

        final TextField nameField = new TextField("", StateManager.skin);
        nameGroup.add(nameField).expandX().fillX().pad(10f).left();

        table.add(nameGroup).fillX();
        table.row();

        //Buttons
        TextButton create = new TextButton("Create terrain",StateManager.skin);
        table.add(create).center().fillX().pad(10f).padBottom(50f);
        create.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try{
                    String[] formula = {"y","sin"};
                    String widthS = wField.getText();
                    String heightS = dField.getText();
                    String xOff = xOffField.getText();
                    String yOff = yOffField.getText();
                    String zOff = zOffField.getText();
                    String scale = scaleField.getText();
                    String startX = xStart.getText();
                    String startY = yStart.getText();
                    String endX = xEnd.getText();
                    String endY = yEnd.getText();
                    String name = nameField.getText();
                    Terrain newC = null;
                    if(widthS.length() == 0 || heightS.length() == 0 || startX.length() == 0 || startY.length() == 0 || endX.length() == 0 || endY.length() == 0 || name.length() == 0){
                        JOptionPane.showMessageDialog(null,"Please fill in all the fields marked by a \"*\"","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        int width = Integer.parseInt(widthS);
                        int height = Integer.parseInt(heightS);
                        Vector3 start = new Vector3(Float.parseFloat(startX),Float.parseFloat(startY),0);
                        Vector3 goal = new Vector3(Float.parseFloat(endX),Float.parseFloat(endY),0);
                        if(start.x > width || start.y > height || start.x < 0 || start.y <0){
                            JOptionPane.showMessageDialog(null,"The starting point isn't on the field.\nPlease choose another point.","Error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if(goal.x > width || goal.y > height || goal.x <0 || goal.y <0){
                            JOptionPane.showMessageDialog(null,"The goal point isn't on the field.\nPlease choose another point.","Error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        newC = new Terrain(width, height,start,goal,new Formula(formula),name);
                        if(xOff.length() != 0 || yOff.length() != 0 || zOff.length() != 0){
                            newC.setOffset(new Vector3(xOff.length()!=0?Float.parseFloat(xOff):0,
                                    yOff.length()!=0?Float.parseFloat(yOff):0,
                                    zOff.length()!=0?Float.parseFloat(zOff):0));
                        }
                        if(scale.length() != 0){
                            float fScale = Float.parseFloat(scale);
                            if(fScale != 1.0f){
                                newC.setScale(fScale);
                            }
                        }
                        CourseIO.writeFile(newC);
                        manager.pop().pop();
                    }
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Please fill in with appropriate values","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        if(debug){
            formulaGroup.debug();
            sizeGroup.debug();
            offSetGroup.debug();
            scaleGroup.debug();
            nameGroup.debug();
            endGroup.debug();
            startGroup.debug();
        }

        return table;
    }

    @Override
    public Table getContent() {
        return content;
    }

    //Method that return the String that hold the title input for this terrain.
    @Override
    protected String getTitle() {
        return TITLE;
    }
}
