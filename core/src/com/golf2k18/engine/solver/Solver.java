package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

public interface Solver {
    Vector3 solveVel(Vector3 position, Vector3 velocity);
    Vector3 solvePos(Vector3 position, Vector3 velocity);
    void setEngine(Engine engine);
}
