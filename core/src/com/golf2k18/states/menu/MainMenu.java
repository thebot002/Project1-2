package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.io.DataIO;
import com.golf2k18.io.Settings;
import com.golf2k18.states.MenuState;
import com.golf2k18.StateManager;
import com.golf2k18.states.editor.EditorMenu;

/**
 * Class that describes the user interface for the main menu, including the skin and the music.
 */
public class MainMenu extends MenuState
{
    private Stage stage;
    /**
     * Constructor for the MainMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    public MainMenu(StateManager manager) {
        super(manager);
        createMusic();
    }
    //Add music to the menu
    private void createMusic(){
        StateManager.music = Gdx.audio.newMusic(Gdx.files.internal("Music/Wii Sports - Wii Sports Theme.mp3"));
        StateManager.music.play();
        StateManager.music.setLooping(true);
        StateManager.music.setVolume(StateManager.settings.getMusicVolume());
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    @Override
    public void createStage() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()));

        Table table = new Table();
        table.setFillParent(true);

        //Title label
        Label label = new Label("GOLF2K18", StateManager.skin, "title");
        table.add(label).expand().center().top().padTop(100f);
        table.row();

        //edition of the course
        TextButton createCourse = new TextButton("Course edition", StateManager.skin, "default");
        createCourse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new EditorMenu(manager));
            }
        });
        table.add(createCourse).center().fillX().pad(10f);
        table.row();

        //Start
        Button start = new TextButton("Start", StateManager.skin, "default");
        start.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                manager.push(new TerrainSelection(manager));
            }
        });
        table.add(start).center().fillX().pad(10f);
        table.row();

        //settings
        TextButton settingsBtn = new TextButton("Settings", StateManager.skin, "default");
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new SettingsMenu(manager));
            }
        });
        table.add(settingsBtn).center().fillX().pad(10f).padBottom(300f);

        stage.addActor(table);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
       super.dispose();
    }
}
