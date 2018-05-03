package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;

public class Engine
{
    private Course course;
    private final double GRAVITY = 9.81;
    private final double STOP_TOLERANCE = 0.2;

    public Engine()
    {
        String[] str = {"x","cos"};
        //String[] str = {"0"};
        //String[] str = {"0","0.5","x","5","-","2","^","^","-"};
        //String[] str = {"y","x","*"};
        //String[] str = {"0.2", "y", "*", "0.1", "x", "*", "+", "0.03", "x", "2", "^", "*", "+"};

        course = new Course(20,20,str);
    }
    private Vector calcGravity(Ball ball)
    {
        Vector Fz = new Vector();
        Fz.setX((-ball.getMass()*GRAVITY*course.getFunction().evaluateXDeriv(ball.getX(), ball.getY())));
        Fz.setY((-ball.getMass()*GRAVITY*course.getFunction().evaluateYDeriv(ball.getX(),ball.getY())));
        return Fz;
    }
    private Vector calcFriction(Ball ball)
    {
        Vector v = ball.getVelocity();
        if(v.magnitude() != 0.0) v.scale(1/v.magnitude());
        v.scale(-course.MU*ball.getMass()*GRAVITY);
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
        ball.setZ(course.getFunction().evaluateF(ball.getX(),ball.getY()));

        if(ball.getVelocity().magnitude() <= STOP_TOLERANCE && (calcGravity(ball).magnitude() / ball.getMass()) <= STOP_TOLERANCE) ball.setStopped();
        eulerVx(ball,Gdx.graphics.getDeltaTime());
        eulerVy(ball,Gdx.graphics.getDeltaTime());
    }

    public Course getCourse() {
        return course;
    }
}
