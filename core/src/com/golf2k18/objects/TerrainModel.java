package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

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
                terrain.getWidth(),0,0,
                terrain.getWidth(),terrain.getHeight(),0,
                0, terrain.getHeight(),0,
                0,0,1,
                new Material(TextureAttribute.createDiffuse(new Texture("Textures/water.png"))),attr);
        world.add(new ModelInstance(water,0,0,0));

        float height_border = 20f;
        float width_border = 0.5f;

        Texture wood = new Texture("Textures/wood_texture.jpg");
        Model border_w = modelBuilder.createBox(terrain.getWidth() + (2 * width_border), width_border,height_border,
                new Material(TextureAttribute.createDiffuse(wood)), attr);
        world.add(new ModelInstance(border_w,terrain.getWidth()/2,-width_border/2,(-height_border/2)+max));
        world.add(new ModelInstance(border_w,terrain.getWidth()/2,terrain.getHeight()+width_border/2,(-height_border/2)+max));

        Model border_d = modelBuilder.createBox(width_border, terrain.getHeight(),height_border,
                new Material(TextureAttribute.createDiffuse(wood)), attr);
        world.add(new ModelInstance(border_d,-(width_border/2),terrain.getHeight()/2,(-height_border/2)+max));
        world.add(new ModelInstance(border_d,terrain.getHeight() + (width_border/2),terrain.getHeight()/2,(-height_border/2)+max));
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
