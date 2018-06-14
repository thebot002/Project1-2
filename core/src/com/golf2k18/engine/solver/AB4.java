package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

import java.util.ArrayList;

public class AB4 implements Solver{

    private Engine engine;
    private ArrayList<Vector3> positions = new ArrayList<>();
    private ArrayList<Vector3> velocities = new ArrayList<>();

    private Solver bootstrapper = new RK4();

    @Override
    public Vector3 solveVel(Vector3 position, Vector3 velocity) {
        if(velocities.isEmpty()){
            positions.add(null);
            velocities.add(null);
        }
        if(velocities.size() < 4){
            velocities.add(velocity);
            positions.add(position);
            return bootstrapper.solveVel(position,velocity);
        }
        else{
            velocities.set(0,velocities.get(1));
            velocities.set(1,velocities.get(2));
            velocities.set(2,velocities.get(3));
            velocities.set(3,velocity);

            positions.set(0,positions.get(1));
            positions.set(1,positions.get(2));
            positions.set(2,positions.get(3));
            positions.set(3,position);

            Vector3 f1 = new Vector3(engine.getAcceleration(positions.get(3),velocities.get(3))).scl(55f);
            Vector3 f2 = new Vector3(engine.getAcceleration(positions.get(2),velocities.get(2))).scl(-59f);
            Vector3 f3 = new Vector3(engine.getAcceleration(positions.get(1),velocities.get(1))).scl(37f);
            Vector3 f4 = new Vector3(engine.getAcceleration(positions.get(0),velocities.get(0))).scl(-9f);

            return velocities.get(3).add(new Vector3(f1.add(f2.add(f3.add(f4)))).scl(engine.getDt()/24f));
        }
    }

    @Override
    public Vector3 solvePos(Vector3 position, Vector3 velocity) {
        return new Vector3(position.x + engine.getDt()*velocity.x,position.y + engine.getDt()*velocity.y,0);
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
        bootstrapper.setEngine(engine);
    }
}
