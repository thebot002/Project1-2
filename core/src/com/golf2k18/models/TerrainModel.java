package com.golf2k18.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.objects.Terrain;

import java.util.ArrayList;

/**
 * Class that includes all the information that is needed for the terrain of the course.
 */
public class TerrainModel {

    public ArrayList<ModelInstance> world;

    private final int DIV_SIZE = 10;
    private final int CHUNK_SIZE = 5;
    private float max = 1f;
    private Terrain terrain;

    public Array<HeightField> map;

    /**
     * Constructor for the TerrainModel class, it takes a Terrain object and creates the "world".
     * (This world is represented as an arrayList of ModelInstances)
     * @param terrain Object of the Terrain class.
     */
    public TerrainModel(Terrain terrain){
        this.terrain = terrain;
        world = new ArrayList<>();
        map = new Array<HeightField>();
        world = new ArrayList<ModelInstance>();
        createWorld();
    }

    /**
     * Method which creates all the models that make up the "world" and joins them in one group.
     */
    private void createWorld(){
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        for (int i = 0; i < terrain.getWidth(); i+=CHUNK_SIZE) {
            for (int j = 0; j < terrain.getHeight(); j+=CHUNK_SIZE) {
                HeightField field = new HeightField(true,createHeights(i,j,CHUNK_SIZE,CHUNK_SIZE),(CHUNK_SIZE*DIV_SIZE)+1,(CHUNK_SIZE*DIV_SIZE)+1,true,attr);
                field.corner00.set(i, j, 0);
                field.corner01.set(i+CHUNK_SIZE, j, 0);
                field.corner10.set(i, j+CHUNK_SIZE, 0);
                field.corner11.set(i+CHUNK_SIZE,j+CHUNK_SIZE,0);
                field.color00.set(0, 0, 1, 1);
                field.color01.set(0, 1, 1, 1);
                field.color10.set(1, 0, 1, 1);
                field.color11.set(1, 1, 1, 1);
                field.magnitude.set(0,0,1f);
                field.update();
                map.add(field);
            }
        }

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
        world.add(new ModelInstance(border_d,terrain.getWidth() + (width_border/2),terrain.getHeight()/2,(-height_border/2)+max));
    }

    /**
     * Method which calculates the height of the course according to the given function.
     * @return float[] heights.
     */
   private float[] createHeights(int x0,int y0, int width, int height){
        float[] heights = new float[((width*DIV_SIZE)+1)*((height*DIV_SIZE)+1)]; //width and height +1 because we want to include the edge
        int ih = 0;
        float division = 1/(DIV_SIZE*1.0f);
        float hole_rad = terrain.getHOLE_DIAM()/2;
        final float hole_depth = 3;
        for (float i = 0; i <= width ; i+=division) {
            for (float j = 0; j <= height ; j+=division) {
                if(terrain.getHole().dst(new Vector3(x0+i,y0+j,0)) <= hole_rad){
                    heights[ih] = terrain.getFunction().evaluateF(x0+i ,y0+j)-hole_depth;
                }
                else heights[ih] =  terrain.getFunction().evaluateF(x0+i ,y0+j);
                ih++;
            }
        }
        return heights;
    }
}
