package com.golf2k18.io;

import com.golf2k18.objects.Course;

import java.io.Serializable;

public class CourseHighScore implements Serializable {
    int[][] scores; //i = terrain , i+1 = total, j = best scores
    int amount = 0;

    public CourseHighScore(Course course) {
        scores = new int[10][course.getSize()+1];
    }

    public boolean isHighScore(int[] score){
        if(amount<10) return true;
        int sum = 0;
        for (int i: score) {
            sum += i;
        }
        return sum <= scores[9][scores.length-1];
    }

    public void addHighScore(int[] score){
        int sum = 0;
        for (int i: score) {
            sum += i;
        }
        int i = 0;
        while(scores[i][scores.length-1] <= sum && i < 9){
            i++;
        }
        int[] temp;
        for (int j = i; j < 10; j++) {
            temp = scores[i];
            scores[i] = score;
            score = temp;
        }
    }

}
