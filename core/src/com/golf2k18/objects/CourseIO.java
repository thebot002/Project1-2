package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.*;

/**
 * Class that handles importing and exporting courses from and to a file
 */
public class CourseIO {
    private static final FileHandle course_folder = Gdx.files.internal("Data/Courses");


    public static Array<String> getCoursesNames(){
        FileHandle[] children = course_folder.list();
        Array<String> childrenNames = new Array<String>();
        for (FileHandle child: children) {
            childrenNames.add(child.nameWithoutExtension());
        }
        return childrenNames;
    }

    /**
     * Write a terrain to a file
     * @param terrain variable that describes the course
     */
    public static void writeFile(Terrain terrain){
        try{
            FileOutputStream fos = new FileOutputStream(course_folder.path() + "/" + terrain.getName() + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(terrain);
            oos.close();
        } catch (IOException e){
            System.out.print(course_folder.path() + "/" + terrain.getName() + ".ser");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public static Terrain getCourse(String name){
        Terrain input = null;
        try {
            FileInputStream fis = new FileInputStream(course_folder.path() + "/" + name + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            input = (Terrain) ois.readObject();
        }
        catch (ClassNotFoundException | IOException e){
            System.out.print(course_folder.path() + "/" + name + ".ser");
            e.printStackTrace();
        }

        return input;
    }
}
