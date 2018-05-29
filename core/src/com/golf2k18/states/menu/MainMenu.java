package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.golf2k18.handlers.Bot;
import com.golf2k18.handlers.Human;
import com.golf2k18.objects.CourseIO;
import com.golf2k18.states.MenuState;
import com.golf2k18.StateManager;
import com.golf2k18.states.game.Game;

import javax.swing.*;

/**
 * Class that describes the user interface for the main menu, including the skin and the music.
 */
public class MainMenu extends MenuState
{
    private Label filePath;
    private TextField path;
    private SelectBox<String> courseList;
    private Stage stage;
    public static boolean mute;
    /**
     * Constructor for the MainMenu class.
     * @param manager object of the GameStateManager which is currently used.
     */
    public MainMenu(StateManager manager) {
        super(manager);
        createMusic(false);
    }

    private void createMusic(boolean activated){
        StateManager.music = Gdx.audio.newMusic(Gdx.files.internal("Music/Wii Sports - Wii Sports Theme.mp3"));
        StateManager.music.play();
        StateManager.music.setLooping(true);
        StateManager.music.setVolume(activated?0.5f:0f);
        mute = activated;
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

        //label title
        Label label = new Label("GOLF2K18", StateManager.skin, "title");
        table.add(label).expand().center().top().padTop(100f).colspan(3);
        table.row();

        //Label input-type
        Label inputType = new Label("Select input source:", StateManager.skin);
        table.add(inputType).center().pad(10f);
        //table.row();

        //Drowpdown inputSource
        final SelectBox<String> inputSource = new SelectBox<String>(StateManager.skin);
        Array<String> inputSources = new Array<String>();
        inputSources.add("UserInput");
        inputSources.add("Read from file");
        inputSource.setItems(inputSources);
        inputSource.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(inputSource.getSelected() == "Read from file")
                {
                    setVisPath(true);
                }
                else
                {
                    setVisPath(false);
                }
            }
        });
        table.add(inputSource).center().fillX().pad(10f).colspan(2);
        table.row();

        //Label filePath
        filePath = new Label("Path:", StateManager.skin);
        table.add(filePath).center().pad(10f);
        filePath.setVisible(false);
        //table.row();

        //TextField path
        path = new TextField("C:/", StateManager.skin);
        table.add(path).center().fillX().pad(10f).colspan(2);
        path.setVisible(false);
        table.row();

        //Label playerChoice
        Label playerChoice = new Label("Select mode:", StateManager.skin);
        table.add(playerChoice).center().pad(10f);
        //table.row();

        //DropDown PlayerMenu
        SelectBox<String> dropDown = new SelectBox<String>(StateManager.skin);
        Array<String> input = new Array<String>();
        input.add("Single player");
        input.add("Multiplayer");
        input.add("Bot");
        dropDown.setItems(input);
        table.add(dropDown).center().fillX().pad(10f).colspan(2);
        table.row();

        //First textbuton
        TextButton importBtn = new TextButton("Import/Export", StateManager.skin, "default");
        importBtn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new IOMenu(manager));
            }
        });
        table.add(importBtn).center().fillX().pad(10f).colspan(3);
        table.row();

        //Third textButton
        TextButton createCourse = new TextButton("Edit course", StateManager.skin, "default");
        createCourse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new EditorMenu(manager));
            }
        });
        table.add(createCourse).center().fillX().pad(10f).colspan(3);
        table.row();

        Label course = new Label("Terrain",StateManager.skin,"default");
        table.add(course).center().pad(10f);

        courseList = new SelectBox<String>(StateManager.skin);
        table.add(courseList).center().fillX().pad(10f);

        //Second TextButton
        Button start = new TextButton("Start", StateManager.skin, "default");
        start.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String playerState = dropDown.getSelected();
                if(playerState.equals("Single player")){
                    manager.push(new Game(manager,CourseIO.getCourse(courseList.getSelected()),new Human()));
                }
                else if( playerState.equals("Multiplayer")){
                    JOptionPane.showMessageDialog(null,"Bish you cant do that!!!");
                }
                else if(playerState.equals("Bot")){
                    manager.push(new Game(manager,CourseIO.getCourse(courseList.getSelected()),new Bot()));
                }
                //manager.push(new Game(manager,CourseIO.getCourse(courseList.getSelected())));
                //manager.push(new Game(manager,new Euler().getTerrain()));
            }
        });
        table.add(start).center().fillX().pad(10f);
        table.row();

        //Select AI menu
        TextButton selectAI = new TextButton("Choose AI", StateManager.skin, "default");
        selectAI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new AISelectMenu(manager));
            }
        });
        table.add(selectAI).center().fillX().pad(10f);
        table.row();

        //MuteButton
        TextButton mute = new TextButton("Mute/unmute music", StateManager.skin);
        mute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!MainMenu.mute){
                    StateManager.music.setVolume(0f);
                    MainMenu.mute = true;
                }
                else
                {
                    StateManager.music.setVolume(.5f);
                    MainMenu.mute = false;
                }
            }
        });
        table.add(mute).bottom().center().fillX().pad(10f).padBottom(200f).colspan(3);
        stage.addActor(table);
    }

    @Override
    public void render() {
        super.render();
        courseList.setItems(CourseIO.getCoursesNames());
    }

    private void setVisPath(boolean bool) {
        if(bool)
        {
            filePath.setVisible(true);
            path.setVisible(true);
        }
        else
        {
            filePath.setVisible(false);
            path.setVisible(false);
        }
    }

    @Override
    public void dispose() {
       super.dispose();
    }
}
