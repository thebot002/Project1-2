package com.golf2k18.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class CameraController extends InputAdapter {

    private final int ctrl = Input.Keys.CONTROL_LEFT;
    private final int lArrow = Input.Keys.DPAD_LEFT;
    private final int rArrow = Input.Keys.DPAD_RIGHT;
    private final int uArrow = Input.Keys.DPAD_UP;
    private final int dArrow = Input.Keys.DPAD_DOWN;

    private final int rotateButton = Input.Buttons.RIGHT;

    private int button = -1;
    private ArrayList<Integer> keyPressed = new ArrayList<Integer>();

    private Camera camera;

    private Vector3 focus;
    private boolean focused;

    private Vector3 tmpV1 = new Vector3();
    private Vector3 tmpV2 = new Vector3();
    private float startX, startY;

    private final float rotateUnit = 2f;
    private final float translateUnits = 0.5f;
    private int bottom = 1;


    public boolean isFocused() {
        return focused && !Gdx.input.isKeyPressed(ctrl);
    }

    public CameraController(Camera camera) {
        this.camera = camera;
        focus = camera.position;
    }

    public void focus(Vector3 focus){
        camera.lookAt(focus);
        camera.up.set(0,0,1);
        camera.lookAt(focus);
        focused = true;
        this.focus = focus;
    }

    public void unfocus(){
        focused = !focused;
        focus = camera.position;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == ctrl){
            focused = !focused;
            return false;
        }
        keyPressed.add(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == ctrl){
            focused = !focused;
            if(focused) unfocus();
            return false;
        }
        for (int i = 0; i < keyPressed.size(); i++) {
            if(keycode == keyPressed.get(i)) keyPressed.remove(i);
        }
        return false;
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
        if(this.button == button) this.button = -1;
        return false;
    }

    private boolean process(float deltaX, float deltaY) {
        if(button == rotateButton && !focused){
            camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * 40 * translateUnits));
            if(deltaY < 0 || camera.position.z > bottom)
                camera.translate(tmpV2.set(camera.up).scl(-deltaY * 40 * translateUnits));
        }
        else if(button == rotateButton && focused){
            camera.rotateAround(focus,new Vector3(0,0,1),-(90*rotateUnit*deltaX));
            Vector3 normal = new Vector3(camera.up).rotate(camera.direction,90);
            if(camera.up.z > 0 || deltaY > 0)
                camera.rotateAround(focus,normal,(90*rotateUnit*deltaY));
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
        if(amount<0 || camera.position.z>bottom)
            camera.translate(tmpV1.set(camera.direction).scl(amount*0.8f));
        return false;
    }

    public void update(){
        if(keyPressed.contains(lArrow)){
            if(!focused){
                camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-translateUnits));
            }
            else{
                camera.rotateAround(focus,new Vector3(0,0,1),-rotateUnit);
            }
        }
        if(keyPressed.contains(rArrow)){
            if(!focused){
                camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(translateUnits));
            }
            else{
                camera.rotateAround(focus,new Vector3(0,0,1),rotateUnit);
            }
        }
        if(keyPressed.contains(uArrow)){
            if(!focused){
                camera.translate(tmpV2.set(camera.up).scl(translateUnits));
            }
            else if(camera.up.z>=0){
                Vector3 normal = new Vector3(camera.up).rotate(camera.direction,90);
                camera.rotateAround(focus,normal,-rotateUnit);
            }
        }
        if(keyPressed.contains(dArrow) && camera.position.z > bottom){
            if(!focused){
                camera.translate(tmpV2.set(camera.up).scl(-translateUnits));
            }
            else{
                Vector3 normal = new Vector3(camera.up).rotate(camera.direction,90);
                camera.rotateAround(focus,normal,rotateUnit);
            }
        }
        camera.update();
    }
}
