package com.golf2k18.states.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.golf2k18.function.Spline;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.State3D;
import com.golf2k18.StateManager;

import java.util.ArrayList;

/**
 * This class is used to create the course, with all its properties
 */
public class TerrainEditor extends State3D {
    private int startIndex;
    private Model nodeSelected;
    private Model nodeUnselected;

    private final float NODE_DIAM = 0.2f;
    private boolean ctrl = false;

    private Spline function;

    private ArrayList<Integer> selected;

    public TerrainEditor(StateManager manager, Terrain terrain) {
        super(manager, terrain);
    }

    /**
     * Creates the terrain according to the chosen function
     */
    @Override
    public void create() {
        super.create();

        if(!(terrain.getFunction() instanceof Spline))
            terrain.toSpline(1);
        function = (Spline) terrain.getFunction();

        createPoints(1);
        selected = new ArrayList<>();

        Gdx.input.setInputProcessor(new InputMultiplexer(this, controller));
        controller.toggleScroll();
    }

    private void createPoints(float interval){
        ModelBuilder builder = new ModelBuilder();

        nodeSelected = builder.createSphere(NODE_DIAM, NODE_DIAM, NODE_DIAM,
                50,50,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );

        nodeUnselected = builder.createSphere(NODE_DIAM, NODE_DIAM, NODE_DIAM,
                50,50,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );
        startIndex = instances.size;

        for (float i = 0; i <=terrain.getWidth() ; i+=interval) {
            for (float j = 0; j <= terrain.getHeight() ; j+=interval) {
                instances.add(new ModelInstance(nodeUnselected,i,j,function.evaluateF(i,j)));
            }
        }
    }

    @Override
    public boolean scrolled(int amount) {
        ArrayList<Vector3> newData = new ArrayList<>();
        for (Integer aSelected : selected) {
            Vector3 pos = instances.get(startIndex + aSelected).transform.getTranslation(new Vector3());
            //Vector3 nd = d1ToD2(aSelected);
            pos.z = function.evaluateF(pos.x, pos.y);
            pos.z += amount;
            newData.add(pos);
        }
        function.update(newData);
        super.createTerrain();
        return super.scrolled(amount);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0) {
            int pointI = getObject(screenX, screenY) - startIndex;
            if (!ctrl) {
                for (int i : selected) {
                    Vector3 pos = d1ToD2(i);
                    instances.set(i + startIndex, new ModelInstance(nodeUnselected, pos.y, pos.x, function.evaluateF(pos.x, pos.y)));
                }
                selected.clear();
            }
            if (pointI > -1) {
                Vector3 pos = d1ToD2(pointI);
                instances.set(pointI + startIndex, new ModelInstance(nodeSelected, pos.y, pos.x, function.evaluateF(pos.x, pos.y)));

                selected.add(pointI);
            }
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.CONTROL_LEFT)ctrl = true;
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.CONTROL_LEFT)ctrl = false;
        return super.keyUp(keycode);
    }

    private Vector3 d1ToD2(int point){
        float xAmount = terrain.getWidth() +1;

        float x = point % xAmount;
        float y = (point - x)/xAmount;

        return new Vector3(x,y,0);
    }

    /**
     * Converts the screen's (X,Y) coordinates into actual game coordinates for the camera
     * @param screenX x coordinate
     * @param screenY y coordinate
     * @return the game coordinates
     */
    private int getObject (int screenX, int screenY) {
        Ray ray = camera.getPickRay(screenX, screenY);

        int result = -1;
        float distance = -1;
        Vector3 position = new Vector3();

        for (int i = startIndex; i < instances.size; ++i) {
            final ModelInstance point = instances.get(i);

            point.transform.getTranslation(position);

            final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
            if (len < 0f)
                continue;

            float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
            if (distance >= 0f && dist2 > distance)
                continue;

            if (dist2 <= (NODE_DIAM/2) * (NODE_DIAM/2)) {
                result = i;
                distance = dist2;
            }
        }
        return result;
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
}
