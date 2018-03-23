package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class IO
{
    public static void exportCourse(Course course, String name)
    {
        String fileName = "Course-" + name + ".txt";
        //FileHandle Export = Gdx.files.external(fileName);
        FileHandle save = Gdx.files.local("Saves/" + fileName);
        save.writeString(Integer.toString(course.courseWidth),false);
        save.writeString(Integer.toString(course.courseHeight),true);

    }
}
