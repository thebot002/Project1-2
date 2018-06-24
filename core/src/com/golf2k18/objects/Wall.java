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

public class Wall {
    private Vector3 p00, p10, p01, p11;

    private float thickness = .2f;

    private ModelInstance instance;
    private static Texture brick = new Texture("Textures/brick_texture.jpg");
    private int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;


    public Wall(Vector3 p0, Vector3 p1) {
        if(p0.equals(p1)){
            p00 = new Vector3(p0.x - thickness,p0.y - thickness,p0.z);
            p01 = new Vector3(p0.x + thickness,p0.y - thickness,p0.z);
            p10 = new Vector3(p0.x - thickness,p0.y + thickness,p0.z);
            p11 = new Vector3(p0.x + thickness,p0.y + thickness,p0.z);
        }
        else {
            p00 = new Vector3(p0.x - thickness,p0.y - thickness,p0.z);
            p01 = new Vector3(p1.x + thickness,p0.y - thickness,p0.z);
            p10 = new Vector3(p0.x - thickness,p1.y + thickness,p0.z);
            p11 = new Vector3(p1.x + thickness,p1.y + thickness,p0.z);
        }

        ModelBuilder builder = new ModelBuilder();
        Model wall = builder.createBox(p0.dst(p1)+(2*thickness),2*thickness,2*thickness,
                new Material(TextureAttribute.createDiffuse(brick)),attr);
        instance = new ModelInstance(wall, new Matrix4(new Vector3(p0).sub(thickness),new Quaternion(p1.sub(p0),1f),new Vector3(1,1,1)));
    }

    public ModelInstance getInstance() {
        return instance;
    }
}
