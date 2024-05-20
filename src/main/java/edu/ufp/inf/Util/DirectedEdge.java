package edu.ufp.inf.Util;

import java.io.Serializable;

public class DirectedEdge implements Serializable {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("Vertex names must be non-negative integers");
        } else if (w < 0) {
            throw new IllegalArgumentException("Vertex names must be non-negative integers");
        } else if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        } else {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }
    }

    public int from() {
        return this.v;
    }

    public int to() {
        return this.w;
    }

    public double weight() {
        return this.weight;
    }

    public String toString() {
        int var10000 = this.v;
        return "" + var10000 + "->" + this.w + " " + String.format("%5.2f", this.weight);
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(12, 34, 5.67);
    }
}
