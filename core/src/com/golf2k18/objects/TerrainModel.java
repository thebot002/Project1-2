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

    private final float DIV_SIZE = 1f;
    private float max = 1f;
    private Terrain terrain;

    public HeightField field;

    public TerrainModel(Terrain terrain){
        this.terrain = terrain;
        world = new ArrayList<ModelInstance>();

        createWorld();
    }

    private void createWorld(){
        ArrayList<Face> faces = createFaces();

        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        field = new HeightField(true,createHeights(),terrain.getWidth(),terrain.getHeight(),true,attr);
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
        /*modelBuilder.begin();

        for(Face face : faces) {
            modelBuilder.part("face"+face.id(), GL20.GL_TRIANGLES, attr,
                    new Material(ColorAttribute.createDiffuse(0.2f, 0.8f, 0.2f, 1f))) //green
                    .triangle(face.p1(), face.p2(), face.p3());
        }

        world.add(new ModelInstance(modelBuilder.end(), 0, 0, 0));*/

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

    private ArrayList<Face> createFaces(){
        ArrayList<Face> faces = new ArrayList<>(); //face list
        ArrayList<Vector3> vectors = createPoints(); //vector list

        //calculates the amount of x and y coordinates according to the size of the terrain and the division size
        int xAmount = ((int)(terrain.getWidth() / DIV_SIZE))+1;
        int yAmount = ((int)(terrain.getHeight() / DIV_SIZE))+1;

        //loop through the vector array
        for (int i = 0; i < xAmount-1 ; i++) {
            for (int j = 0; j < yAmount-1 ; j++) {
                int pos = (i*yAmount)+j; //converting 2D coordinates to a position in an 1D ArrayList
                faces.add(new Face(vectors.get(pos) , vectors.get(pos+yAmount) , vectors.get(pos+1)));
                faces.add(new Face(vectors.get(pos+yAmount) , vectors.get(pos+yAmount+1) , vectors.get(pos+1)));
            }
        }

        return faces;
    }

    private ArrayList<Vector3> createPoints(){
        ArrayList<Vector3> vectors = new ArrayList<Vector3>(); //vector list
        //creates the vectors of the terrain, terrain centered at 0,0
        for (float i = 0; i <= terrain.getWidth() ; i+=DIV_SIZE) {
            for (float j = 0; j <= terrain.getHeight() ; j+=DIV_SIZE) {
                vectors.add(new Vector3(i,j,(float) terrain.getFunction().evaluateF(i ,j)));
            }
        }
        return vectors;
    }

    private float[] createHeights(){
        float[] heights = new float[terrain.getWidth()*terrain.getHeight()];
        for (int i = 0; i < terrain.getWidth() ; i++) {
            for (int j = 0; j < terrain.getHeight() ; j++) {
                heights[(i*terrain.getHeight()) + j] =  terrain.getFunction().evaluateF(i ,j);
            }
        }
        return heights;
    }


}
