package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.game.golf_2k18;
import com.golf2k18.objects.Course;
import com.golf2k18.states.State;
import com.golf2k18.states.StateManager;

public abstract class State3D extends State {
    protected PerspectiveCamera camera;
    protected CameraController controller;
    //protected CameraInputController controller;
    private ModelBatch batch;
    protected Array<ModelInstance> instances = new Array<>();

    private Color bgColor = new Color(.8f,.8f,.8f,1f);
    private Environment environment;

    private Course course;

    State3D(StateManager manager, Course course) {
        super(manager);
        this.course = course;
    }

    @Override
    public void create () {
        batch = new ModelBatch();

        //camera setup
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 30f);
        camera.lookAt(10, 10, 0);
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.update();

        Gdx.input.setInputProcessor(controller = new CameraController(camera));

        //environment setup
        DirectionalLight light = new DirectionalLight();
        light.set(.8f,.8f,.8f, -1f,-1f,-1f);

        environment = new Environment();
        environment.clear();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.f));
        environment.add(light);

        //add the terrain to the list of models to display
        instances.add(course.world);
    }

    public abstract void pause();
    public abstract void resume();

    public void render (final Array<ModelInstance> instances) {
        batch.begin(camera);
        if (instances != null) batch.render(instances, environment);
        batch.end();
    }

    @Override
    public void render() {
        controller.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);

        update(Gdx.graphics.getDeltaTime());
        render(instances);
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

    public abstract void handleInput();
    public void update(float dt){
        camera.update();
        handleInput();
    }
}
