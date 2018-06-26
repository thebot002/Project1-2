package com.golf2k18.io;

import com.golf2k18.engine.solver.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable {
    private ArrayList<String> solvers;
    private int selectedSolver;

    private ArrayList<String> sources;
    private int selectedSource;

    private float musicVolume;

    private boolean water = false;
    private boolean wind = false;
    private float windIntensity = 1f;

    public Settings(){
        selectedSolver = 1;

        solvers = new ArrayList<>();

        solvers.add("Euler");
        solvers.add("Runge Kutta");
        solvers.add("Adams Bashforth");
        solvers.add("Adams Moulton");
        solvers.add("Bogacki Shampine");

        selectedSource = 0;

        sources = new ArrayList<>();

        sources.add("Manual");
        sources.add("Arrow");

        musicVolume = 0f;
    }

    public ArrayList<String> getSolvers() {
        return solvers;
    }

    public Solver getSolver() {
        Solver solver;
        switch (selectedSolver){
            case 0:
                solver = new Euler();
                break;
            case 1:
                solver = new RK4();
                break;
            case 2:
                solver = new AB4();
                break;
            case 3:
                solver = new AM3();
                break;
            case 4:
                solver = new BS();
                break;
            default:
                solver = new RK4();
                break;
        }
        return solver;
    }

    public int getSelectedSolver() {
        return selectedSolver;
    }

    public void setSelectedSolver(int i){
        selectedSolver = i;
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public int getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(int i) {
        selectedSource = i;
    }

    public void setMusicVolume(float volume){
        musicVolume = volume;
    }

    public float getMusicVolume(){
        return musicVolume;
    }

    public boolean hasWater(){
        return water;
    }

    public void setWater(boolean water){
        this.water = water;
    }

    public boolean hasNoise() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public float getWindIntensity() {
        return windIntensity;
    }

    public void setWindIntensity(float windIntensity) {
        this.windIntensity = windIntensity;
    }

    public static Settings load(){
        Settings settings = null;
        try{
            settings = DataIO.getSettings();
        }
        catch (FileNotFoundException | InvalidClassException e){
            settings = new Settings();
            DataIO.writeSettings(settings);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            return settings;
        }
    }
}
