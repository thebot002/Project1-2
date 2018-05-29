package com.golf2k18.models;

import java.io.Serializable;

/**
 *Class that details matrix operations (multiplication and transposition)that will be used for the splines
 */
public class Matrix implements Serializable {
    private int col_amount; //x,j
    private int row_amount; //y,i

    private float[][] matrix;

    public Matrix(float[][] matrix) {
        this.matrix = matrix;

        this.row_amount = matrix.length;
        this.col_amount = matrix[0].length;
    }

    /**
     *Creation of a matrix
     * @param row_amount the number of rows in the matrix
     * @param col_amount the number of columns in the matrix
     */
    public Matrix(int row_amount, int col_amount) {
        this.row_amount = row_amount;
        this.col_amount = col_amount;

        matrix = new float[row_amount][col_amount];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * Method that multiplies to matrices
     * @param a The first matrix, on the left
     * @param b The second matrix, on the right
     * @return The result of the multiplication
     */
    public static Matrix multiplication(Matrix a, Matrix b){
        if (a.col_amount != b.row_amount) {
            throw new IllegalArgumentException("A:Rows: " + a.col_amount + " did not match B:Columns " + b.row_amount + ".");
        }
        Matrix c = new Matrix(a.row_amount,b.col_amount);
        for (int i = 0; i < a.row_amount; i++) { // aRow
            for (int j = 0; j < b.col_amount; j++) { // bColumn
                for (int k = 0; k < a.col_amount; k++) { // aColumn
                    c.add(i,j,a.get(i,k) * b.get(k,j));
                }
            }
        }
        return c;
    }

    /**
     * Method that transposes a given matrix
     * @param a The matrix to be transposed
     */
    public static void transpose(Matrix a){
        float[][] temp = new float[a.col_amount][a.row_amount];
        for (int i = 0; i < a.row_amount; i++)
            for (int j = 0; j < a.col_amount; j++)
                temp[j][i] = a.get(i,j);
        a.matrix = temp;
        a.col_amount = temp.length;
        a.row_amount = temp[0].length;
    }

    public float get(int i, int j){
        return matrix[i][j];
    }

    public void add(int i, int j, float v){
        matrix[i][j] += v;
    }

    public float[][] getMatrix() {
        return matrix;
    }

    /**
     * Method that gives the final result of the multiplication or the transposition
     */
    public void print() {
        String result = "";
        for (int i = 0; i < row_amount; i++) {
            for (int j = 0; j < col_amount; j++) {
                result += String.valueOf(matrix[i][j]) + " ";
            }
            result += "\n";
        }
        System.out.print(result);
    }
}
