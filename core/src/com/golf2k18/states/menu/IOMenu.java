package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.states.MenuState;
import com.golf2k18.StateManager;

/**
 * Class that describes the user interface for importing a course from a file.
 */
public class IOMenu extends MenuState {
    private Stage stage;
    private Array<String> saves;
    /**
     * Constructor for the IOMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    public IOMenu(StateManager manager) {
        super(manager);
    }

    @Override
    public void create() {
        saves = new Array<String>();
        //temp
        saves.add("Under");
        saves.add("Construction");
        //temp
        super.create();
    }

    protected void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()));
        Table table = new Table();
        table.setFillParent(true);
        //Title
        Label ioMenu = new Label("IO-MENU", StateManager.skin, "title");
        table.add(ioMenu).center().pad(100f);
        table.row();

        //ImportLabel
        Label importLabel = new Label("Enter path for import file:", StateManager.skin);
        table.add(importLabel).center().padTop(10f);
        table.row();

        //ImportTextField
        TextField path = new TextField("C:", StateManager.skin);
        table.add(path).center().fillX().pad(10f);
        table.row();

        //ImportButton
        TextButton importBtn = new TextButton("Import", StateManager.skin);
        table.add(importBtn).center().pad(10f);
        table.row();

        //Export table
        List<String> list = new List<String>(StateManager.skin);
        list.setItems(saves);
        table.add(list).center().fillX().pad(10f);
        table.row();

        //ExportButton
        TextButton exportBtn = new TextButton("Export", StateManager.skin);
        table.add(exportBtn).center().pad(10f);
        table.row();

        //Return button
        TextButton returnBtn = new TextButton("Return", StateManager.skin);
        table.add(returnBtn).expand().right().bottom().pad(20f);
        returnBtn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.pop();
            }
        });

        stage.addActor(table);

        exportBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String[] str = {"0.2", "y", "*", "0.1", "x", "*", "+", "0.03", "x", "2", "^", "*", "+"};
                /*Terrain terrain = new Terrain(100,100,str);
                IO.exportCourse(terrain,"New");*/
            }
        });
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    public void addCourse(String path)
    {
        saves.add(path);
    }
    public Array<String> getCourses()
    {
        return this.saves;
    }
}
