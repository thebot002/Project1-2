package com.golf2k18.states.game;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.objects.Engine;
import com.golf2k18.objects.Face;
import com.golf2k18.objects.Function;
import com.golf2k18.objects.Vector;
import com.golf2k18.states.StateManager;

import java.util.ArrayList;
import java.util.Random;

public class Game3D extends GameState {

    //course size and division of the mesh of the terrain
    private final double X_SIZE = 50;
    private final double Y_SIZE = 50;
    private final double DIV_SIZE = 0.5; // divide the size by this value to have the amount of divisions on the course
                                         // needs to be <1 or a multiple of the size the terrain

    //variables
    private Engine engine;
    private Function function;

    private ModelInstance courseInstance;
    private ModelInstance sphere;
    private Environment environment;

    private CameraInputController inputController;

    public Game3D(StateManager gsm) {
        super(gsm);
        engine = new Engine();
        function = engine.getCourse().getFunction();
    }

    @Override
    public void create() {
        super.create();

        DirectionalLight light = new DirectionalLight();
        light.set(.8f,.8f,.8f, -1.f,-1.f,-1.f);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
        environment.add(light);

        /*ModelBuilder builder = new ModelBuilder();
        //Texture tex = new Texture("spheretexture02.jpg");
        //Material mat = new Material(TextureAttribute.createDiffuse(this.tex));

        Model tempSphere;
        tempSphere = builder.createSphere(2, 2, 2,
                50,50,
                new Material(),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );

        sphere = new ModelInstance(tempSphere,0,0,0); //x,y,z (+ve z = away, -ve z = behind)
*/

        createMesh();
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
    public void render(ModelBatch batch, Array<ModelInstance> instances) {
        //batch.render(instances,environment);
        batch.render(courseInstance,environment);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    private void createMesh(){
        ArrayList<Face> faces = createFaces();

        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        Texture myTexture = new Texture("badlogic.jpg");

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.manage(myTexture); //make modelbuilder responsible for disposing the texture resource

        Random r = new Random();
        for(Face face : faces) {

            modelBuilder.part("face"+face.id(), GL20.GL_TRIANGLES, attr,
                    //new Material(TextureAttribute.createDiffuse(myTexture)))
                    new Material(ColorAttribute.createDiffuse(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1)))
                    .triangle(face.p1().toVector3(), face.p2().toVector3(), face.p3().toVector3());
        }

        courseInstance = new ModelInstance(modelBuilder.end(), 0, 0, 0);
    }

    private ArrayList<Face> createFaces(){
        ArrayList<Face> faces = new ArrayList<>(); //face list
        ArrayList<Vector> vectors = createPoints(); //vector list

        //calculates the amount of x and y coordinates according to the size of the terrain and the division size
        int xAmount = ((int)(X_SIZE / DIV_SIZE))+1;
        int yAmount = ((int)(Y_SIZE / DIV_SIZE))+1;

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

    private ArrayList<Vector> createPoints(){
        ArrayList<Vector> vectors = new ArrayList<>(); //vector list

        //creates the vectors of the terrain, terrain centered at 0,0
        for (double i = -X_SIZE/2; i <= X_SIZE/2 ; i+=DIV_SIZE) {
            for (double j=-Y_SIZE/2; j <= Y_SIZE/2 ; j+=DIV_SIZE) {
                vectors.add(new Vector(i,j,function.evaluateF(i,j)));
            }
        }

        return vectors;
    }
}
