package com.golf2k18.engine.solver;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;

public class BS implements Solver {

    private Engine engine;
    private final float e = 0.00001f;

    @Override
    public Vector3 solveVel(Vector3 position, Vector3 velocity) {
        float h = engine.getDt();

        float currentH = h;
        Vector3 newPos = position;
        Vector3 newVel = velocity;

        do{
            Vector3 f1 = new Vector3(engine.getAcceleration(newPos,newVel));
            Vector3 k1 = f1.scl(currentH);

            Vector3 pos2 = new Vector3(newPos).add(currentH/2);
            Vector3 vel2 = new Vector3(newVel).add(new Vector3(k1).scl(.5f));
            Vector3 f2 = new Vector3(engine.getAcceleration(pos2,vel2));
            Vector3 k2 = f2.scl(currentH);

            Vector3 pos3 = new Vector3(newPos).add(currentH*.75f);
            Vector3 vel3 = new Vector3(newVel).add(new Vector3(k2).scl(.75f));
            Vector3 f3 = new Vector3(engine.getAcceleration(pos3,vel3));
            Vector3 k3 = f3.scl(currentH);

            Vector3 tempk1 = new Vector3(k1).scl(2f/9f);
            Vector3 tempk2 = new Vector3(k2).scl(3f/9f);
            Vector3 tempk3 = new Vector3(k3).scl(4f/9f);

            Vector3 k = tempk1.add(tempk2.add(tempk3));

            Vector3 pos4 = new Vector3(newPos).add(currentH);
            Vector3 vel4 = new Vector3(newVel).add(new Vector3(k));
            Vector3 f4 = new Vector3(engine.getAcceleration(pos4,vel4));
            Vector3 k4 = f4.scl(currentH);

            Vector3 temp2k1 = new Vector3(k1).scl(7f/24f);
            Vector3 temp2k2 = new Vector3(k2).scl(6f/24f);
            Vector3 temp2k3 = new Vector3(k3).scl(8f/24f);
            Vector3 temp2k4 = new Vector3(k4).scl(3f/24f);

            Vector3 kp = temp2k1.add(temp2k2.add(temp2k3.add(temp2k4)));

            Vector3 difK = new Vector3(k).add(new Vector3(kp).scl(-1f));

            if(difK.len() <= (e*currentH)){
                newVel.add(k);
                newPos.add(currentH);
                h -= currentH;
                currentH = h;
            }
            else{
                float q = (float)Math.sqrt((e*currentH)/(2*difK.len()));
                currentH = q*currentH;
            }
        }while (h>0);

        return newVel;
    }

    @Override
    public Vector3 solvePos(Vector3 position, Vector3 velocity) {
        float h = engine.getDt();

        float currentH = h;
        Vector3 newPos = position;
        Vector3 newVel = velocity;

        do{
            Vector3 f1 = new Vector3(solveVel(newPos,newVel));
            Vector3 k1 = f1.scl(currentH);

            Vector3 vel2 = new Vector3(newVel).add(currentH/2);
            Vector3 pos2 = new Vector3(newPos).add(new Vector3(k1).scl(.5f));
            Vector3 f2 = new Vector3(solveVel(pos2,vel2));
            Vector3 k2 = f2.scl(currentH);

            Vector3 vel3 = new Vector3(newVel).add(currentH*.75f);
            Vector3 pos3 = new Vector3(newPos).add(new Vector3(k2).scl(.75f));
            Vector3 f3 = new Vector3(solveVel(pos3,vel3));
            Vector3 k3 = f3.scl(currentH);

            Vector3 tempk1 = new Vector3(k1).scl(2f/9f);
            Vector3 tempk2 = new Vector3(k2).scl(3f/9f);
            Vector3 tempk3 = new Vector3(k3).scl(4f/9f);

            Vector3 k = tempk1.add(tempk2.add(tempk3));

            Vector3 vel4 = new Vector3(newVel).add(currentH);
            Vector3 pos4 = new Vector3(newPos).add(new Vector3(k));
            Vector3 f4 = new Vector3(solveVel(pos4,vel4));
            Vector3 k4 = f4.scl(currentH);

            Vector3 temp2k1 = new Vector3(k1).scl(7f/24f);
            Vector3 temp2k2 = new Vector3(k2).scl(6f/24f);
            Vector3 temp2k3 = new Vector3(k3).scl(8f/24f);
            Vector3 temp2k4 = new Vector3(k4).scl(3f/24f);

            Vector3 kp = temp2k1.add(temp2k2.add(temp2k3.add(temp2k4)));

            Vector3 difK = new Vector3(k).add(new Vector3(kp).scl(-1f));

            if(difK.len() <= (e*currentH)){
                newPos.add(k);
                newVel = newVel.add(currentH);
                h -= currentH;
                currentH = h;
            }
            else{
                float q = (float)Math.sqrt((e*currentH)/(2*difK.len()));
                currentH = q*currentH;
            }
        }while (h>0);

        return newVel;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
