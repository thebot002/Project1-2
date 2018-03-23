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
         out.write(Integer.toString(course.courseWidth));
         out.newLine();
         out.write(Integer.toString(course.courseHeight));
         out.newLine();
         out.write(Double.toString(course.MU));
         out.newLine();
         for(int i = 0; i < course.start.length; i++) {
             out.write(Double.toString(course.start[i]));
             if(i<course.start.length-1){out.write(" ");}
         }
         out.newLine();
         for(int i = 0; i < course.goal.length; i++){
             out.write(Double.toString(course.goal[i]));
             if(i<course.goal.length-1){out.write(" ");}
         }
         out.newLine();
         out.write(Double.toString(course.tolerance));
         out.newLine();
         out.write(Double.toString(course.vMax));
         out.newLine();
         for(int i = 0; i < course.formula.length; i++)
         {
             out.write(course.formula[i]);
             out.write(" ");
         }
            out.close();
        }catch(IOException e){e.printStackTrace();}
    }
}

