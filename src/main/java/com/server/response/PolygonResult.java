package com.server.response;

import java.sql.Timestamp;

public class PolygonResult {
    private float h;
    private float l;
    private Timestamp t;

    public PolygonResult() {
    }

    public PolygonResult(float h, float l, Timestamp t) {
        this.h = h;
        this.l = l;
        this.t = t;
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

    public Timestamp getT() {
        return t;
    }

    public void setT(Timestamp t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "PolygonResult{" +
                "h=" + h +
                ", l=" + l +
                ", t=" + t +
                '}';
    }
}
