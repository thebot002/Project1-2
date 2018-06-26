package com.golf2k18.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.io.Serializable;

public class Wall implements Collider,Serializable {
    private Vector3 p00, p10, p01, p11;

    private float thickness = .2f;

    private ModelInstance instance;
    private static Texture brick = new Texture("Textures/brick_texture.jpg");
    private int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;


    public Wall(Vector3 p0, Vector3 p1) {
        if(p0.equals(p1)){
            p00 = new Vector3(p0.x - thickness,p0.y - thickness,0);
            p01 = new Vector3(p0.x + thickness,p0.y - thickness,0);
            p10 = new Vector3(p0.x - thickness,p0.y + thickness,0);
            p11 = new Vector3(p0.x + thickness,p0.y + thickness,0);
        }
        else {
            p00 = new Vector3(p0.x - thickness,p0.y - thickness,0);
            p01 = new Vector3(p1.x + thickness,p0.y - thickness,0);
            p10 = new Vector3(p0.x - thickness,p1.y + thickness,0);
            p11 = new Vector3(p1.x + thickness,p1.y + thickness,0);
        }

        Vector3 p0p1 = p1.cpy().sub(p0);
        float angle = (float) Math.acos(p0p1.dot(1,0,0) / p0p1.len());

        Quaternion orientation = new Quaternion(p0p1.cpy().crs(-1,0,0),(float)Math.toDegrees(angle));

        Vector3 position = p0.cpy().add(p0p1.cpy().scl(.5f));

        ModelBuilder builder = new ModelBuilder();
        Model wall = builder.createBox(p0p1.len()+(2*thickness),2*thickness,2*thickness,
                new Material(TextureAttribute.createDiffuse(brick)),attr);
        instance = new ModelInstance(wall, new Matrix4(position,orientation,new Vector3(1,1,1)));
    }

    public ModelInstance getInstance() {
        return instance;
    }

    @Override
    public Vector3 getTopLeftCorner() {
        return p01;
    }

    public Vector3 getBottomLeftCorner(){return p00;}

    @Override
    public Vector3 getBottomRightCorner() {
        return p10;
    }

    public Vector3 getTopRightCorner(){ return p11;}





}
