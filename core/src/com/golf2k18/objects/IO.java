package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class IO
{
    public static void exportCourse(Course course, String name)
    {
        String fileName = name + ".txt";
        FileHandle path = Gdx.files.external(fileName);
        BufferedWriter out;
        try{
         out = new BufferedWriter((path.writer(true)));
         out.write(course.courseWidth);
         out.newLine();
         out.write(course.courseHeight);
         out.newLine();
         out.write(Double.toString(course.MU));
         out.newLine();
         out.write(course.goal.toString());
         out.newLine();
         out.write(course.goal.toString());
         out.newLine();
         out.write(Double.toString(course.tolerance));
         out.newLine();
         out.write(Double.toString(course.vMax));
         out.newLine();
         out.write(course.formula.toString());
            out.close();
        }catch(IOException e){e.printStackTrace();}
    }
}

