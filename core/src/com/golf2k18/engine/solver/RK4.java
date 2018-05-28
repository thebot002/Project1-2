package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

public class RK4 implements Solver {

    private Engine engine;

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public Vector3 solvePos(Vector3 position, Vector3 velocity){
        Vector3 f1 = new Vector3(solveVel(position,velocity));
        Vector3 k1 = f1.scl(engine.getDt());

        Vector3 vel2 = new Vector3(velocity).add(engine.getDt()/2);
        Vector3 pos2 = new Vector3(position).add(new Vector3(k1).scl(.5f));
        Vector3 f2 = new Vector3(solveVel(pos2,vel2));
        Vector3 k2 = f2.scl(engine.getDt());

        Vector3 vel3 = new Vector3(velocity).add(engine.getDt()/2);
        Vector3 pos3 = new Vector3(position).add(new Vector3(k2).scl(.5f));
        Vector3 f3 = new Vector3(solveVel(pos3,vel3));
        Vector3 k3 = f3.scl(engine.getDt());

        Vector3 vel4 = new Vector3(velocity).add(engine.getDt());
        Vector3 pos4 = new Vector3(position).add(new Vector3(k3));
        Vector3 f4 = new Vector3(solveVel(pos4,vel4));
        Vector3 k4 = f4.scl(engine.getDt());

        k2.scl(2);
        k3.scl(2);
        Vector3 sumK = new Vector3(k1.add(k2.add(k3.add(k4))));
        return new Vector3(new Vector3(position).add(sumK.scl((float)1/6)));
    }

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
