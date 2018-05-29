package com.golf2k18.function;

/**
 * This interface gives a general function's properties
 */
public interface Function {
    float evaluateF(float x, float y);
    float evaluateXDeriv(float x,float y);
    float evaluateYDeriv(float x,float y);
}
