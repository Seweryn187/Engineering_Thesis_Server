package com.server.response;

import java.sql.Timestamp;

public class FcsApiResponse {
    private String s;
    private float o;
    private float h;
    private float l;
    private float c;
    private float a;
    private float b;
    private float sp;
    private Timestamp t;

    public FcsApiResponse(String s, float o, float h, float l, float c, float a, float b, float sp, Timestamp t) {
        this.s = s;
        this.o = o;
        this.h = h;
        this.l = l;
        this.c = c;
        this.a = a;
        this.b = b;
        this.sp = sp;
        this.t = t;
    }

    public FcsApiResponse() {
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public float getO() {
        return o;
    }

    public void setO(float o) {
        this.o = o;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getSp() {
        return sp;
    }

    public void setSp(float sp) {
        this.sp = sp;
    }

    public Timestamp getT() {
        return t;
    }

    public void setT(Timestamp t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "FcsApiResponse{" +
                "s='" + s + '\'' +
                ", o=" + o +
                ", h=" + h +
                ", l=" + l +
                ", c=" + c +
                ", a=" + a +
                ", b=" + b +
                ", sp=" + sp +
                ", t=" + t +
                '}';
    }
}
