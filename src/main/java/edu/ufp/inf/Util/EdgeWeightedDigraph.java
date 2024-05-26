package edu.ufp.inf.Util;

import edu.ufp.inf.Util.*;


import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EdgeWeightedDigraph implements Serializable {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    private int[] indegree;

    public EdgeWeightedDigraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
        } else {
            this.V = V;
            this.E = 0;
            this.indegree = new int[V];
            this.adj = new Bag[V];

            for(int v = 0; v < V; ++v) {
                this.adj[v] = new Bag();
            }

        }
    }

    public EdgeWeightedDigraph(int V, int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges in a Digraph must be non-negative");
        } else {
            for(int i = 0; i < E; ++i) {
                int v = StdRandom.uniformInt(V);
                int w = StdRandom.uniformInt(V);
                double weight = 0.01 * (double)StdRandom.uniformInt(100);
                DirectedEdge e = new DirectedEdge(v, w, weight);
                this.addEdge(e);
            }

        }
    }

    public EdgeWeightedDigraph(In in) {
        if (in == null) {
            throw new IllegalArgumentException("argument is null");
        } else {
            try {
                this.V = in.readInt();
                if (this.V < 0) {
                    throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");
                } else {
                    this.indegree = new int[this.V];
                    this.adj = new Bag[this.V];

                    int E;
                    for(E = 0; E < this.V; ++E) {
                        this.adj[E] = new Bag();
                    }

                    E = in.readInt();
                    if (E < 0) {
                        throw new IllegalArgumentException("Number of edges must be non-negative");
                    } else {
                        for(int i = 0; i < E; ++i) {
                            int v = in.readInt();
                            int w = in.readInt();
                            this.validateVertex(v);
                            this.validateVertex(w);
                            double weight = in.readDouble();
                            this.addEdge(new DirectedEdge(v, w, weight));
                        }

                    }
                }
            } catch (NoSuchElementException var8) {
                throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", var8);
            }
        }
    }

    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();

        int v;
        for(v = 0; v < G.V(); ++v) {
            this.indegree[v] = G.indegree(v);
        }

        for(v = 0; v < G.V(); ++v) {
            Stack<DirectedEdge> reverse = new Stack();
            Iterator var4 = G.adj[v].iterator();

            DirectedEdge e;
            while(var4.hasNext()) {
                e = (DirectedEdge)var4.next();
                reverse.push(e);
            }

            var4 = reverse.iterator();

            while(var4.hasNext()) {
                e = (DirectedEdge)var4.next();
                this.adj[v].add(e);
            }
        }

    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.V - 1));
        }
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        this.validateVertex(v);
        this.validateVertex(w);
        this.adj[v].add(e);
        int var10002 = this.indegree[w]++;
        ++this.E;
    }

    public Iterable<DirectedEdge> adj(int v) {
        this.validateVertex(v);
        return this.adj[v];
    }

    public int outdegree(int v) {
        this.validateVertex(v);
        return this.adj[v].size();
    }

    public int indegree(int v) {
        this.validateVertex(v);
        return this.indegree[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag();

        for(int v = 0; v < this.V; ++v) {
            Iterator var3 = this.adj(v).iterator();

            while(var3.hasNext()) {
                DirectedEdge e = (DirectedEdge)var3.next();
                list.add(e);
            }
        }

        return list;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.V + " " + this.E + NEWLINE);

        for(int v = 0; v < this.V; ++v) {
            s.append("" + v + ": ");
            Iterator var3 = this.adj[v].iterator();

            while(var3.hasNext()) {
                DirectedEdge e = (DirectedEdge)var3.next();
                s.append("" + e + "  ");
            }

            s.append(NEWLINE);
        }

        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);
    }
}
