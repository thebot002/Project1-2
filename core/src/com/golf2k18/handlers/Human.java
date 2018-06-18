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

/**
 * Defines the properties the different games commands if it is being played by a human.
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
        if(down) return;
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            game.getBall().getPosition().x += 0.1;
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            game.getBall().getPosition().x -= 0.1;
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            game.getBall().getPosition().y += 0.1;
        }
        if(manualMovement && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            game.getBall().getPosition().y -= 0.1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            if(manualMovement) game.setProcessors();
            else Gdx.input.setInputProcessor(new InputMultiplexer(game.hud, game));
            manualMovement = !manualMovement;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            double dir = game.directionInput.getValue();
            double intensity = game.intensityInput.getValue();
            game.getBall().hit(new Vector3((float)(Math.cos(Math.toRadians(dir))*intensity) , (float)(Math.sin(Math.toRadians(dir))*intensity) , 0));
            hitCount++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            if(game.controller.isFocused()){
                game.labels.get("focus").setText("");
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            if(!game.controller.isFocused())game.controller.focus(game.getBall().getPosition());
            else game.controller.unfocus();
        }
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(ballTouched(screenX, screenY)) down = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(down){
            ModelBuilder builder = new ModelBuilder();
            Model line = builder.createArrow(getTerrainMousePos(screenX,screenY, gameState.getTerrain().getFunction().evaluateF(gameState.getBall().getPosition().x, gameState.getBall().getPosition().y)), gameState.getBall().getPosition(),
                    new Material(ColorAttribute.createDiffuse(Color.BLACK)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
            );
            if(gameState.getInstances().size == 6) gameState.getInstances().removeIndex(gameState.getInstances().size - 1);
            gameState.getInstances().add(new ModelInstance(line,0,0,0));
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(down && gameState.getInstances().size == 6){
            gameState.getInstances().removeIndex(gameState.getInstances().size - 1);
            Vector3 currentPos = new Vector3(gameState.getBall().getPosition());
            gameState.getBall().hit(new Vector3(currentPos.add(new Vector3(getTerrainMousePos(screenX,screenY, gameState.getTerrain().getFunction().evaluateF(gameState.getBall().getPosition().x, gameState.getBall().getPosition().y))).scl(-1))).scl(2));
            hitCount++;
        }
        down = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private Vector3 getTerrainMousePos(int mouseX, int mouseY, float height){
        Ray ray = gameState.getCamera().getPickRay(mouseX, mouseY);

        Plane p = new Plane(new Vector3(0,0,1),height);
        Vector3 intersect = new Vector3();

        Intersector.intersectRayPlane(ray,p,intersect);

        return intersect;
    }

    private boolean ballTouched (int screenX, int screenY) {
        Vector3 position = new Vector3();
        Ray ray = gameState.getCamera().getPickRay(screenX, screenY);

        final ModelInstance instance = gameState.getBall().getModel();
        instance.transform.getTranslation(position);
        final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
        if (dist2 <= (gameState.getBall().getDiameter()/2) * (gameState.getBall().getDiameter()/2)) return true;
        return false;
    }
}
