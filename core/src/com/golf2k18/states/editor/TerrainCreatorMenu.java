package com.golf2k18.states.editor;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.function.Formula;
import com.golf2k18.io.DataIO;
import com.golf2k18.objects.Terrain;
import com.golf2k18.StateManager;
import com.golf2k18.states.SubMenu;

import javax.swing.*;
import java.util.HashMap;

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

    private HashMap<String,TextField> fields;
    private boolean edit;

    /**
     * Constructor for the TerrainCreatorMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    public TerrainCreatorMenu(StateManager manager, boolean edit) {
        super(manager);
        this.edit = edit;
    }

    //Creating every element of the TerrainCreatorMenu UI and then spacing them to look nice.
    protected void createContent() {
        content = new Table();
        Terrain terrain = null;
        SelectBox<String> terrain_selection = null;

        if(edit){
            Array<String> terrains = DataIO.getTerrainNames();
            Array<String> formulaTerrains = new Array<>();
            terrain_selection = new SelectBox<>(StateManager.skin);
            for (String s: terrains) {
                Terrain t = DataIO.getTerrain(s);
                if(t.getFunction() instanceof Formula) formulaTerrains.add(s);
            }
            terrain_selection.setItems(formulaTerrains);
            SelectBox<String> finalTerrain_selection = terrain_selection;
            terrain_selection.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Terrain nT = DataIO.getTerrain(finalTerrain_selection.getSelected());
                    fields.get("formula").setText(nT.getFunction().toString());
                    fields.get("width").setText(String.valueOf(nT.getWidth()));
                    fields.get("depth").setText(String.valueOf(nT.getHeight()));
                    fields.get("xOff").setText(String.valueOf(nT.getOffset().x));
                    fields.get("yOff").setText(String.valueOf(nT.getOffset().y));
                    fields.get("zOff").setText(String.valueOf(nT.getOffset().z));
                    fields.get("scale").setText(String.valueOf(nT.getScale()));
                    fields.get("xStart").setText(String.valueOf(nT.getStart().x));
                    fields.get("yStart").setText(String.valueOf(nT.getStart().y));
                    fields.get("xGoal").setText(String.valueOf(nT.getHole().x));
                    fields.get("yGoal").setText(String.valueOf(nT.getHole().y));
                    fields.get("name").setText(nT.getName());
                }
            });
            content.add(terrain_selection).fillX();
            content.row();
            terrain = DataIO.getTerrain(terrain_selection.getSelected());
        }

        fields = new HashMap<>();
        fields.put("formula",new TextField(edit?terrain.getFunction().toString():"",StateManager.skin));
        fields.put("width",new TextField(edit?String.valueOf(terrain.getWidth()):"",StateManager.skin));
        fields.put("depth",new TextField(edit?String.valueOf(terrain.getHeight()):"",StateManager.skin));
        fields.put("xOff",new TextField(edit?String.valueOf(terrain.getOffset().x):"",StateManager.skin));
        fields.put("yOff",new TextField(edit?String.valueOf(terrain.getOffset().y):"",StateManager.skin));
        fields.put("zOff",new TextField(edit?String.valueOf(terrain.getOffset().z):"",StateManager.skin));
        fields.put("scale",new TextField(edit?String.valueOf(terrain.getScale()):"",StateManager.skin));
        fields.put("xStart",new TextField(edit?String.valueOf(terrain.getStart().x):"",StateManager.skin));
        fields.put("yStart",new TextField(edit?String.valueOf(terrain.getStart().y):"",StateManager.skin));
        fields.put("xGoal",new TextField(edit?String.valueOf(terrain.getHole().x):"",StateManager.skin));
        fields.put("yGoal",new TextField(edit?String.valueOf(terrain.getHole().y):"",StateManager.skin));
        fields.put("name",new TextField(edit?terrain.getName():"",StateManager.skin));

        //formula TextField
        Table formulaGroup = new Table();
        Label fx = new Label("Formula:",StateManager.skin);
        formulaGroup.add(fx).pad(10f).prefWidth(widthLabel);
        formulaGroup.add(fields.get("formula")).pad(10f).expandX().fillX();

        content.add(formulaGroup).expandX().fillX();
        content.row();

        //Size field
        Table sizeGroup = new Table();

        Label sizeLabel = new Label("Size*:",StateManager.skin);
        sizeGroup.add(sizeLabel).left().pad(10f).prefWidth(widthLabel);

        Label wLabel = new Label("Width",StateManager.skin);
        sizeGroup.add(wLabel).pad(10f);
        sizeGroup.add(fields.get("width")).pad(10f).padRight(30f).width(widthField);

        Label dLabel = new Label("Depth",StateManager.skin);
        sizeGroup.add(dLabel).pad(10f);
        sizeGroup.add(fields.get("depth")).pad(10f).width(widthField).expandX().left();

        content.add(sizeGroup).fillX();
        content.row();

        //Offset field
        Table offSetGroup = new Table();

        Label offSetLabel = new Label("OffSet:",StateManager.skin);
        offSetGroup.add(offSetLabel).pad(10f).prefWidth(widthLabel);

        Label xOffLabel = new Label("X",StateManager.skin);
        offSetGroup.add(xOffLabel).pad(10f);
        offSetGroup.add(fields.get("xOff")).pad(10f).padRight(30f).width(widthField);

        Label yOffLabel = new Label("Y",StateManager.skin);
        offSetGroup.add(yOffLabel).pad(10f);
        offSetGroup.add(fields.get("yOff")).pad(10f).padRight(30f).width(widthField);

        Label zOffLabel = new Label("Z",StateManager.skin);
        offSetGroup.add(zOffLabel).pad(10f);
        offSetGroup.add(fields.get("zOff")).pad(10f).width(widthField).expandX().left();

        content.add(offSetGroup).fillX();
        content.row();

        //scale field
        Table scaleGroup = new Table();

        Label scaleLabel = new Label("Scale:",StateManager.skin);
        scaleGroup.add(scaleLabel).pad(10f).prefWidth(widthLabel);

        scaleGroup.add(fields.get("scale")).pad(10f).width(widthField).expandX().left();
        content.add(scaleGroup).fillX();
        content.row();

        //Startpoint field
        Table startGroup = new Table();

        Label startLabel = new Label("Startpoint*:",StateManager.skin);
        startGroup.add(startLabel).pad(10f).prefWidth(widthLabel);

        Label xLabel = new Label("X",StateManager.skin);
        startGroup.add(xLabel).pad(10f);
        startGroup.add(fields.get("xStart")).pad(10f).padRight(30f).width(widthField);

        Label yLabel = new Label("Y",StateManager.skin);
        startGroup.add(yLabel).pad(10f);
        startGroup.add(fields.get("yStart")).pad(10f).width(widthField).expandX().left();
        content.add(startGroup).fillX();
        content.row();

        //Endpoint field
        Table endGroup = new Table();
        Label endLabel = new Label("Goal*:",StateManager.skin);
        endGroup.add(endLabel).pad(10f).prefWidth(widthLabel);

        Label xEndLabel = new Label("X",StateManager.skin);
        endGroup.add(xEndLabel).pad(10f);
        endGroup.add(fields.get("xGoal")).pad(10f).padRight(30f).width(widthField);

        Label yEndLabel = new Label("Y",StateManager.skin);
        endGroup.add(yEndLabel).pad(10f);
        endGroup.add(fields.get("yGoal")).pad(10f).width(widthField).expandX().left();
        content.add(endGroup).fillX();
        content.row();

        //Name field
        Table nameGroup = new Table();
        Label nameLabel = new Label("Name*:",StateManager.skin);
        nameGroup.add(nameLabel).pad(10f).prefWidth(widthLabel);
        nameGroup.add(fields.get("name")).expandX().fillX().pad(10f).left();

        content.add(nameGroup).fillX();
        content.row();

        //Buttons
        TextButton create = new TextButton("Create terrain",StateManager.skin);
        content.add(create).center().fillX().pad(10f).padBottom(50f);
        create.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try{
                    String formula = fields.get("formula").getText();
                    String widthS = fields.get("width").getText();
                    String heightS = fields.get("depth").getText();
                    String xOff = fields.get("xOff").getText();
                    String yOff = fields.get("yOff").getText();
                    String zOff = fields.get("zOff").getText();
                    String scale = fields.get("scale").getText();
                    String startX = fields.get("xStart").getText();
                    String startY = fields.get("yStart").getText();
                    String endX = fields.get("xGoal").getText();
                    String endY = fields.get("yGoal").getText();
                    String name = fields.get("name").getText();
                    Terrain newC;
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
                        DataIO.writeTerrain(newC);
                        manager.home();
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
