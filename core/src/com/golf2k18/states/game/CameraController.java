package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends CameraInputController {

    public int ctrl = Input.Keys.CONTROL_LEFT;

    private Vector3 focus;
    private boolean focused;

    private Vector3 tmpV1 = new Vector3();
    private Vector3 tmpV2 = new Vector3();
    private float startX, startY;

    protected CameraController(CameraGestureListener gestureListener, Camera camera) {
        super(gestureListener, camera);
        rotateButton = Input.Buttons.RIGHT;
    }

    public CameraController(Camera camera) {
        super(camera);
        rotateButton = Input.Buttons.RIGHT;
    }


    public void focus(Vector3 focus){
        focused = true;
        this.focus = focus;
    }

    public void unfocus(){
        focused = !focused;
    }

    @Override
    protected boolean process(float deltaX, float deltaY, int button) {
        if(!focused && Gdx.input.isKeyPressed(ctrl)){
            camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits));
            camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits));
            if (translateTarget) target.add(tmpV1).add(tmpV2);
        }
        else if(focused){
            System.out.println(deltaX);
            camera.rotateAround(focus,new Vector3(0,0,1),deltaX*10);
            camera.rotateAround(focus,new Vector3(0,1,0),deltaY*10);
        }

        return true;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        boolean result = false;//super.touchDragged(screenX, screenY, pointer);
        if (result || this.button < 0) return result;
        final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
        final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
        startX = screenX;
        startY = screenY;
        return process(deltaX, deltaY, button);
    }
}
