package com.golf2k18.ai;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Goal;
import com.golf2k18.objects.Terrain;

public abstract class AI_Abstract {
    private Ball ball;
    private Terrain terrain;
    private Goal goal;

    public abstract Vector3 holeInOne();
    protected abstract Vector3 fitness();

}
