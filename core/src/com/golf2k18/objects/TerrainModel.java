package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class TerrainModel {

    public ArrayList<ModelInstance> world;

    private final int DIV_SIZE = 4;
    private float max = 1f;
    private Terrain terrain;

    public HeightField field;

    public TerrainModel(Terrain terrain){
        this.terrain = terrain;
        world = new ArrayList<ModelInstance>();
        createWorld();
    }

    private void createWorld(){
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        field = new HeightField(true,createHeights(),terrain.getWidth()*DIV_SIZE,terrain.getHeight()*DIV_SIZE,true,attr);
        field.corner00.set(0, 0, 0);
        field.corner01.set(terrain.getWidth(), 0, 0);
        field.corner10.set(0, terrain.getHeight(), 0);
        field.corner11.set(terrain.getWidth(), terrain.getHeight(),0);
        field.color00.set(0, 0, 1, 1);
        field.color01.set(0, 1, 1, 1);
        field.color10.set(1, 0, 1, 1);
        field.color11.set(1, 1, 1, 1);
        field.magnitude.set(0,0,1f);
        field.update();

        ModelBuilder modelBuilder = new ModelBuilder();

        Model water = modelBuilder.createRect(0,0,0,
                20,0,0,
                20,20,0,
                0, 20,0,
                0,0,1,
                new Material(TextureAttribute.createDiffuse(new Texture("water.png"))),attr);
        world.add(new ModelInstance(water,0,0,0));

        float height_border = 20;

        Model border_w = modelBuilder.createBox(terrain.getWidth() + (2 * DIV_SIZE), DIV_SIZE,height_border,
                new Material(TextureAttribute.createDiffuse(new Texture("wood_texture.jpg"))), attr);
        world.add(new ModelInstance(border_w,terrain.getWidth()/2,-DIV_SIZE/2,(-height_border/2)+max));
        world.add(new ModelInstance(border_w,terrain.getWidth()/2,terrain.getHeight()+DIV_SIZE/2,(-height_border/2)+max));

        Model border_d = modelBuilder.createBox(DIV_SIZE, terrain.getHeight(),height_border,
                new Material(TextureAttribute.createDiffuse(new Texture("wood_texture.jpg"))), attr);
        world.add(new ModelInstance(border_d,-(DIV_SIZE/2),terrain.getHeight()/2,(-height_border/2)+max));
        world.add(new ModelInstance(border_d,terrain.getHeight() + (DIV_SIZE/2),terrain.getHeight()/2,(-height_border/2)+max));
    }

    private float[] createHeights(){
        float[] heights = new float[terrain.getWidth()*terrain.getHeight()*DIV_SIZE*DIV_SIZE];
        for (float i = 0; i < terrain.getWidth() ; i+=1/(DIV_SIZE*1.0f)) {
            for (float j = 0; j < terrain.getHeight() ; j+=(1/(DIV_SIZE*1.0f))) {
                heights[(int)(((i*terrain.getHeight()*DIV_SIZE) + j)*DIV_SIZE)] =  terrain.getFunction().evaluateF(i ,j);
            }
        }
        return heights;
    }
}
