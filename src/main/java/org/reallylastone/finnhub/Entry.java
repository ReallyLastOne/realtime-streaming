package org.reallylastone.finnhub;

import java.io.Serializable;

public class Entry implements Serializable {
    private static final long serialVersionUID = -8757672990206523351L;
    private Object c;
    private double p;
    private String s;
    private long t;
    private double v;

    public Object getC() {
        return c;
    }

    public void setC(Object c) {
        this.c = c;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }
}
