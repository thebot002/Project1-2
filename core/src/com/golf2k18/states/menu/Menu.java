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
import com.golf2k18.objects.Engine;
import com.golf2k18.states.StateManager;
import com.golf2k18.states.game.Game;


public class Menu extends MenuState
{
    private Texture backgroundMenu;
    private Label filePath;
    private TextField path;
    private Stage stage;
    public static boolean mute;

    public Menu(StateManager manager) {
        super(manager);
        backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
        StateManager.music = Gdx.audio.newMusic(Gdx.files.internal("Music/Wii Sports - Wii Sports Theme.mp3"));
        StateManager.music.play();
        StateManager.music.setLooping(true);
        StateManager.music.setVolume(0.5f);
        mute = true;
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
        table.add(label).expand().center().top().padTop(100f);
        table.row();

        //Label input-type
        Label inputType = new Label("Select input source:", StateManager.skin);
        table.add(inputType).center().pad(10f);
        table.row();

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
        table.add(inputSource).center().fillX().pad(10f);
        table.row();

        //Label filePath
        filePath = new Label("Path:", StateManager.skin);
        table.add(filePath).center().fillX().pad(10f);
        filePath.setVisible(false);
        table.row();

        //TextField path
        path = new TextField("C:/", StateManager.skin);
        table.add(path).center().fillX().pad(10f);
        path.setVisible(false);
        table.row();

        //Label playerChoice
        Label playerChoice = new Label("Select mode:", StateManager.skin);
        table.add(playerChoice).center().pad(10f);
        table.row();

        //DropDown PlayerMenu
        SelectBox<String> dropDown = new SelectBox<String>(StateManager.skin);
        Array<String> input = new Array<String>();
        input.add("Single player");
        input.add("Multiplayer");
        input.add("Bot");
        dropDown.setItems(input);
        table.add(dropDown).center().fillX().pad(10f);
        table.row();

        //First textbuton
        TextButton importBtn = new TextButton("Import/Export", StateManager.skin, "default");
        importBtn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new IOMenu(manager));
            }
        });
        table.add(importBtn).center().fillX().pad(10f);
        table.row();

        //Third textButton
        TextButton createCourse = new TextButton("Create new course", StateManager.skin, "default");
        createCourse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new CreatorMenu(manager));
            }
        });
        table.add(createCourse).center().fillX().pad(10f);
        table.row();

        //Second TextButton
        Button button = new TextButton("Start", StateManager.skin, "default");
        button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                manager.push(new Game(manager,new Engine().getCourse()));
            }
        });
        table.add(button).center().fillX().pad(10f);
        table.row();

        //MuteButton
        TextButton mute = new TextButton("Mute/unmute music", StateManager.skin);
        mute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Menu.mute){
                    StateManager.music.setVolume(0f);
                    Menu.mute = true;
                }
                else
                {
                    StateManager.music.setVolume(.5f);
                    Menu.mute = false;
                }
            }
        });
        table.add(mute).bottom().center().fillX().pad(10f).padBottom(200f);
        stage.addActor(table);
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
        backgroundMenu.dispose();
        super.dispose();
    }
}
