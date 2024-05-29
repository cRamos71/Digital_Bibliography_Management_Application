package edu.ufp.inf.Graph;

import edu.ufp.inf.Util.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an undirected graph.
 */
public class UGraph extends Graph implements Serializable {


    public UGraph(int V) {
        super(V);
    }

    public UGraph(In in) {
        super(in);
    }

    public UGraph(Graph G) {
        super(G);
    }

    /**
     * Checks if there is an edge between two vertices.
     *Time Complexity: O(V), where V is the number of vertices in the graph.
     * It iterates over the adjacent vertices of one vertex to check if there is an edge to another vertex.
     * Extra Space: O(1). The additional space used is constant, as it only stores references to adjacent vertices temporarily.
     * @param id1 The ID of the first vertex.
     * @param id2 The ID of the second vertex.
     * @return True if there is an edge between the vertices, false otherwise.
     */
    public boolean hasEdge(Integer id1, Integer id2){
        Iterable<Integer> adjacentVertices =  super.adj(id1);

        for(Integer id : adjacentVertices ){
            if(id.equals(id2)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Performs a depth-first search (DFS) on the graph.
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It performs a depth-first search traversal of the graph.
     * Extra Space: O(V). The additional space is used to store the visited array, which tracks visited vertices during the DFS traversal.
     * @param v       The vertex to start the search from.
     * @param visited An array to keep track of visited vertices.
     */
    public void dfs(int v, boolean[] visited) {
        visited[v] = true;
        for (int w : super.adj(v)) {
            if (!visited[w]) {
                dfs(w, visited);
            }
        }
    }

    /**
     * Checks if the graph is connected.
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It calls the dfs method to perform a depth-first search traversal.
     * Extra Space: O(V). Similar to dfs, it uses extra space for the visited array.
     * @return True if the graph is connected, false otherwise.
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

    /**
     * Checks if there is a connection between all vertex of the graph
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It performs a depth-first search traversal.
     * Extra Space: O(V). It uses extra space for the visited array.
     * @return True if it has, false otherwise.
     */
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


    /**
     * Method to get a set of vertices in the graph
     * @return Set of integers (vertices)
     */
    private Set<Integer> vertexSet(){
        Set<Integer> set = new HashSet<>();
        for(int i = 0 ; i < super.V(); i++){
            set.add(i);
        }
        return set;
    }

    /**
     * Checks if there is a connection between all vertex of the graph
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It performs a depth-first search traversal.
     * Extra Space: O(V). It uses extra space for the visited array.
     * @return True if it has, false otherwise.
     */
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

    /**
     * Finds the minimum number of hops (edges) between two vertices in the graph.
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It performs a breadth-first search traversal to find the minimum number of hops between two vertices.
     * Extra Space: O(V). It uses additional space for the boolean array marked[], the edgeTo[] array, and the distTo[] array.
     * @param orig The ID of the origin vertex.
     * @param dest The ID of the destination vertex.
     * @return The minimum number of hops between the vertices, or -1 if there is no path between them.
     */
    public Integer minimumHopsBetween(int orig, int dest){
        boolean marked[] = new boolean[super.V()];
        int edgeTo[] = new int[super.V()];
        int distTo[] = new int[super.V()];

        Queue<Integer> q = new Queue<Integer>();
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
                //if it got to dest
                if(w == dest){
                    return distTo[w];
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        Graph g1 = new Graph(new In("./data/graphE.txt"));
        UGraph ug = new UGraph(g1);

        System.out.println(ug.minimumHopsBetween(0, 3));
        System.out.println(ug.isConexo());
        System.out.println(ug.isConexov2());
    }

}
