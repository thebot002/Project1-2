package com.golf2k18.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.golf2k18.StateManager;
import com.golf2k18.objects.Course;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.menu.Settings;

import java.io.*;

/**
 * Class that contains the information about the course inputs and outputs
 */
public class DataIO {
    private static final FileHandle data_folder = Gdx.files.internal("Data");

    /**
     *Method used to contain all the different course names.
     * @return
     */
    public static Array<String> getTerrainNames(){
        FileHandle[] children = data_folder.child("Terrains").list();
        Array<String> childrenNames = new Array<>();
        for (FileHandle child: children) {
            childrenNames.add(child.nameWithoutExtension());
        }
        return childrenNames;
    }

    /**
     * Method used to write course properties into a file.
     * @param terrain The variable that designates the course.
     */
    public static void writeTerrain(Terrain terrain){
        try{
            FileOutputStream fos = new FileOutputStream(data_folder.path() + "/Terrains/" + terrain.getName() + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(terrain);
            oos.close();
        } catch (IOException e){
            System.out.print(data_folder.path() + "/Terrains/" + terrain.getName() + ".ser");
            e.printStackTrace();
        }
    }

    /**
     *Method used to get the properties of a course
     * @param name Name of the course
     * @return a new Terrain
     */
    public static Terrain getTerrain(String name){
        Terrain input = null;
        try {
            FileInputStream fis = new FileInputStream(data_folder.path() + "/Terrains/" + name + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            input = (Terrain) ois.readObject();
        }
        catch (InvalidClassException | EOFException e){
            StateManager.reset();
            return getTerrain(name);
        }
        catch (ClassNotFoundException | IOException e){
            System.out.print(data_folder.path() + "/Terrains/" + name + ".ser");
            e.printStackTrace();
        }
        return input;
    }

    /**
     *Method used to contain all the different course names.
     * @return
     */
    public static Array<String> getCourseNames(){
        FileHandle[] children = data_folder.child("Courses").list();
        Array<String> childrenNames = new Array<>();
        for (FileHandle child: children) {
            childrenNames.add(child.nameWithoutExtension());
        }
        return childrenNames;
    }

    /**
     * Method used to write course properties into a file.
     * @param course The variable that designates the course.
     */
    public static void writeCourse(Course course){
        try{
            FileOutputStream fos = new FileOutputStream(data_folder.path() + "/Courses/" + course.getName() + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(course);
            oos.close();
        } catch (IOException e){
            System.out.print(data_folder.path() + "/Courses/" + course.getName() + ".ser");
            e.printStackTrace();
        }
    }

    /**
     *Method used to get the properties of a course
     * @param name Name of the course
     * @return a new Course
     */
    public static Course getCourse(String name){
        Course input = null;
        try {
            FileInputStream fis = new FileInputStream(data_folder.path() + "/Courses/" + name + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            input = (Course) ois.readObject();
        }
        catch (ClassNotFoundException | IOException e){
            System.out.print(data_folder.path() + "/Courses/" + name + ".ser");
            e.printStackTrace();
        }
        return input;
    }


    /**
     * Method used to write course properties into a file.
     * @param settings The variable that designates the course.
     */
    public static void writeSettings(Settings settings){
        try{
            FileOutputStream fos = new FileOutputStream(data_folder.path() + "/settings.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(settings);
            oos.close();
        } catch (IOException e){
            System.out.print(data_folder.path() + "/settings.ser");
            e.printStackTrace();
        }
    }


    /**
     *Method used to get the settings
     * @return a settings class
     */
    public static Settings getSettings() throws IOException{
        Settings input = null;
        try {
            FileInputStream fis = new FileInputStream(data_folder.path() + "/settings.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            input = (Settings) ois.readObject();
        }
        catch (ClassNotFoundException e){
            System.out.print(data_folder.path() + "/settings.ser");
            e.printStackTrace();
        }
        return input;
    }

}
