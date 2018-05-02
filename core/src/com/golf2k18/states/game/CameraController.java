package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends InputAdapter {

    private final int ctrl = Input.Keys.CONTROL_LEFT;
    private final int lArrow = Input.Keys.DPAD_LEFT;
    private final int rArrow = Input.Keys.DPAD_RIGHT;
    private final int uArrow = Input.Keys.DPAD_UP;
    private final int dArrow = Input.Keys.DPAD_DOWN;

    private final int rotateButton = Input.Buttons.RIGHT;

    private int button = -1;

    private Camera camera;

    private Vector3 focus;
    private boolean focused;

    private Vector3 tmpV1 = new Vector3();
    private Vector3 tmpV2 = new Vector3();
    private float startX, startY;

    private final float rotateUnit = 180f;
    private final float translateUnits = 10f;


    public CameraController(Camera camera) {
        this.camera = camera;
    }


    public void focus(Vector3 focus){
        camera.lookAt(focus);
        focused = true;
        this.focus = focus;
    }

    public void unfocus(){
        focused = !focused;
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        startX = screenX;
        startY = screenY;
        this.button = button;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(this.button == button) button = -1;
        return false;
    }

    private boolean process(float deltaX, float deltaY) {
        if(!focused || Gdx.input.isKeyPressed(ctrl)){
            camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits));
            camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits));
        }
        else if(focused){
            camera.rotateAround(focus,new Vector3(0,0,1),-(rotateUnit*deltaX));
            Vector3 normal = new Vector3(camera.up).rotate(camera.direction,90);
            camera.rotateAround(focus,normal,(rotateUnit*deltaY));
        }

        return false;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
        final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
        startX = screenX;
        startY = screenY;

        return process(deltaX,deltaY);
        }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }

    public void update(){

    }
}
