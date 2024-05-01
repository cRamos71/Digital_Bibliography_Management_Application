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

    public void dfsConnected(int v, boolean[] visited, int[] numVisited) {
        visited[v] = true;
        numVisited[0]++;
        for (int neighbor : super.adj(v)) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited);
            }
        }
    }

    public boolean isConexo() {
        boolean[] visited = new boolean[super.V()];
        int num[] = new int[1];
        num[0] = 1;
        dfsConnected(0, visited, num);

        return num[0] == this.vertexSet().size();
    }

    public Set<Integer> vertexSet(){
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

    public static void main(String[] args) {

    }



}
