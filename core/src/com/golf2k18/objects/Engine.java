package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;

public class Engine
{
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
    private Vector calcGravity(Ball ball)
    {
        Vector Fz = new Vector();
        Fz.setX((int) (-ball.getMass()*GRAVITY*function.xPartial(root,ball.getX(), ball.getY(), 0.0001)));
        Fz.setY((int) (-ball.getMass()*GRAVITY*function.yPartial(root,ball.getX(),ball.getY(),0.0001)));
        return Fz;
    }
    private Vector calcFriction(Ball ball)
    {
        Vector v = ball.getVelocity();
        v.scale(-course.getMu(ball.getX(), ball.getY())*ball.getMass()*GRAVITY);
        return v;
    }
    public Vector getAcceleration(Ball ball)
    {
        Vector v = calcGravity(ball);
        v.add(calcFriction(ball));
        return v;
    }
    private int eulerX(Ball ball, float dt)
    {
        double x = ball.getX();
        double y = ball.getY();
        double vX = ball.getVelocity().getX();
        double vY = ball.getVelocity().getY();
        double mass = ball.getMass();
        double newV = ball.getVelocity().getX() + (getAcceleration(ball).getX() * dt * (1/mass));

        return (int)(x + (dt * newV));
    }
    private int eulerY(Ball ball, float dt)
    {
        double x = ball.getX();
        double y = ball.getY();
        double vX = ball.getVelocity().getX();
        double vY = ball.getVelocity().getY();
        double mass = ball.getMass();
        double newV = ball.getVelocity().getY() + (getAcceleration(ball).getY() * dt * (1/mass));

        return(int)(y + (dt * newV));
    }
    public void updateBall(int x, int y, Ball ball)
    {
        ball.updateLoctation(eulerX(ball,Gdx.graphics.getDeltaTime()),eulerY(ball,Gdx.graphics.getDeltaTime()));
    }


}
