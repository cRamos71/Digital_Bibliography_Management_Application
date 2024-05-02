package edu.ufp.inf.Graph;

import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UGraph extends Graph {

    public UGraph(int V) {
        super(V);
    }

    public UGraph(In in) {
        super(in);
    }

    public UGraph(Graph G) {
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

    public boolean isConexo() {
        int V = super.V();
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);
        int count = 0;

        for (int v = 0; v < V; v++) {
            if (!visited[v]) {
                dfs(v, visited);
                count++;
            }
        }
        return count == 1;
    }



    private Set<Integer> vertexSet(){
        Set<Integer> set = new HashSet<>();
        for(int i = 0 ; i < super.V(); i++){
            set.add(i);
        }
        return set;
    }

    public boolean isConexov2() {
        boolean[] visited = new boolean[super.V()];
        Arrays.fill(visited, false);

        dfs(0, visited);

        // Check if all vertices have been visited
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    public Integer minimumHopsBetween(int orig, int dest){
        boolean marked[] = new boolean[super.V()];
        int edgeTo[] = new int[super.V()];
        int distTo[] = new int[super.V()];

        Queue<Integer> q = new Queue<>();
        q.enqueue(orig);
        marked[orig] = true;
        distTo[orig] = 0;
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : super.adj(v)) {
                if (!marked[w]) {
                    q.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
                if(w == dest){
                    return distTo[w];
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        Graph g1 = new Graph(new In("/.../.../Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/graphE.txt"));
        UGraph ug = new UGraph(g1);

        System.out.println(ug.minimumHopsBetween(0, 3));
        System.out.println(ug.isConexo());
        System.out.println(ug.isConexov2());
    }

}
