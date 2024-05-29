package edu.ufp.inf.Graph;

import edu.princeton.cs.algs4.Digraph;
import edu.ufp.inf.Util.EdgeWeightedDigraph;
import edu.ufp.inf.Util.In;


import java.io.Serializable;

public class PGraph extends EdgeWeightedDigraph implements Serializable {

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
