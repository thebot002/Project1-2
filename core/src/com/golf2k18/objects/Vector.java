package com.golf2k18.objects;

public class Vector
{
    public double x;
    public double y;
    public double z;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
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
    public Vector()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector(double x, double y , double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double magnitude()
    {
        return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
    }

}
