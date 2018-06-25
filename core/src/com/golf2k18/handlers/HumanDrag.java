package com.golf2k18.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class HumanDrag extends Human {
    private ShapeRenderer renderer;
    private Vector2 ball;
    private Vector2 mouse;

    private boolean down = false;

    public HumanDrag() {
        renderer = new ShapeRenderer();
    }

    @Override
    public void renderInput() {
        if(down){
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.BLACK);
            renderer.rectLine(ball,mouse,4);
            renderer.end();
        }
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(!gameState.getBall().isStopped() || button != 0) return false;
        ball = ballTouched(screenX,screenY);
        if(ball != null){
            down = true;
            mouse = new Vector2(screenX,Gdx.graphics.getHeight()-screenY);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(down) mouse = new Vector2(screenX,Gdx.graphics.getHeight()-screenY);
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(down && button == 0){
            Vector3 velocity = getTerrainMousePos(screenX,screenY,gameState.getBall().getPosition().z).scl(-1f).add(gameState.getBall().getPosition()).scl(2);
            gameState.getBall().hit(velocity);
            hitCount++;
        }
        down = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private Vector3 getTerrainMousePos(int mouseX, int mouseY, float height){
        Ray ray = gameState.getCamera().getPickRay(mouseX, mouseY);

        Plane p = new Plane(new Vector3(0,0,height),new Vector3(0,1,height),new Vector3(1,0,height));
        Vector3 intersect = new Vector3();

        Intersector.intersectRayPlane(ray,p,intersect);
        return intersect;
    }

    private Vector2 ballTouched (int screenX, int screenY) {
        Vector3 position = new Vector3();
        Vector2 pos = null;
        Ray ray = gameState.getCamera().getPickRay(screenX, screenY);

        final ModelInstance instance = gameState.getBall().getModel();
        instance.transform.getTranslation(position);
        final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
        if(dist2 <= (gameState.getBall().getDiameter() / 2) * (gameState.getBall().getDiameter() / 2)){
            Vector3 screenPos = gameState.getCamera().project(position);
            pos = new Vector2(screenPos.x,screenPos.y);
        }
        return pos;
    }
}
