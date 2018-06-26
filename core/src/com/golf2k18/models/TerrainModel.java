package com.golf2k18.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.function.Function;
import com.golf2k18.function.Spline;
import com.golf2k18.objects.Terrain;
import com.golf2k18.objects.Wall;

import java.util.ArrayList;

/**
 * Class that includes all the information that is needed for the terrain of the course.
 */
public class TerrainModel {
    private ArrayList<ModelInstance> field;
    private ArrayList<ModelInstance> edges;
    private ArrayList<ModelInstance> skeleton;
    private ArrayList<ModelInstance> walls;
    private ModelInstance water;

    private final int DIV_SIZE = 10;
    private final int CHUNK_SIZE = 5;
    private float max = 1f;
    private Terrain terrain;

    private int attr;

    public Array<HeightField> map;

    /**
     * Constructor for the TerrainModel class, it takes a Terrain object and creates the "world".
     * (This world is represented as an arrayList of ModelInstances)
     * @param terrain Object of the Terrain class.
     */
    public TerrainModel(Terrain terrain){
        this.terrain = terrain;
        field = new ArrayList<>();
        skeleton = new ArrayList<>();
        edges = new ArrayList<>();
        map = new Array<>();

        attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        createWorld();
    }

    /**
     * Method which creates all the models that make up the "world" and joins them in one group.
     */
    private void createWorld(){
        for (int i = 0; i < terrain.getWidth(); i+=CHUNK_SIZE) {
            for (int j = 0; j < terrain.getHeight(); j+=CHUNK_SIZE) {
                map.add(createField(i,j));
            }
        }
        ModelBuilder modelBuilder = new ModelBuilder();

        Model water = modelBuilder.createRect(0,0,0,
                terrain.getWidth(),0,0,
                terrain.getWidth(),terrain.getHeight(),0,
                0, terrain.getHeight(),0,
                0,0,1,
                new Material(TextureAttribute.createDiffuse(new Texture("Textures/water.png"))),attr);
        this.water = new ModelInstance(water,0,0,0);

        float height_border = 20f;
        float width_border = 0.5f;

        Texture wood = new Texture("Textures/wood_texture.jpg");
        Model border_w = modelBuilder.createBox(terrain.getWidth() + (2 * width_border), width_border,height_border,
                new Material(TextureAttribute.createDiffuse(wood)), attr);
        edges.add(new ModelInstance(border_w,terrain.getWidth()/2,-width_border/2,(-height_border/2)+max));
        edges.add(new ModelInstance(border_w,terrain.getWidth()/2,terrain.getHeight()+width_border/2,(-height_border/2)+max));

        Model border_d = modelBuilder.createBox(width_border, terrain.getHeight(),height_border,
                new Material(TextureAttribute.createDiffuse(wood)), attr);
        edges.add(new ModelInstance(border_d,-(width_border/2),terrain.getHeight()/2,(-height_border/2)+max));
        edges.add(new ModelInstance(border_d,terrain.getWidth() + (width_border/2),terrain.getHeight()/2,(-height_border/2)+max));
    }

    private HeightField createField(int x, int y){
        float[] heights = createHeights(x,y,CHUNK_SIZE,CHUNK_SIZE);
        HeightField field = new HeightField(true,heights,(CHUNK_SIZE*DIV_SIZE)+1,(CHUNK_SIZE*DIV_SIZE)+1,true,attr);
        field.corner00.set(x, y, 0);
        field.corner01.set(x+CHUNK_SIZE, y, 0);
        field.corner10.set(x, y+CHUNK_SIZE, 0);
        field.corner11.set(x+CHUNK_SIZE,y+CHUNK_SIZE,0);
        field.color00.set(0, 0, 1, 1);
        field.color01.set(0, 1, 1, 1);
        field.color10.set(1, 0, 1, 1);
        field.color11.set(1, 1, 1, 1);
        field.magnitude.set(0,0,1f);
        field.update();

        return field;
    }

    /**
     * Method which calculates the height of the course according to the given function.
     * @return float[] heights.
     */
    private float[] createHeights(int x0,int y0, int width, int height){
        float[] heights = new float[((width*DIV_SIZE)+1)*((height*DIV_SIZE)+1)]; //width and height +1 because we want to include the edge
        int ih = 0;
        float hole_rad = terrain.getHOLE_DIAM()/2;
        final float hole_depth = 3;
        for (float i = 0; i <= width*DIV_SIZE ; i++) {
            for (float j = 0; j <= height*DIV_SIZE ; j++) {
                if(terrain.getHole().dst(new Vector3(x0+(i/DIV_SIZE),y0+(j/DIV_SIZE),0)) <= hole_rad){
                    heights[ih] = terrain.getFunction().evaluateF(x0+(i/DIV_SIZE) ,y0+(j/DIV_SIZE))-hole_depth;
                }
                else heights[ih] =  terrain.getFunction().evaluateF(x0+(i/DIV_SIZE) ,y0+(j/DIV_SIZE));
                ih++;
            }
        }
        return heights;
    }

    public ArrayList<ModelInstance> getField() {
        return field;
    }

    public ArrayList<ModelInstance> getEdges() {
        return edges;
    }

    public ArrayList<ModelInstance> generateObstacles(){
        Texture brick = new Texture("Textures/brick_texture.jpg");

        ArrayList<ModelInstance> walls = new ArrayList<>();
        for (Wall w: terrain.getObstacles()) {
            ArrayList<Vector3> points = w.getCore();
            float thickness = w.getThickness();
            Vector3 p0p1 = points.get(1).cpy().sub(points.get(0));
            float angle = (float) Math.acos(p0p1.dot(1,0,0) / p0p1.len());

            Quaternion orientation = new Quaternion(p0p1.cpy().crs(-1,0,0),(float)Math.toDegrees(angle));

            Vector3 position = points.get(0).cpy().add(p0p1.cpy().scl(.5f));

            ModelBuilder builder = new ModelBuilder();
            Model wall = builder.createBox(p0p1.len()+(2*thickness),2*thickness,2*thickness,
                    new Material(TextureAttribute.createDiffuse(brick)),attr);
            walls.add(new ModelInstance(wall, new Matrix4(position,orientation,new Vector3(1,1,1))));

        }
        return walls;
    }

    public ArrayList<ModelInstance> generateSkeleton(){
        ArrayList<ModelInstance> skeletons = new ArrayList<>();

        ArrayList<Function> functions = new ArrayList<>();
        functions.add(terrain.getFunction());
        if(terrain.getFunction() instanceof Spline){
            Spline fnct = (Spline) functions.get(0);
            functions.add(fnct.toBiquintic());
        }

        ModelBuilder modelBuilder = new ModelBuilder();
        MeshPartBuilder builder;

        for (Function f: functions) {
            for (int i = 0; i < terrain.getWidth(); i++) {
                for (int j = 0; j < terrain.getHeight(); j++) {
                    for (float k = 0; k < 1 - (1f/DIV_SIZE); k+=(1f/DIV_SIZE)) {
                        modelBuilder.begin();
                        builder = modelBuilder.part("line", 1, 3, new Material());
                        builder.setColor(f.getSkelColor());
                        builder.line(i+k, j, terrain.getFunction().evaluateF(i+k,j), i+k+(1f/DIV_SIZE), j, f.evaluateF(i+k+(1f/DIV_SIZE),j));
                        skeletons.add(new ModelInstance(modelBuilder.end()));

                        modelBuilder.begin();
                        builder = modelBuilder.part("line", 1, 3, new Material());
                        builder.setColor(f.getSkelColor());
                        builder.line(i, j+k, terrain.getFunction().evaluateF(i,j+k), i, j+k+(1f/DIV_SIZE), f.evaluateF(i,j+k+(1f/DIV_SIZE)));
                        skeletons.add(new ModelInstance(modelBuilder.end()));
                    }
                }
            }
        }

        return skeletons;
    }

    public ModelInstance getWater() {
        return water;
    }
}
