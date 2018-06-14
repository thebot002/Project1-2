package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

import java.util.ArrayList;

public class AM3 implements Solver {
    private Engine engine;
    private ArrayList<Vector3> positions = new ArrayList<>();
    private ArrayList<Vector3> velocities = new ArrayList<>();
    private Solver predictor = new AB4();

    @Override
    public Vector3 solveVel(Vector3 position, Vector3 velocity) {
        if(velocities.isEmpty()){
            positions.add(position);
            velocities.add(velocity);
        }
        if(velocities.size() < 3){
            Vector3 newVel = predictor.solveVel(position,velocity);
            velocities.add(newVel);
            positions.add(predictor.solvePos(position,newVel));
            return newVel;
        }
        else{
            Vector3 predVel = predictor.solveVel(position,velocity);
            Vector3 predPos = predictor.solvePos(position,velocity);

            Vector3 f1 = new Vector3(engine.getAcceleration(predVel,predPos)).scl(9f);
            Vector3 f2 = new Vector3(engine.getAcceleration(positions.get(2),velocities.get(2))).scl(19f);
            Vector3 f3 = new Vector3(engine.getAcceleration(positions.get(1),velocities.get(1))).scl(-5f);
            Vector3 f4 = new Vector3(engine.getAcceleration(positions.get(0),velocities.get(0)));

            Vector3 newVel = velocities.get(2).add(new Vector3(f1.add(f2.add(f3.add(f4)))).scl(engine.getDt()/24f));

            velocities.set(0,velocities.get(1));
            velocities.set(1,velocities.get(2));
            velocities.set(2,newVel);

            positions.set(0,positions.get(1));
            positions.set(1,positions.get(2));
            positions.set(2,solvePos(position,newVel));

            return newVel;
        }
    }

    @Override
    public Vector3 solvePos(Vector3 position, Vector3 velocity) {
        return new Vector3(position.x + engine.getDt()*velocity.x,position.y + engine.getDt()*velocity.y,0);
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
        predictor.setEngine(engine);
    }
}
