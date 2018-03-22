package com.golf2k18.objects;

public class Vector
{
    private int x;
    private int y;
    private int z;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    public Vector add(Vector vector)
    {
        Vector v = vector;
        v.setX(this.x + v.getX());
        v.setY(this.y + v.getY());
        v.setZ(this.z + v.getZ());
        return v;
    }
    public void scale(double a)
    {
        this.x *= a;
        this.y *= a;
        this.z *= a;
    }


    public Vector(int x, int y , int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
