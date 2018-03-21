package com.golf2k18.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class Menu extends GameState
{
    private TextButton button;
    private Skin skin;
    private Texture backgroundMenu;
    private SpriteBatch batch;
    private Stage stage;
    public Menu(GameStateManager gsm, SpriteBatch batch)
	{
		super(gsm);
		backgroundMenu = new Texture("MiniGolf WIndmill.jpg");
		this.batch = batch;
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/flat-earth/skin/flat-earth-ui.json"));
        createStage();
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
        Label inputType = new Label("Select input source", skin);
        table.add(inputType).center().pad(10f);


        //DropDown menu
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
                super.clicked(event, x, y);
            }
        });
        table.add(importBtn).center().fillX().pad(10f);
        table.row();

        //Second TextButton
        button = new TextButton("Start", skin, "default");
        button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gsm.push(new Game(gsm));
            }
        });
        table.add(button).bottom().center().fillX().pad(10f).padBottom(300f);;
        table.row();


        table.debug();
        stage.addActor(table);
    }
}
