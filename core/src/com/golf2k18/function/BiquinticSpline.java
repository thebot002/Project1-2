package com.golf2k18.function;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;

import java.io.IOException;

public class BiquinticSpline extends Spline {

    private float[][] ma;
    private float[][] xxDeriv;
    private float[][] yyDeriv;
    private float[][] xxyDeriv;
    private float[][] xyyDeriv;
    private float[][] xxyyDeriv;

    public BiquinticSpline(float[][] data) {
        super(data);
        try {
            MatFileReader fr = new MatFileReader("Data/biquinticCoefficients.mat");
            MLArray m = fr.getContent().get("ans");
            MLDouble md;
            double[][] mt = new double[0][0];
            if(m instanceof MLDouble){
                md = (MLDouble) m;
                mt = md.getArray();
            }
            ma = new float[mt.length][mt[0].length];
            for (int i = 0; i < mt.length; i++) {
                for (int j = 0; j < mt[0].length; j++) {
                    ma[i][j] = (float) mt[i][j];
                }
            }
            xxDeriv = xDeriv(xDeriv);
            yyDeriv = yDeriv(yDeriv);
            xxyDeriv = yDeriv(xxDeriv);
            xyyDeriv = yDeriv(xyDeriv);
            xxyyDeriv = yDeriv(xxyDeriv);

            interpolate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void interpolate(){
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data[0].length - 1; j++) {
                float[][] fMatrix = {
                        {data[i][j]},
                        {data[i+1][j]},
                        {data[i][j+1]},
                        {data[i+1][j+1]},

                        {xDeriv[i][j]},
                        {xDeriv[i+1][j]},
                        {xDeriv[i][j+1]},
                        {xDeriv[i+1][j+1]},

                        {yDeriv[i][j]},
                        {yDeriv[i+1][j]},
                        {yDeriv[i][j+1]},
                        {yDeriv[i+1][j+1]},

                        {xyDeriv[i][j]},
                        {xyDeriv[i+1][j]},
                        {xyDeriv[i][j+1]},
                        {xyDeriv[i+1][j+1]},

                        {xxDeriv[i][j]},
                        {xxDeriv[i+1][j]},
                        {xxDeriv[i][j+1]},
                        {xxDeriv[i+1][j+1]},

                        {yyDeriv[i][j]},
                        {yyDeriv[i+1][j]},
                        {yyDeriv[i][j+1]},
                        {yyDeriv[i+1][j+1]},

                        {xxyDeriv[i][j]},
                        {xxyDeriv[i+1][j]},
                        {xxyDeriv[i][j+1]},
                        {xxyDeriv[i+1][j+1]},

                        {xyyDeriv[i][j]},
                        {xyyDeriv[i+1][j]},
                        {xyyDeriv[i][j+1]},
                        {xyyDeriv[i+1][j+1]},

                        {xxyyDeriv[i][j]},
                        {xxyyDeriv[i+1][j]},
                        {xxyyDeriv[i][j+1]},
                        {xxyyDeriv[i+1][j+1]}};
                coefficients[i][j] = Matrix.multiplication(new Matrix(ma),new Matrix(fMatrix));
                coefficients[i][j].toSquare();
            }
        }
    }

    @Override
    public float evaluateF(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        if(x >= coefficients.length) nx = 1;
        if(y >= coefficients[0].length) ny = 1;

        float[][] xVector = {{1,nx,(float)Math.pow(nx,2),(float)Math.pow(nx,3),(float)Math.pow(nx,4),(float)Math.pow(nx,5)}};
        float[][] yVector = {{1},{ny},{(float)Math.pow(ny,2)},{(float)Math.pow(ny,3)},{(float)Math.pow(ny,4)},{(float)Math.pow(ny,5)}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    @Override
    public float evaluateXDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{0,1,2*nx,(float)(3*Math.pow(nx,2)),(float)(4*Math.pow(nx,3)),(float)(5*Math.pow(nx,4))}};
        float[][] yVector = {{1},{ny},{(float)Math.pow(ny,2)},{(float)Math.pow(ny,3)},{(float)Math.pow(ny,4)},{(float)Math.pow(ny,5)}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    @Override
    public float evaluateYDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{1,nx,(float)Math.pow(nx,2),(float)Math.pow(nx,3),(float)Math.pow(nx,4),(float)Math.pow(nx,5)}};
        float[][] yVector = {{0},{1},{2*ny},{(float)(3*Math.pow(ny,2))},{(float)(4*Math.pow(ny,3))},{(float)(5*Math.pow(ny,4))}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }
}
