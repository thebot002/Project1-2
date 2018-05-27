package com.golf2k18.states.editor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.State3D;
import com.golf2k18.states.StateManager;

public class TerrainEditor extends State3D {
    public TerrainEditor(StateManager manager, Terrain terrain) {
        super(manager, terrain);
    }

    private final float NODE_DIAM = 0.2f;
    @Override
    public void create() {
        super.create();
        createPoints(1);
    }

    private void createPoints(float interval){
        ModelBuilder builder = new ModelBuilder();
        Texture nodeTexture = new Texture("Textures/grey_background.png");

        Model nodeModel = builder.createSphere(NODE_DIAM, NODE_DIAM, NODE_DIAM,
                50,50,
                new Material(TextureAttribute.createDiffuse(nodeTexture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );

        for (float i = 0; i <=terrain.getWidth() ; i+=interval) {
            for (float j = 0; j < terrain.getHeight() ; j+=interval) {
                instances.add(new ModelInstance(nodeModel,i,j,terrain.getFunction().evaluateF(i,j)));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void handleInput() {

    }
}
