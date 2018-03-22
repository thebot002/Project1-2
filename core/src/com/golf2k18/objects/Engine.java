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
        String[] str = {"0.2", "y", "*", "0.1", "x", "*", "+", "0.03", "x", "2", "^", "*", "+"};
        course = new Course(100,100,str);
        function = new Function();

        root = function.constructTree(course.formula);
    }
    private Vector calcGravity(Ball ball)
    {
        Vector Fz = new Vector();
        Fz.setX((-ball.getMass()*GRAVITY*function.xPartial(root,ball.getX(), ball.getY(), 0.0001)));
        Fz.setY((-ball.getMass()*GRAVITY*function.yPartial(root,ball.getX(),ball.getY(),0.0001)));
        return Fz;
    }
    private Vector calcFriction(Ball ball)
    {
        Vector v = ball.getVelocity();
        v.scale(-course.MU*ball.getMass()*GRAVITY);
        v.scale(1/v.magnitude());
        return v;
    }
    public Vector getAcceleration(Ball ball)
    {
        Vector v = calcGravity(ball);
        v.add(calcFriction(ball));
        v.scale(1/ball.getMass());
        return v;
    }
    private double eulerX(Ball ball, double dt)
    {
        double vX = ball.getVelocity().getX();
        return ball.getX() + dt*vX;

    }
    private void eulerVx(Ball ball, double dt)
    {
        double aX = getAcceleration(ball).getX();
        ball.updateVelocityX(ball.getVelocity().getX() + dt*aX);
    }
    private double eulerY(Ball ball, double dt)
    {
        double vY = ball.getVelocity().getY();
        return ball.getY() + dt*vY;

    }
    private void eulerVy(Ball ball, double dt)
    {
        double aY = getAcceleration(ball).getY();
        ball.updateVelocityY(ball.getVelocity().getY() + dt*aY);
    }

    public void updateBall(Ball ball)
    {
        ball.updateLocation(eulerX(ball,(double)Gdx.graphics.getDeltaTime()),eulerY(ball,Gdx.graphics.getDeltaTime()));
        eulerVx(ball,Gdx.graphics.getDeltaTime());
        eulerVy(ball,Gdx.graphics.getDeltaTime());
    }


}
