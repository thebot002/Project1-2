package com.golf2k18.states.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.golf2k18.game.golf_2k18;
import com.golf2k18.objects.Engine;
import com.golf2k18.states.game.Game3D;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;
import com.golf2k18.states.game.GameState;


public class Menu extends MenuState
{
    private Skin skin;
    private Texture backgroundMenu;
    private Label filePath;
    private TextField path;
    private SpriteBatch batch;
    private Stage stage;
    public static boolean mute;

    public Menu(StateManager manager, SpriteBatch batch) {
        super(manager);
        backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
        this.batch = batch;
        //skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/flat-earth/skin/flat-earth-ui.json"));
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));
        createStage();
        golf_2k18.music = Gdx.audio.newMusic(Gdx.files.internal("Music/Wii Sports - Wii Sports Theme.mp3"));
        golf_2k18.music.play();
        golf_2k18.music.setLooping(true);
        golf_2k18.music.setVolume(0.5f);
        mute = true;
    }

    @Override
    public void create() {

    }

	public void render(SpriteBatch sb)
	{
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
	}

    @Override
    public void dispose()
    {
        backgroundMenu.dispose();
        batch.dispose();
        skin.dispose();
    }
    public void createStage()
    {
        stage = new Stage(new ScreenViewport(), batch);
        Table table = new Table();
        table.setFillParent(true);

        //label title
        Label label = new Label("GOLF2K18", skin, "title");
        table.add(label).expand().center().top().padTop(100f);
        table.row();

        //Label input-type
        Label inputType = new Label("Select input source:", skin);
        table.add(inputType).center().pad(10f);
        table.row();

        //Drowpdown inputSource
        final SelectBox<String> inputSource = new SelectBox<String>(skin);
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
        filePath = new Label("Path:", skin);
        table.add(filePath).center().fillX().pad(10f);
        filePath.setVisible(false);
        table.row();

        //TextField path
        path = new TextField("C:/", skin);
        table.add(path).center().fillX().pad(10f);
        path.setVisible(false);
        table.row();

        //Label playerChoice
        Label playerChoice = new Label("Select mode:", skin);
        table.add(playerChoice).center().pad(10f);
        table.row();

        //DropDown PlayerMenu
        SelectBox<String> dropDown = new SelectBox<String>(skin);
        Array<String> input = new Array<String>();
        input.add("Single player");
        input.add("Multiplayer");
        input.add("Bot");
        dropDown.setItems(input);
        table.add(dropDown).center().fillX().pad(10f);
        table.row();

        //First textbuton
        TextButton importBtn = new TextButton("Import/Export", skin, "default");
        importBtn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new IOMenu(manager,batch));
            }
        });
        table.add(importBtn).center().fillX().pad(10f);
        table.row();

        //Third textButton
        TextButton createCourse = new TextButton("Create new course", skin, "default");
        createCourse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new CreatorMenu(manager,batch));
            }
        });
        table.add(createCourse).center().fillX().pad(10f);
        table.row();

        //Second TextButton
        Button button = new TextButton("Start", skin, "default");
        button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                manager.push(new GameState(manager,new Engine().getCourse()));
            }
        });
        table.add(button).center().fillX().pad(10f);
        table.row();

        //MuteButton
        TextButton mute = new TextButton("Mute/unmute music", skin);
        mute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Menu.mute){
                    golf_2k18.music.setVolume(0f);
                    Menu.mute = true;
                }
                else
                {
                 golf_2k18.music.setVolume(.5f);
                 Menu.mute = false;
                }
            }
        });
        table.add(mute).bottom().center().fillX().pad(10f).padBottom(200f);
       // table.debug();
        stage.addActor(table);
    }
    public void setVisPath(boolean bool)
    {
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
}
