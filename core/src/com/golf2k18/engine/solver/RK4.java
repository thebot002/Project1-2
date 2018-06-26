package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

public class RK4 implements Solver {

    private Engine engine;

    private final boolean EULER = false;

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * solvePos calculates the balls position
     * @param position balls position at a given time
     * @param velocity balls velocity at a given time
     * @return Vector3 new updated position
     */
    @Override
    public Vector3 solvePos(Vector3 position, Vector3 velocity){
        if(EULER) return new Vector3(position.x + engine.getDt()*velocity.x,position.y + engine.getDt()*velocity.y,0);

        Vector3 f1 = new Vector3(solveVel(position,velocity));
        Vector3 k1 = f1.scl(engine.getDt());

        //adventurous step
        engine.sclDt(.5f);
        Vector3 vel2 = solveVel(position,velocity);
        engine.sclDt(2f);

        Vector3 pos2 = new Vector3(position).add(new Vector3(k1).scl(.5f));
        Vector3 f2 = new Vector3(solveVel(pos2,vel2));
        Vector3 k2 = f2.scl(engine.getDt());

        Vector3 pos3 = new Vector3(position).add(new Vector3(k2).scl(.5f));
        Vector3 f3 = new Vector3(solveVel(pos3,vel2));
        Vector3 k3 = f3.scl(engine.getDt());

        Vector3 vel4 = solveVel(position,velocity);
        Vector3 pos4 = new Vector3(position).add(new Vector3(k3));
        Vector3 f4 = new Vector3(solveVel(pos4,vel4));
        Vector3 k4 = f4.scl(engine.getDt());

        k2.scl(2);
        k3.scl(2);
        Vector3 sumK = new Vector3(k1.add(k2.add(k3.add(k4))));
        Vector3 newPos = new Vector3(new Vector3(position).add(sumK.scl(1f/6f)));
        newPos.z = 0;
        return newPos;
    }

    /**
     * solveVel uses 4th order Runge-Kutta to compute new velocity using vectors
     * method computes four approximations k1,k2,k3,k4 to estimate the slope at a given time
     * f1,f2,f3,f4 compute acceleration for each approximation
     * we then use a weighted sum of k1,k2,k3,k4 to get our final estimate of velocity
     * @param position balls position at a given time
     * @param velocity balls velocity at a given time
     * @return Vector3 newVelocity
     */
    @Override
    public Vector3 solveVel(Vector3 position, Vector3 velocity){
        Vector3 f1 = new Vector3(engine.getAcceleration(position,velocity));
        Vector3 k1 = f1.scl(engine.getDt());

        Vector3 pos2 = new Vector3(position).add(engine.getDt()/2);
        Vector3 vel2 = new Vector3(velocity).add(new Vector3(k1).scl(.5f));
        Vector3 f2 = new Vector3(engine.getAcceleration(pos2,vel2));
        Vector3 k2 = f2.scl(engine.getDt());

        Vector3 pos3 = new Vector3(position).add(engine.getDt()/2);
        Vector3 vel3 = new Vector3(velocity).add(new Vector3(k2).scl(.5f));
        Vector3 f3 = new Vector3(engine.getAcceleration(pos3,vel3));
        Vector3 k3 = f3.scl(engine.getDt());

        Vector3 pos4 = new Vector3(position).add(engine.getDt());
        Vector3 vel4 = new Vector3(velocity).add(new Vector3(k3));
        Vector3 f4 = new Vector3(engine.getAcceleration(pos4,vel4));
        Vector3 k4 = f4.scl(engine.getDt());

        k2.scl(2);
        k3.scl(2);
        Vector3 sumK = new Vector3(k1.add(k2.add(k3.add(k4))));
        return new Vector3(new Vector3(velocity).add(sumK.scl((float)1/6)));
    }
}
