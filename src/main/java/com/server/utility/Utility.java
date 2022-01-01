package com.server.utility;

public class Utility {

    public static int castFloatToInt(float value) {
        return (int) (value * 1000);
    }

    public static int calculateSpread(int askValue, int bidValue, int meanValue) {
        return (((askValue - bidValue) * 10000)/meanValue);
    }
}
