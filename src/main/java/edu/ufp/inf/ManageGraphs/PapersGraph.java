package edu.ufp.inf.ManageGraphs;
import edu.princeton.cs.algs4.*;
import edu.ufp.inf.Graph.PGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.paper_author.PaperConference;
import edu.ufp.inf.paper_author.PaperJournal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PapersGraph<P extends Paper> {

    private EdgeWeightedDigraph papersPGraph;
    private HashMap<Integer, P> papersMap = new HashMap<>();
    private Integer ids = 0;


    public PapersGraph(PGraph papersPGraph) {
        this.papersPGraph = papersPGraph;
    }

    public PapersGraph(PGraph papersPGraph, HashMap<Integer, P> hashPapers) {
        this.papersPGraph = papersPGraph;
        for (Integer k : hashPapers.keySet()){
            int id = this.ids++;
            hashPapers.get(k).setGraphId(id);
            this.papersMap.put(id, hashPapers.get(k));
        }
    }

    public void listPapersJournalAndTime(Date start, Date end){
        for (Integer id : papersMap.keySet()){
            if(papersMap.get(id) instanceof PaperJournal ) {
                if (papersMap.get(id).getDate().isBefore(end) && papersMap.get(id).getDate().isAfter(start)) {
                    System.out.println(id);
                }
            }
        }
    }

    public void listPapersConferenceTime(Date start, Date end){

        for (Integer id : papersMap.keySet()){
                if( papersMap.get(id) instanceof PaperConference) {
                    System.out.println("olaolaoa");
                    if (papersMap.get(id).getDate().isBefore(end) && papersMap.get(id).getDate().isAfter(start)) {
                        System.out.println(id);
                    }
                }
        }
    }

    public int calculateFirstOrderQuotes(P paper){
        if (paper.getGraphId() == -1) return 0;
        return papersPGraph.indegree(paper.getGraphId());
    }

    public int calculateSecondOrderQuotes(P paper){
        if (paper.getGraphId() == -1) return 0;
        return calculateFirstOrderQuotes(paper) + caculateSecondOrderVertexes(paper.getGraphId());
    }

    public int caculateSecondOrderVertexes(Integer root){
        int returnCount = 0;
        for (Integer keyMap : this.papersMap.keySet()){
            for (DirectedEdge w : this.papersPGraph.adj(keyMap)){
                if (w.to() == root){
                    returnCount += calculateFirstOrderQuotes(this.papersMap.get(keyMap));
                }
            }
        }
        return returnCount;
    }

    public int selfQuotes(P paper){
        int key =paper.getGraphId();
        if (key == -1) return 0;
        int selfQuoteCounter = 0;

        for (DirectedEdge edge : this.papersPGraph.adj(key)){
            if (papersMap.get(edge.to()).getAuthors().equals(paper.getAuthors())){
                selfQuoteCounter++;
            }
        }
        return selfQuoteCounter;
    }


    private int paperMapKeyFinder(P paper){
        for (Integer key : this.papersMap.keySet()){
            if (this.papersMap.get(key).equals(paper)){
                return key;
            }
        }
        return -1;
    }


    // Peso, diferença entre artigos
    public Iterable<Integer> dijkstraShortestPath(P paperStart, P paperEnd) {
        Integer keyPaperS = paperStart.getGraphId();
        Integer keyPaperE = paperEnd.getGraphId();

        int n = this.papersPGraph.V(); // Number of vertices
        double[] distances = new double[n];
        int[] edgeTo = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[keyPaperS] = 0;

        // Priority queue to store vertices based on their distances
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(keyPaperS);

        while (!pq.isEmpty()) {
            int u = pq.poll();

            // Skip vertex if already visited
            if (visited[u]) continue;


            visited[u] = true;

            // Update distances to adjacent vertices
            for (DirectedEdge edge : this.papersPGraph.adj(u)) {
                int v = edge.to();
                double weight = edge.weight();
                if (!visited[v]) {
                    double newDist = distances[u] + weight;
                    if (newDist < distances[v]) {
                        distances[v] = newDist;
                        edgeTo[v] = u;
                        pq.offer(v);
                    }
                }
            }
        }

        // Reconstruct the shortest path from paperStart to paperEnd
        Stack<Integer> path = new Stack<>();
        int[] edgeToTest = new int[n];
        if (Arrays.equals(edgeTo, edgeToTest)) {
            return null;
        }
        for (int v = keyPaperE; v != keyPaperS; v = edgeTo[v]) {
                path.push(v);
        }
            path.push(keyPaperS); // Add the start paper to the path

       // nao pode ser usado com pesos negativos
        return path;
    }


    public void addEdge(P p1, P p2){
        addHash(p1);
        addHash(p2);
        double weight = Math.abs(weightCalc(p1) - weightCalc(p2));
        System.out.println(weight);

        this.papersPGraph.addEdge(new DirectedEdge(p1.getGraphId(),p2.getGraphId(),weight));
    }

    public void addHash(P p){
        if(p.getGraphId() == -1){
            Integer id1 = this.ids++;
            p.setGraphId(id1);
            System.out.println("I got in!");
            this.papersMap.put(id1, p);
        }
    }

    private double weightCalc(P paper){
        return (double) ((paper.getDate().day() + paper.getDate().month() + paper.getDate().year()));
    }

     //grafo vazio , 1 verificar quantos existem conference e depois criar o grafo
    public void writeGraphToFile(String fn){
        Out fp = new Out(fn);
        fp.println(this.papersPGraph.V());
        fp.println(this.papersPGraph.E());
        for(int i = 0; i < this.papersPGraph.V(); i++){
            for (DirectedEdge id : this.papersPGraph.adj(i)){
              fp.println(id.from() +  " " + id.to() + " " + id.weight());
            }
        }
    }


    public void readGraphFromFile(String fn){
        In fp = new In(fn);
        int vertex = fp.readInt();
        int edges = fp.readInt();

        for (int i = 0; i < edges; i++){
            this.papersPGraph.addEdge(new DirectedEdge(fp.readInt(), fp.readInt(), fp.readDouble()));
        }
        //System.out.println(papersPGraph);
    }


    public static void main(String[] args) {
        PGraph pg = new PGraph(new In("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/test1.txt"));
        HashMap<Integer, Paper> m = new HashMap<>();
        Author a1 = new Author();
        a1.setPenName("olaoao");
        a1.setAffiliation("PT");
        a1.setIdNumber(0);
        Author a2 = new Author();
        a2.setPenName("mundodd");
        a2.setAffiliation("PT");
        a2.setIdNumber(1);
        Paper p1 = new PaperConference();
        Paper p2 = new PaperJournal();
        Paper p3 = new PaperConference();
        Paper p4 = new PaperConference();


        for(int i= 0; i < 6 ; i++){
            System.out.println(i);
            if(i % 2 == 0){
                p1.setDate(new Date(10, 1 + i, 2000));
                p1.addAuthor(a1);
                m.put(i, p1);
            }else{
                p2.addAuthor(a1);
                p2.setDate(new Date(10, 1 + i, 2000));
                m.put(i, p2);

            }
        }


        p3.setDate(new Date(10,2, 1900));
        p3.addAuthor(a2);
        p4.setDate(new Date(6,2, 1904));
        p4.addAuthor(a2);
        PapersGraph pa = new PapersGraph(pg, m);
        pa.addEdge(p1, p3);
        pa.addEdge(p1, p4);



        PGraph pag = new PGraph(8);
        HashMap<Integer, Paper> hm = new HashMap<>();
        PapersGraph pG = new PapersGraph(pag, hm);
        //pa.writeGraphToFile("/Users/claudio/Downloads/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/test1.txt");
        pG.readGraphFromFile("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/test1.txt");


        /* System.out.println(pa.paperMapKeyFinder(p1));
        System.out.println(pa.paperMapKeyFinder(p3));
        Iterable<Integer> p =pa.dijkstraShortestPath(p1, p3);
if(p != null) {
    for (Object a : p) {
        System.out.println((Integer) a);
    }
}*/
        //pa.listPapersConferenceTime(new Date(10, 1, 2000), new Date(12, 1, 2024));
        //System.out.println(pa.selfQuotes(p1));

    }

}
