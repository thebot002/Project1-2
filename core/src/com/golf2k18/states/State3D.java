package com.golf2k18.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.objects.HeightField;
import com.golf2k18.objects.Terrain;
import com.golf2k18.objects.TerrainModel;
import com.golf2k18.camera.GameCameraController;

/**
 * This abstract class is used to create the 3-dimensional states.
 */
public abstract class State3D extends State {
    protected PerspectiveCamera camera;
    protected GameCameraController controller;
    private ModelBatch batch;
    protected Array<ModelInstance> instances = new Array<>();

    private Color bgColor = new Color(.8f,.8f,.8f,1f);
    private Environment environment;

    protected Terrain terrain;
    private TerrainModel terrainModel;
    private Array<Renderable> fields;

    public State3D(StateManager manager, Terrain terrain) {
        super(manager);
        this.terrain = terrain;
    }

    /**
     * Creates a model batch with the environment's properties and camera properties.
     */
    @Override
    public void create () {
        batch = new ModelBatch();

        //camera setup
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 0f, 10f);
        camera.lookAt(10, 10, 0);
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.update();

        Gdx.input.setInputProcessor(controller = new GameCameraController(camera));

        //environment setup
        DirectionalLight light = new DirectionalLight();
        light.set(.8f,.8f,.8f, -1f,-1f,-1f);

        environment = new Environment();
        environment.clear();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.f));
        environment.add(light);


        createTerrain();
        //add the terrain to the list of models to display
        for (ModelInstance m: terrainModel.world) {
            instances.add(m);
        }
    }

    public abstract void pause();
    public abstract void resume();

    public void render (final Array<ModelInstance> instances) {
        batch.begin(camera);
        if (instances != null) batch.render(instances, environment);
        for (Renderable r: fields) {
            batch.render(r);
        }
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

    /**
     * Creates the game's field.
     */
    public void createTerrain(){
        terrainModel = new TerrainModel(terrain);
        Array<HeightField> hf = terrainModel.map;

        fields = new Array<>();
        for (int i = 0; i < hf.size; i++) {
            Renderable field = new Renderable();
            field.environment = environment;
            field.meshPart.mesh = hf.get(i).mesh;
            field.meshPart.primitiveType = GL20.GL_TRIANGLES;
            field.meshPart.offset = 0;
            field.meshPart.size = hf.get(i).mesh.getNumIndices();
            field.meshPart.update();
            field.material = new Material(TextureAttribute.createDiffuse(new Texture("Textures/grass_texture_better.jpg")));
            fields.add(field);
        }
    }

    public abstract void handleInput();
    public void update(float dt){
        camera.update();
        handleInput();
    }
}
