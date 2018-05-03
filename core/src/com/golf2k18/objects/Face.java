package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

public class Face {
    private Vector3 p1;
    private Vector3 p2;
    private Vector3 p3;

    private int id;

    private static int amount = 0;

    public Face(Vector3 p1, Vector3 p2, Vector3 p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        this.id = ++amount;
    }

    public Vector3 p1() {
        return p1;
    }

    public Vector3 p2() {
        return p2;
    }

    public Vector3 p3() {
        return p3;
    }

    public int id() {
        return id;
    }
}
