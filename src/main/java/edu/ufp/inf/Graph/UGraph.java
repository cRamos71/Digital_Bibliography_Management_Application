package edu.ufp.inf.Graph;

import edu.princeton.cs.algs4.*;

public class UGraph extends edu.princeton.cs.algs4.Graph {

    public UGraph(int V) {
        super(V);
    }

    public UGraph(In in) {
        super(in);
    }

    public UGraph(edu.princeton.cs.algs4.Graph G) {
        super(G);
    }


    public boolean hasEdge(Integer id1, Integer id2){
        Iterable<Integer> adjacentVertices =  super.adj(id1);

        for(Integer id : adjacentVertices ){
            if(id.equals(id2)) {
                return true;
            }
        }
        return false;
    }

    public void dfs(int v, boolean[] visited) {
        visited[v] = true;
        for (int w : super.adj(v)) {
            if (!visited[w]) {
                dfs(w, visited);
            }
        }
    }

    /**
     * Check if all vertices are connected in a graph
     */
    public boolean isConnected() {
        int V = super.V();
        boolean[] visited = new boolean[V];

        dfs(0, visited);

        for (int v = 0; v < V; v++) {
            if (!visited[v]) {
                return false;
            }
        }
        return true;
    }



}
