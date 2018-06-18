package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

/**
 * Euler method to solve differential equations
 */
public class Euler implements Solver {

    private Engine engine;
/**
 * solvePos calculates ball position after a single time step
 * @param  position ball position
 * @param  velocity ball velocity
 *@return Vector3 returns the new ball position
 */
    @Override
    public Vector3 solvePos(Vector3 position, Vector3 velocity){
        return new Vector3(position.x + engine.getDt()*velocity.x,position.y + engine.getDt()*velocity.y,0);
    }

    /**
     * solveVel calculates the balls velocity
     * @param position ball position
     * @param velocity ball velocity
     * @return Vector3 updated velocity
     */
    @Override
    public Vector3 solveVel(Vector3 position, Vector3 velocity){
        float aX = engine.getAcceleration(position,velocity).x;
        float aY = engine.getAcceleration(position,velocity).y;
        return new Vector3(velocity.x + engine.getDt()*aX, velocity.y + engine.getDt()*aY, 0);
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}