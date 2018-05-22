package com.golf2k18.objects;

import org.omg.CORBA.MARSHAL;

import java.io.Serializable;

public class Spline implements Function, Serializable {
    private float[][] data;
    private Matrix[][] coefficients;

    private float[][] xDeriv;
    private float[][] yDeriv;
    private float[][] xyDeriv;

    private float[][] A1 = {{1,0,0,0},{0,0,1,0},{-3,3,-2,-1},{2,-2,1,1}};
    private float[][] A2 = {{1,0,-3,2},{0,0,3,-2},{0,1,-2,1},{0,0,-1,1}};

    public Spline(float[][] data) {
        this.data = data;
        coefficients = new Matrix[data.length-1][data[0].length-1];

        xDeriv = new float[data.length][data[0].length];
        yDeriv = new float[data.length][data[0].length];
        xyDeriv = new float[data.length][data[0].length];

        //init of derivatives
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length-1; j++) {
                if(i == 1) yDeriv[i][j] = (data[2][j] - data[0][j])/2;
                if(i == data.length - 1) yDeriv[i][j] = (data[data.length-3][j] - data[data.length-1][j])/2;
                yDeriv[i][j] = (data[i+1][j] - data[i-1][j])/2;

                if(j == 1) xDeriv[i][j] = (data[i][2] - data[i][0])/2;
                if(j == data[0].length - 1) xDeriv[i][j] = (data[i][data[0].length-3] - data[i][data[0].length-1])/2;
                xDeriv[i][j] = (data[i][j+1] - data[i][j-1])/2;
            }
        }
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length-1; j++) {
                if(i == 1) xyDeriv[i][j] = (yDeriv[i][2] - yDeriv[i][0])/2;
                if(i == data.length - 1) xyDeriv[i][j] = (yDeriv[data.length-3][j] - yDeriv[data.length-1][j])/2;
                xyDeriv[i][j] = (yDeriv[i][j+1] - yDeriv[i][j-1])/2;
            }
        }
        interpolate();
    }

    private void interpolate(){
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data[0].length - 1; j++) {
                float[][] fMatrix = {
                        {data[i][j],data[i][j+1],yDeriv[i][j],yDeriv[i][j+1]},
                        {data[i+1][j],data[i+1][j+1],yDeriv[i+1][j],yDeriv[i+1][j+1]},
                        {xDeriv[i][j],xDeriv[i][j+1],xyDeriv[i][j],xyDeriv[i][j+1]},
                        {xDeriv[i+1][j],xDeriv[i+1][j+1],xyDeriv[i+1][j],xyDeriv[i+1][j+1]}};
                coefficients[i][j] = Matrix.multiplication(new Matrix(A1),Matrix.multiplication(new Matrix(fMatrix),new Matrix(A2)));

                //float[][] xV = {{1,i,(float)Math.pow(i,2),(float)Math.pow(i,3)}};
                //float[][] yV = {{1},{j},{(float)Math.pow(j,2)},{(float)Math.pow(j,3)}};
                /*float result = Matrix.multiplication(new Matrix(xV),Matrix.multiplication(coefficients[i][j],new Matrix(yV))).get(0,0);
                if(result != data[i][j]){
                    //new Matrix(fMatrix).print();
                    /*System.out.println("x");
                    new Matrix(xV).print();
                    System.out.println("y");
                    new Matrix(yV).print();
                    System.out.println("coefs");
                    coefficients[i][j].print();
                    System.out.println();
                    System.out.println(result);
                }*/
            }
        }
    }

    @Override
    public float evaluateF(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{1,nx,(float)Math.pow(nx,2),(float)Math.pow(nx,3)}};
        float[][] yVector = {{1},{ny},{(float)Math.pow(ny,2)},{(float)Math.pow(ny,3)}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    @Override
    public float evaluateXDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{0,1,2*nx,(float)(3*Math.pow(nx,2))}};
        float[][] yVector = {{1},{ny},{(float)Math.pow(ny,2)},{(float)Math.pow(ny,3)}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    @Override
    public float evaluateYDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{1,nx,(float)Math.pow(nx,2),(float)Math.pow(nx,3)}};
        float[][] yVector = {{0},{1},{2*ny},{(float)(3*Math.pow(ny,2))}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    public float evaluateXYDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{0,1,2*nx,(float)(3*Math.pow(nx,2))}};
        float[][] yVector = {{0},{1},{2*ny},{(float)(3*Math.pow(ny,2))}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    private float evaluate(Matrix xVector, Matrix yVector, float x, float y){
        if(x > coefficients.length-1) x = coefficients.length-1;
        if(y > coefficients[0].length-1) y = coefficients[0].length-1;
        Matrix result = Matrix.multiplication(Matrix.multiplication(xVector,coefficients[(int)x][(int)y]),yVector);
        if(result.get(0,0) > 1 ) System.out.println(" res:"+result.get(0,0));
        return result.get(0,0);
    }
}
