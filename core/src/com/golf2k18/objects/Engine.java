package com.golf2k18.objects;

public class Engine
{
    private Vector acceleration;
    private Course course;
    private Function function;
    private Function.Node root;
    private final double GRAVITY = 9.81;
    public Engine()
    {
        course = new Course(100,100);
        function = new Function();
        String[] string = {"0.2","y","*","0.1","x","*","+","0.03","x","2","^","*","+"};
        root = function.constructTree(string);
    }
    public Vector calcGravity(Ball ball)
    {
        Vector Fz = new Vector();
        Fz.setX((int) (-ball.getMass()*GRAVITY*function.xPartial(root,ball.getX(), ball.getY(), 0.0001)));
        Fz.setY((int) (-ball.getMass()*GRAVITY*function.yPartial(root,ball.getX(),ball.getY(),0.0001)));
        return Fz;
    }
    public Vector calcFriction(Ball ball)
    {
        Vector v = ball.getVelocity();
        v.scale(-course.getMu(ball.getX(), ball.getY())*ball.getMass()*GRAVITY);
        return v;
    }
}
