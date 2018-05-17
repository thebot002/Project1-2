package com.golf2k18.objects;

public class Matrix {
    private int col_amount;
    private int row_amount;

    private float[][] matrix;

    public Matrix(float[][] matrix) {
        this.matrix = matrix;

        this.row_amount = matrix.length;
        this.col_amount = matrix[0].length;
    }

    public Matrix(int col_amount, int row_amount) {
        this.col_amount = col_amount;
        this.row_amount = row_amount;

        matrix = new float[row_amount][col_amount];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

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

    public static void transpose(Matrix a){
        float[][] temp = new float[a.col_amount][a.row_amount];
        for (int i = 0; i < a.row_amount; i++)
            for (int j = 0; j < a.col_amount; j++)
                temp[j][i] = a.get(i,j);
        a.matrix = temp;
        a.col_amount = temp.length;
        a.row_amount = temp[0].length;
    }

    public float get(int x, int y){
        return matrix[x][y];
    }

    public void add(int x, int y, float v){
        matrix[x][y] += v;
    }

    public float[][] getMatrix() {
        return matrix;
    }

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
