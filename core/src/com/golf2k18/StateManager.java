package com.golf2k18;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.golf2k18.function.BiquinticSpline;
import com.golf2k18.function.Formula;
import com.golf2k18.function.Spline;
import com.golf2k18.handlers.HumanDrag;
import com.golf2k18.handlers.ai.simulatingBot;
import com.golf2k18.io.DataIO;
import com.golf2k18.io.Settings;
import com.golf2k18.objects.*;
import com.golf2k18.states.State;
import com.golf2k18.states.editor.TerrainEditor;
import com.golf2k18.states.game.Game;
import com.golf2k18.states.menu.MainMenu;

/**
 * This class contains the different states for the menu.
 */
public class StateManager extends ApplicationAdapter {
	private Stack<State> states;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;
    public static Settings settings;
    public static Music music;
    public static Skin skin;

    public StateManager() {
		states = new Stack<>();
	}

    /**
     * The menu has a skin and a state
     */
	@Override
    public void create () {
        skin = new Skin(Gdx.files.internal("Skins/gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));
        settings = Settings.load();
        //State start = new MainMenu(this);
        State start = new TerrainEditor(this,DataIO.getTerrain("Cos"));
        //State start = new Game(this,new Course(DataIO.getTerrain("PlaneSpline")),new HumanDrag());
        //State start = new Game(this,DataIO.getCourse("Default"),new HumanDrag());
        //State start = new Game(this,new Course(DataIO.getTerrain("Plane")),new simulatingBot());
        start.create();
        states.push(start);
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }

    public StateManager push(State state){
        state.create();
		states.push(state);
		return this;
	}

	public StateManager pop() {
	    states.peek().dispose();
		states.pop();
		return this;
	}

	public void set(State state){
		states.pop();
		state.create();
		states.push(state);
	}

	@Override
	public void render()
	{
        states.peek().render();
	}

	@Override
    public void dispose(){
        skin.dispose();
    }

    public void home(){
	    while(!(states.peek() instanceof MainMenu)){
	        states.pop();
        }
    }

    public static void reset(){
		int width = 20;
		int height = 20;
		Vector3 start = new Vector3(3,3,0);
		Vector3 goal = new Vector3(15,15,0);

		String[] cosx = {"x","cos"};
		Terrain c1 = new Terrain(width,height,start,goal,new Formula(cosx),"Cos");
		DataIO.writeTerrain(c1);

		String[] sinx = {"x","sin"};
		Terrain c2 = new Terrain(width,height,start,goal,new Formula(sinx),"Sin");
		DataIO.writeTerrain(c2);

		String[] cosy = {"y","cos"};
		Terrain c3 = new Terrain(width,height,start,goal,new Formula(cosy),"CosY");
		DataIO.writeTerrain(c3);

		String[] siny = {"y","sin"};
		Terrain c4 = new Terrain(width,height,start,goal,new Formula(siny),"SinY");
		DataIO.writeTerrain(c4);

        String[] flat = {"1"};
        Terrain c5 = new Terrain(width,height,start,goal,new Formula(flat),"Plane");
        DataIO.writeTerrain(c5);

        float[][] flatinter = new float[width+1][height+1];
        float[][] xDeriv = new float[width+1][height+1];
        float[][] yDeriv = new float[width+1][height+1];
        for (int i = 0; i < flatinter.length; i++) {
			for (int j = 0; j < flatinter[0].length; j++) {
				flatinter[i][j] = 1;
			}
		}
        for (int i = 0; i < flatinter[0].length; i++) {
            flatinter[0][i] = 4;
            xDeriv[0][i] = -2f;
            flatinter[1][i] = 2;
            xDeriv[1][i] = -1f;
        }
		flatinter[9][10] = 2;
        flatinter[10][9] = 2;
        flatinter[11][10] = 2;
        flatinter[10][11] = 2;
		flatinter[10][10] = 3;
        Terrain c6 = new Terrain(width,height,start,goal,new Spline(flatinter/*,xDeriv,yDeriv*/),"PlaneSpline");
		DataIO.writeTerrain(c6);

        ArrayList<Terrain> terrains = new ArrayList<>();
        terrains.add(c5);
        terrains.add(c2);
        terrains.add(c4);
        terrains.add(c6);
        Course course = new Course(terrains,"Default");
        DataIO.writeCourse(course);
    }
}
