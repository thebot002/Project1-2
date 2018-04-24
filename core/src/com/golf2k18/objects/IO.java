package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class IO
{
    //TODO internal memory
    public static void exportCourse(Course course, String name)
    {
        String fileName = name + ".txt";
        FileHandle path = Gdx.files.external(fileName);
        BufferedWriter out;
        try{
         out = new BufferedWriter((path.writer(true)));
         out.write(Integer.toString(course.width));
         out.newLine();
         out.write(Integer.toString(course.height));
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
    public static Course importCourse(String path)
    {
        String[] str = {""};
        Course course = new Course(1,1,str);
        FileHandle importStream = Gdx.files.internal(path);
        BufferedReader in;
        try{
            in = new BufferedReader(importStream.reader());
            course.width = Integer.parseInt(in.readLine());
            course.height = Integer.parseInt(in.readLine());
            course.MU = Double.parseDouble(in.readLine());
            String start = in.readLine();
            Scanner scan = new Scanner(start);
            int i = 0;
            while (scan.hasNext())
            {
                course.start[i] = Double.parseDouble(scan.next());
                i++;
            }
            String goal = in.readLine();
            scan = new Scanner(goal);
            i = 0;
            while (scan.hasNext())
            {
                course.goal[i] = Double.parseDouble(scan.next());
                i++;
            }
            course.tolerance = Double.parseDouble(in.readLine());
            course.vMax = Double.parseDouble(in.readLine());
            i = 0;
            String formula = in.readLine();
            scan = new Scanner(formula);
            while (scan.hasNext())
            {
                course.formula[i] = scan.next();
                i++;
            }
            in.close();
        }catch(IOException e){ e.printStackTrace();}
        catch(NumberFormatException ex){ex.printStackTrace();}
        catch(Exception exc){exc.printStackTrace();}
        return course;
    }
}

