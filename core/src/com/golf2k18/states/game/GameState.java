package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;

public abstract class GameState extends State {

    private AssetManager assets;

    private PerspectiveCamera camera;
    private CameraInputController controller;
    private ModelBatch batch;
    private Array<ModelInstance> instances = new Array<ModelInstance>();


    private Color bgColor = new Color(.8f,.8f,.8f,1f);

    public GameState(StateManager manager) {
        super(manager);
        camera = new PerspectiveCamera();
    }

    @Override
    public void create () {
        if (assets == null) assets = new AssetManager();

        batch = new ModelBatch();

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 10f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.update();

        Gdx.input.setInputProcessor(controller = new CameraInputController(camera));
    }


    public abstract void pause();
    public abstract void resume();

    public abstract void render (final ModelBatch batch, final Array<ModelInstance> instances);

    public void render (final Array<ModelInstance> instances) {
        batch.begin(camera);
        if (instances != null) render(batch, instances);
        batch.end();
    }

    @Override
    public void render() {
        update(1);
        controller.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);

        render(instances);
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

    public abstract void handleInput();
    public abstract void update(float dt);

}
