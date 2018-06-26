package com.golf2k18.states.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.StateManager;
import com.golf2k18.io.DataIO;
import com.golf2k18.states.SubMenu;

public class BasisChoiceMenu extends SubMenu {
    private final String TITLE = "TERRAIN BASIS";
    private Table content;

    public BasisChoiceMenu(StateManager manager) {
        super(manager);
    }

    @Override
    protected void createContent() {
        content = new Table();

        Array<String> terrains = DataIO.getTerrainNames();
        ButtonGroup<TextButton> buttons = new ButtonGroup<>();

        ClickListener choice = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String s = buttons.getChecked().getText().toString();
                manager.push(new TerrainEditor(manager,DataIO.getTerrain(s)));
            }
        };

        Table buttonTable = new Table();
        for (String t: terrains) {
            TextButton b = new TextButton(t,StateManager.skin);
            b.addListener(choice);
            buttons.add(b);
            buttonTable.add(b).pad(10f).expandX().fillX();
            buttonTable.row();
        }

        content.add(new ScrollPane(buttonTable)).expand().fillX();
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
