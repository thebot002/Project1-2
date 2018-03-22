package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
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



public class Menu extends GameState
{
    private Skin skin;
    private Texture backgroundMenu;
    private Label filePath;
    private TextField path;
    private SpriteBatch batch;
    private Stage stage;
    public Menu(GameStateManager gsm, SpriteBatch batch)
	{
		super(gsm);
		backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
		this.batch = batch;
        //skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/flat-earth/skin/flat-earth-ui.json"));
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));
        createStage();
	}

    @Override
    public void create() {

    }

    public void handleInput()
	{

	}
	public void update(float dt)
	{

	}
	public void render(SpriteBatch sb)
	{
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
                gsm.push(new IOMenu(gsm,batch));
            }
        });
        table.add(importBtn).center().fillX().pad(10f);
        table.row();

        //Third textButton
        TextButton createCourse = new TextButton("Create new course", skin, "default");
        createCourse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new CreatorMenu(gsm,batch));
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
                gsm.push(new Game(gsm));
            }
        });
        table.add(button).bottom().center().fillX().pad(10f).padBottom(200f);;
        table.row();


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
