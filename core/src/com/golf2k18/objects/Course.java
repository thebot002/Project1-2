package com.golf2k18.objects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Random;

public class Course {
    int width;
    int height;
    double[] start = {0,0,0};
    double[] goal = {0,1,0};

    //Vector start = new Vector(0,0,0); // to be implemented
    //Vector goal = new Vector(0.0,1.0,0.0); // to be implemented
    double scale = 1; //function unit to world unit
    //      ^ to be implemented later

    String[] formula;
    double MU = 0.5;
    double tolerance = 0.02;
    double vMax = 3;

    private Function function;
    public ModelInstance world;

    private final double DIV_SIZE = .5;

    public Function getFunction() {
        return function;
    }

    //default constructor
    public Course(int width, int height, String[] formula) {
        this.width = width;
        this.height = height;

        function = new Function(formula);

        createWorld();
    }

    //modelbuilder is slow 1.2 sec for 0.5 div size
    private void createWorld(){
        ArrayList<Face> faces = createFaces();

        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        //Texture myTexture = new Texture("badlogic.jpg");

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        //modelBuilder.manage(myTexture); //make modelbuilder responsible for disposing the texture resource

        Random r = new Random();
        for(Face face : faces) {
            modelBuilder.part("face"+face.id(), GL20.GL_TRIANGLES, attr,
                    //new Material(TextureAttribute.createDiffuse(myTexture)))
                    new Material(ColorAttribute.createDiffuse(0.2f, 0.8f, 0.2f, 1f))) //green
                    //new Material(ColorAttribute.createDiffuse(r.nextFloat(),r.nextFloat(),r.nextFloat(),1))) //random colors
                    .triangle(face.p1().toVector3(), face.p2().toVector3(), face.p3().toVector3());
        }

        world = new ModelInstance(modelBuilder.end(), 0, 0, 0);
    }

    //quiet fast
    private ArrayList<Face> createFaces(){
        ArrayList<Face> faces = new ArrayList<>(); //face list
        ArrayList<Vector> vectors = createPoints(); //vector list

        //calculates the amount of x and y coordinates according to the size of the terrain and the division size
        int xAmount = ((int)(width / DIV_SIZE))+1;
        int yAmount = ((int)(height / DIV_SIZE))+1;

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

    //quiet fast
    private ArrayList<Vector> createPoints(){
        ArrayList<Vector> vectors = new ArrayList<>(); //vector list
        //creates the vectors of the terrain, terrain centered at 0,0
        for (double i = 0; i <= width ; i+=DIV_SIZE) {
            for (double j= 0; j <= height ; j+=DIV_SIZE) {
                vectors.add(new Vector(i,j,function.evaluateF(i ,j)));
            }
        }
        return vectors;
    }

}
