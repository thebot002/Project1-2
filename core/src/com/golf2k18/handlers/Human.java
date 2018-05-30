package com.golf2k18.handlers;

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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.golf2k18.states.game.Game;
import com.golf2k18.states.menu.endStates.WinState;

/**
 * Defines the properties the different gams commands if it is being played by a human.
 */
public class Human extends Player {
    private boolean manualMovement = false;
    private boolean down = false;

    /**
     * Manual movements that can be executed by a player, using the keyboard and the mouse.
     * @param game the game that is played
     */
    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            game.getBall().setX(game.getBall().getX()+0.1);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            game.getBall().setX(game.getBall().getX()-0.1);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            game.getBall().setY(game.getBall().getY()+0.1);
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            game.getBall().setY(game.getBall().getY()-0.1);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            if(manualMovement) game.setProcessors();
            else Gdx.input.setInputProcessor(new InputMultiplexer(game.hud,game));
            manualMovement = !manualMovement;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            double dir = game.directionInput.getValue();
            double intensity = game.intensityInput.getValue();
            System.out.println(intensity);
            game.getBall().hit(new Vector3((float)(Math.cos(Math.toRadians(dir))*intensity) , (float)(Math.sin(Math.toRadians(dir))*intensity) , 0));
        }
        /*if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            if(game.controller.isFocused()){
                game.labels.get("focus").setText("");
            }
        }*/
        if(game.isHit(game.getBall())){
            game.getStateManager().push(new WinState(game.getStateManager()));
        }
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(getObject(screenX, screenY)==4) down = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(down){
            ModelBuilder builder = new ModelBuilder();
            Model line = builder.createArrow(getTerrainMousePos(screenX,screenY,game.getTerrain().getFunction().evaluateF(game.getBall().getX(),game.getBall().getY())),game.getBall().getPosition(),
                    new Material(ColorAttribute.createDiffuse(Color.BLACK)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
            );
            if(game.getInstances().size == 6) game.getInstances().removeIndex(game.getInstances().size - 1);
            game.getInstances().add(new ModelInstance(line,0,0,0));
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(down && game.getInstances().size == 6){
            game.getInstances().removeIndex(game.getInstances().size - 1);
            Vector3 currentPos = new Vector3(game.getBall().getPosition());
            game.getBall().hit(new Vector3(currentPos.add(new Vector3(getTerrainMousePos(screenX,screenY,game.getTerrain().getFunction().evaluateF(game.getBall().getX(),game.getBall().getY()))).scl(-1))).scl(2));
        }
        down = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private Vector3 getTerrainMousePos(int mouseX, int mouseY, float height){
        Ray ray = game.getCamera().getPickRay(mouseX, mouseY);

        Plane p = new Plane(new Vector3(0,0,1),height);
        Vector3 intersect = new Vector3();

        Intersector.intersectRayPlane(ray,p,intersect);

        return intersect;
    }

    private int getObject (int screenX, int screenY) {
        Vector3 position = new Vector3();
        Ray ray = game.getCamera().getPickRay(screenX, screenY);
        int result = -1;
        final ModelInstance instance = game.getInstances().get(4);
        instance.transform.getTranslation(position);
        final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
        if (dist2 <= (game.getBall().getDiameter()/2) * (game.getBall().getDiameter()/2)) {
            result = 4;
        }
        return result;
    }
}
