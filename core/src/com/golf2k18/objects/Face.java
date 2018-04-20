package com.golf2k18.objects;

public class Face {
    private Vector p1;
    private Vector p2;
    private Vector p3;

    private int id;

    static int amount;

    public Face(Vector p1, Vector p2, Vector p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        this.id = amount;
        amount++;
    }

    public Vector p1() {
        return p1;
    }

    public Vector p2() {
        return p2;
    }

    public Vector p3() {
        return p3;
    }

    public int id() {
        return id;
    }
}
