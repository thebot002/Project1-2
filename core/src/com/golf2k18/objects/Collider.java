package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.states.game.Game;

public interface Collider {


     Vector3 getTopLeftCorner();
     Vector3 getBottomRightCorner();
}
