package edu.ufp.inf.Graph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;

public class PGraph extends EdgeWeightedDigraph {

    public PGraph(int V){
        super(V);
    }

    public PGraph(In in) {
        super(in);
    }

    public PGraph(EdgeWeightedDigraph G) {
        super(G);
    }


}
