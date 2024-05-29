package edu.ufp.inf.ManageGraphs;
import edu.princeton.cs.algs4.*;
import edu.ufp.inf.Util.DirectedEdge;
import edu.ufp.inf.Util.In;
import edu.ufp.inf.Util.EdgeWeightedDigraph;
import edu.ufp.inf.Util.Date;
import edu.princeton.cs.algs4.Stack;
import edu.ufp.inf.Graph.PGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.paper_author.PaperConference;
import edu.ufp.inf.paper_author.PaperJournal;

import java.io.*;
import java.util.*;

/**
 * A graph representation for papers using an edge-weighted directed graph.
 *
 * @param <P> The type of Paper objects stored in the graph.
 */
public class PapersGraph<P extends Paper> implements Serializable{
    /**
     * Edge Weighted Digraph
     */
    private EdgeWeightedDigraph papersPGraph;
    /**
     * Map  vertex id to a Paper
     */
    private HashMap<Integer, P> papersMap = new HashMap<>();
    /**
     * Var to increment and assign to each paper added to the graph
     */
    private Integer ids = 0;

    public PapersGraph() {
    }
    public PapersGraph(PGraph papersPGraph) {
        this.papersPGraph = papersPGraph;
    }

    public PapersGraph(EdgeWeightedDigraph papersPGraph, HashMap<Integer, P> hashPapers) {
        this.papersPGraph = papersPGraph;
        for (Integer k : hashPapers.keySet()){
            int id = this.ids++;
            hashPapers.get(k).setGraphId(id);
            this.papersMap.put(id, hashPapers.get(k));
        }
    }

    public PapersGraph(String fn){
        readPapersGraphTxt(fn);
    }

    public void setPapersPGraph(EdgeWeightedDigraph papersPGraph) {
        this.papersPGraph = papersPGraph;
    }

    public void setPapersMap(HashMap<Integer, P> papersMap) {
        this.papersMap = papersMap;
    }

    public EdgeWeightedDigraph getPapersPGraph() {
        return papersPGraph;
    }

    public HashMap<Integer, P> getPapersMap() {
        return papersMap;
    }

    public Integer getIds() {
        return ids;
    }

    /**
     * Lists the titles of papers that are instances of PaperJournal and whose dates fall within the specified time range.
     * Time Complexity: O(n), where n is the number of entries in papersMap.
     * Iterates over each entry in the papersMap to check if it is an instance of PaperJournal and if its date add to result
     * Space Complexity: O(k), where k is the number of papers that are instances of PaperJournal and are within the specified date range.
     * @param start The start date of the time range.
     * @param end   The end date of the time range.
     * @return An ArrayList containing the titles of the papers that are of type PaperJournal and fall within the specified time range.
     */
    public ArrayList<String> listPapersJournalAndTime(Date start, Date end){
        ArrayList<String> result = new ArrayList<>();
        for (Integer id : papersMap.keySet()){
            if(papersMap.get(id) instanceof PaperJournal) {
                if (papersMap.get(id).getDate().isBefore(end) && papersMap.get(id).getDate().isAfter(start)) {
                    //System.out.println(papersMap.get(id));
                    result.add(papersMap.get(id) + "");
                }
            }
        }
        return result;
    }

    /**
     * Lists the titles of papers that are instances of PaperJournal and whose dates fall within the specified time range and output to txt.
     * Time Complexity: O(n), where n is the number of entries in papersMap.
     * Iterates over each entry in the papersMap to check if it is an instance of PaperJournal and if its date add to result
     * Space Complexity: O(k), where k is the number of papers that are instances of PaperJournal and are within the specified date range.
     * @param start The start date of the time range.
     * @param end   The end date of the time range.
     * @param fn    file name
     */
    public void listPapersJournalAndTime(Date start, Date end, String fn){
       ArrayList<String> a = listPapersJournalAndTime(start, end);
       writeTotxt(a, fn);
    }

    /**
     * Write result ArrList<String> to txt
     * @param arr ArrayList of Strings to write
     * @param fn filename
     */
    private void writeTotxt(ArrayList<String> arr, String fn){
        try{
            File fp = new File(fn);
            FileWriter fw = new FileWriter(fp);

            for (String s : arr){
                fw.write(s + "\n");
            }
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Lists the titles of papers that are instances of Conference and whose dates fall within the specified time range.
     * Time Complexity: O(n), where n is the number of entries in papersMap.
     * Iterates over each entry in the papersMap to check if it is an instance of Conference and if its date add to result
     * Space Complexity: O(k), where k is the number of papers that are instances of Conference and are within the specified date range.
     * @param start The start date of the time range.
     * @param end   The end date of the time range.
     * @return An ArrayList containing the titles of the papers that are of type Conference and fall within the specified time range.
     */
    public ArrayList<String> listPapersConferenceTime(Date start, Date end){
        ArrayList<String> r = new ArrayList<>();
        for (Integer id : papersMap.keySet()){
                if( papersMap.get(id) instanceof PaperConference) {
                    if (papersMap.get(id).getDate().isBefore(end) && papersMap.get(id).getDate().isAfter(start)) {
                        //System.out.println(papersMap.get(id));
                        r.add(papersMap.get(id) + " ");
                    }
                }
        }
        return  r;
    }

    /**
     * Lists the titles of papers that are instances of Conference and whose dates fall within the specified time range and output to txt.
     * Time Complexity: O(n), where n is the number of entries in papersMap.
     * Iterates over each entry in the papersMap to check if it is an instance of Conference and if its date add to result
     * Space Complexity: O(k), where k is the number of papers that are instances of Conference and are within the specified date range.
     * @param start The start date of the time range.
     * @param end   The end date of the time range.
     * @param fn    file name
     */
    public void listPapersConferenceTime(Date start, Date end, String fn){
        ArrayList<String> a = listPapersConferenceTime(start, end);
        writeTotxt(a, fn);
    }

    /**
     * Write result to txt
     * @param i integer to write
     * @param fn filename
     */
    private void writeTotxt(int i, String fn){
        try{
            File fp = new File(fn);
            FileWriter fw = new FileWriter(fp);
            fw.write(i + "\n");
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Calculates the first-order quotes (indegree) for a given paper.
     * The first-order quotes represent the number of papers that cite this paper.
     * Time complexity: O(1) adjacency list
     * ExtraSpace: O(1)
     * @param paper The paper for which the first-order quotes are to be calculated.
     * @return The number of first-order quotes (indegree) of the given paper. If the paper is not part of the graph (indicated by a graphId of -1), it returns 0.
     */
    public int calculateFirstOrderQuotes(P paper){
        if (paper.getGraphId() == -1) return 0;
        return papersPGraph.indegree(paper.getGraphId());
    }

    /**
     * Calculates the first-order quotes (indegree) for a given paper and output to a txt file.
     * The first-order quotes represent the number of papers that cite this paper.
     * Time complexity: O(1) adjacency list
     * ExtraSpace: O(1)
     * @param paper The paper for which the first-order quotes are to be calculated.
     * @param fn   file name
     */
    public void calculateFirstOrderQuotes(P paper, String fn){
       int i = calculateFirstOrderQuotes(paper);
       writeTotxt(i, fn);
    }

    /**
     * Calculates the second-order quotes for a given paper.
     * The second-order quotes represent the number of papers that directly cite this paper (first-order quotes)
     * plus the number of papers that cite those citing papers (second-order vertexes).
     * Time Complexity: O(V + E)
     * Extra Space: O(1)
     * @param paper The paper for which the second-order quotes are to be calculated.
     * @return The number of second-order quotes of the given paper. If the paper is not part of the graph (indicated by a graphId of -1), it returns 0.
     */
    public int calculateSecondOrderQuotes(P paper){
        if (paper.getGraphId() == -1) return 0;
        return calculateFirstOrderQuotes(paper) + caculateSecondOrderVertexes(paper.getGraphId());
    }

    /**
     * Calculates the second-order quotes for a given paper and output to a txt file.
     * The second-order quotes represent the number of papers that directly cite this paper (first-order quotes)
     * plus the number of papers that cite those citing papers (second-order vertexes).
     * Time Complexity: O(V + E)
     * Extra Space: O(1)
     * @param paper The paper for which the second-order quotes are to be calculated.
     * @param fn file name
     */
    public void calculateSecondOrderQuotes(P paper, String fn){
       int i = calculateSecondOrderQuotes(paper);
       writeTotxt(i, fn);
    }

    /**
     * Calculate the number of second-order vertexes for a given root paper ID.
     * The second-order vertexes are those papers that cite the papers which directly cite the root paper.
     * This method iterates over all vertices in the papersMap (O(V)) and for each vertex, it iterates over its adjacent vertices (O(E)).
     * For each adjacent vertex, it checks if it points to the root and then calls calculateFirstOrderQuotes, which is O(1).
     * Time complexity: O(V + E).
     * Space Complexity: O(1)
     * @param root The graph ID of the root paper.
     * @return The number of second-order vertexes for the root paper.
     */
    private int caculateSecondOrderVertexes(Integer root){
        int returnCount = 0;
        // for each vertex
        for (Integer keyMap : this.papersMap.keySet()){
            // for each edge
            for (DirectedEdge w : this.papersPGraph.adj(keyMap)){
                // if we are in a vertex that points
                if (w.to() == root){
                    returnCount += calculateFirstOrderQuotes(this.papersMap.get(keyMap));
                }
            }
        }
        return returnCount;
    }

    /**
     * Calculates the number of self-quotes for a given paper.
     * A self-quote occurs when the citing paper has the same authors as the cited paper.
     * O(E) E = adjacent edges
     * @param paper The paper for which the self-quotes are to be calculated.
     * @return The number of self-quotes for the given paper. If the paper is not part of the graph (indicated by a graphId of -1), it returns 0.
     */
    public int selfQuotes(P paper){
        int key =paper.getGraphId();
        if (key == -1) return 0;
        int selfQuoteCounter = 0;

        //iterate over all adjacent edges
        for (DirectedEdge edge : this.papersPGraph.adj(key)){
            //check if the authors are the same
            if (papersMap.get(edge.to()).getAuthors().equals(paper.getAuthors())){
                selfQuoteCounter++;
            }
        }
        return selfQuoteCounter;
    }

    /**
     * Calculates the number of self-quotes for a given paper.
     * A self-quote occurs when the citing paper has the same authors as the cited paper.
     * O(E) E = adjacent edges
     * @param paper The paper for which the self-quotes are to be calculated.
     * @param fn filename
     */
    public void selfQuotes(P paper, String fn){
        int i = selfQuotes(paper);
        writeTotxt(i, fn);
    }


    /**
     * Write Iterable result to txt
     * @param s Iterable to write
     * @param fn filename
     */
    private void writeTotxt(Iterable<Integer> s, String fn){
        try{
            File fp = new File(fn);
            FileWriter fw = new FileWriter(fp);
            fw.write(s + "\n");
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Finds the shortest path between two papers using Dijkstra's algorithm.
     * Time Complexity: O((V + E) log V) E = number of edges V = number of vertices
     * Extra Space: O(V)
     * @param paperStart The starting paper for the shortest path calculation.
     * @param paperEnd The ending paper for the shortest path calculation.
     * @return An iterable of paper IDs representing the shortest path from paperStart to paperEnd.
     *         If there is no path, it returns null.
     */
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

    /**
     * Finds the shortest path between two papers using Dijkstra's algorithm and output to txt.
     * Time Complexity: O((V + E) log V) E = number of edges V = number of vertices
     * Extra Space: O(V)
     * @param paperStart The starting paper for the shortest path calculation.
     * @param paperEnd The ending paper for the shortest path calculation.
     * @param fn filename
     */
    public void dijkstraShortestPath(P paperStart, P paperEnd, String fn){
        Iterable<Integer> s = dijkstraShortestPath(paperStart, paperEnd);
        writeTotxt(s, fn);
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

    /**
     * Calculate weight given a paper
     * weight = sum of the date
     * @param paper
     * @return weight
     */
    private double weightCalc(P paper){
        return (double) ((paper.getDate().day() + paper.getDate().month() + paper.getDate().year()));
    }

     //grafo vazio , 1 verificar quantos existem conference e depois criar o grafo
    /*public void writeGraphToFile(String fn){
        Out fp = new Out(fn);
        fp.println(this.papersPGraph.V());
        fp.println(this.papersPGraph.E());
        for(int i = 0; i < this.papersPGraph.V(); i++){
            for (DirectedEdge id : this.papersPGraph.adj(i)){
              fp.println(id.from() +  " " + id.to() + " " + id.weight());
            }
        }
    }*/




    /**
     * Filters the papers in the graph based on the provided type.
     * Time Complexity = ( V + E)
     * Extra space =  ( V + E)  V vertices and E edges the subgraph
     * <p>
     * This method creates a subgraph containing papers of a specific type (e.g., PaperConference or PaperJournal)
     * and returns a {@link PapersGraph} representing this subgraph.
     * </p>
     * <p>
     * The type parameter specifies the type of papers to filter. Valid values are "PaperConference" and "PaperJournal".
     * </p>
     *
     * @param type the type of papers to filter
     * @return a {@link PapersGraph} representing the subgraph containing papers of the specified type,
     * or {@code null} if the subgraph is empty
     */
    public PapersGraph subGraphArticleFilter(String type){
        // subGraphHash -> maps the vertices that we want
        HashMap<Integer, P> subGraphHash = new HashMap<>();
        // map original graph ids to new ids
        HashMap<Integer, Integer> subGraphFilter = new HashMap<>();

        //if set to false = PaperJournal else PaperConference
        boolean filter = type.equals("PaperConference");

        subHashFilter(filter, subGraphHash, subGraphFilter);
        //System.out.println(subGraphHash);
       // return null if the subHash is empty
        if (subGraphHash.isEmpty())
            return null;
        //Initialize subgraph
        PGraph pg = new PGraph(subGraphHash.size());

        // for each of the old ids that are of a given type
       for (Integer id : subGraphFilter.keySet()){
           // Iterate over edges and check if they are of a given type
            for (DirectedEdge edge : papersPGraph.adj(id)){
                if (filter){
                    if (papersMap.get(edge.to()) instanceof PaperConference){
                        // add to the new subgraph the directed edge using the correspondent ids given by the subGraphFilter Map
                        pg.addEdge(new DirectedEdge(subGraphFilter.get(id),  subGraphFilter.get(edge.to()), edge.weight()));
                    }
                }else{
                    if (papersMap.get(edge.to()) instanceof PaperJournal){
                        pg.addEdge(new DirectedEdge(subGraphFilter.get(id),  subGraphFilter.get(edge.to()), edge.weight()));
                    }
                }
            }
       }
        return new PapersGraph<>(pg, subGraphHash);
    }

    /**
     * Filters the papers in the graph based on the provided type.
     * Time Complexity = ( V + E)
     * Extra space =  ( V + E)  V vertices and E edges the subgraph
     * <p>
     * This method creates a subgraph containing papers of a specific type (e.g., PaperConference or PaperJournal)
     * and returns a {@link PapersGraph} representing this subgraph.
     * </p>
     * <p>
     * The type parameter specifies the type of papers to filter. Valid values are "PaperConference" and "PaperJournal".
     * </p>
     *
     * @param type the type of papers to filter
     * @param fn  filename
     */
    public void subGraphArticleFilter(String type, String fn){
        PapersGraph<P> p = subGraphArticleFilter(type);
        p.savePapersGraphTxt(fn);
    }

    /**
     * Filters the papers in the provided map based on the specified criteria and populates the provided subgraph hash and filter maps.
     * Time complexity: O(V) V = number of vertices
     * <p>
     * This method iterates over the keys of the papersMap and filters the papers based on the value of the filter parameter.
     * If the filter parameter is false, it adds papers of type PaperJournal to the subGraphHash and subGraphFilter maps.
     * If the filter parameter is true, it adds papers of type PaperConference to the subGraphHash and subGraphFilter maps.
     * </p>
     * <p>
     * The subGraphHash map contains the papers filtered by type, with their hash ID as the key.
     * The subGraphFilter map contains the original IDs of the papers mapped to their corresponding hash IDs in subGraphHash.
     * </p>
     *
     * @param filter         specifies whether to include papers of type PaperJournal (false) or PaperConference (true) in the subgraph
     * @param subGraphHash   the hash map to populate with the filtered papers, where the hash ID is the key and the paper object is the value
     * @param subGraphFilter the filter map to populate with the original paper IDs mapped to their corresponding hash IDs in subGraphHash
     */
    private void subHashFilter(boolean filter, HashMap<Integer, P> subGraphHash ,HashMap<Integer, Integer> subGraphFilter){
        int hashID = 0;

        for (Integer id : papersMap.keySet()){
            if (!filter){
                if(papersMap.get(id) instanceof PaperJournal){
                    //System.out.println("ID" + id + " hashID " + hashID);
                    subGraphFilter.put(id,hashID);
                    subGraphHash.put(hashID++, papersMap.get(id));
                }
            }else{
                if(papersMap.get(id) instanceof PaperConference){
                    //System.out.println("ID " + id + " hashID " + hashID);
                    subGraphFilter.put(id,hashID);
                    subGraphHash.put(hashID++, papersMap.get(id));
                }
            }
        }
    }

    /**
     * Finds the authors of papers that have been quoted within a specific time period.
     * Time Complexity O(N x E^2)  N = number of papers in list  E = all edges in the graph
     * @param papersList List of papers to check for quotes.
     * @param dateStart  Start date of the period.
     * @param dateEnd    End date of the period.
     * @return A string representation of the authors who have been quoted within the specified period,
     *         or a message indicating no quotes were found.
     */

    public String authorQuotesPeriod(ArrayList<P> papersList, Date dateStart, Date dateEnd){
        ArrayList<Author> authors = new ArrayList<>();

        for (P paper : papersList){
            Integer id = paper.getGraphId();
            //para cada edge
            for (DirectedEdge edge : this.papersPGraph.adj(id)){
                for (DirectedEdge edge1 : this.papersPGraph.adj(edge.to())){
                    //citacao para paper p
                    if (edge1.to() == edge.from()){
                        if (paper.getDate().isAfter(dateStart) && paper.getDate().isBefore(dateEnd)){
                            authors.addAll(papersMap.get(id).getAuthors());
                        }
                    }
                }
            }
        }
        if (authors.isEmpty())
            return "There are no quotes in this time stamp";
        return authors.toString();
    }

    /**
     * Finds the authors of papers that have been quoted within a specific time period.
     * Time Complexity O(N x E^2)  N = number of papers in list  E = all edges in the graph
     * @param papersList List of papers to check for quotes.
     * @param dateStart  Start date of the period.
     * @param dateEnd    End date of the period.
     * @param fn   filename
     */
    public void authorQuotesPeriod(ArrayList<P> papersList, Date dateStart, Date dateEnd, String fn){
        String s = authorQuotesPeriod(papersList, dateStart, dateEnd);
        writeTotxt(s, fn);
    }

    /**
     * Write string to txt
     */
    private void writeTotxt(String s, String fn){
        try{
            File fp = new File(fn);
            FileWriter fw = new FileWriter(fp);
            fw.write(s + "\n");
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Finds and returns papers that are journal articles and have been quoted within a specific time period.
     * Time Complexity O( N x E)  N = number of papers E = edges
     * Extra Space O (N) ArrayList of papers
     * @param dateStart Start date of the period.
     * @param dateEnd   End date of the period.
     * @return A string representation of the journal papers that have been quoted within the specified period,
     *         or a message indicating no journal quotes were found.
     */
    public String journalQuotes(Date dateStart, Date dateEnd){
        ArrayList<P> papers = new ArrayList<>();

        for (Integer key : papersMap.keySet()){
            if (papersMap.get(key) instanceof PaperJournal){
                //for each journal iterate over adj
              for (DirectedEdge edge : papersPGraph.adj(key)){
                  if (papersMap.get(edge.to()).getDate().isAfter(dateStart) && papersMap.get(edge.to()).getDate().isBefore(dateEnd)){
                      papers.add(papersMap.get(edge.to()));
                  }
              }
            }
        }
        if (papers.isEmpty())
            return "There are no quotes in any Journal";
        return papers.toString();
    }

    /**
     * Finds and returns papers that are journal articles and have been quoted within a specific time period.
     * Time Complexity O( N x E)  N = number of papers E = edges
     * Extra Space O (N) ArrayList of papers
     * @param dateStart Start date of the period.
     * @param dateEnd   End date of the period.
     * @return A string representation of the journal papers that have been quoted within the specified period,
     *         or a message indicating no journal quotes were found.
     */
    public void journalQuotes(Date dateStart, Date dateEnd, String fn){
        String s = journalQuotes(dateStart, dateEnd);
        writeTotxt(s, fn);
    }


    /**
     * Save graph to txt file in following format:
     * 8
     * 17
     * 0 1 0.26
     * 0 2 0.26
     * 0 4 0.38
     * 0 7 0.16
     * 1 3 0.29
     * 1 2 0.36
     * 1 7 0.19
     * 1 5 0.32
     * 2 7 0.34
     * 2 3 0.17
     * 3 6 0.52
     * 4 7 0.37
     * 4 5 0.35
     * 5 7 0.28
     * 6 4 0.93
     * 6 0 0.58
     * 6 2 0.4
     * nPapers: 8
     * Key: 0; Paper: reeeeeg1; Title: A horta ; Keywords: gardening, agriculture ; anAbstract: Study on urban gardening ; Date: 11/15/2020 ; TotalNumViews: 50000 ; TotalNumLikes: 50000 ; numDownloads: 9873100; editionNumber: 2 ; Local: Porto
     * nAuthors: 2
     * Author: 0 ; birthDate: 01/01/1985; Name: João Silva; Address: Rua das Flores, Porto; penName: olaoao ; Affiliation: University of Porto; ORCID: 0000-0000-0000-0001; scienceID: 10001; googleScholarID: GS12345; ScopusAuthorID: SA12345
     * Author: 1 ; birthDate: 02/15/1970; Name: Maria Pereira; Address: Avenida da Liberdade, Lisbon; penName: mundodd ; Affiliation: University of Lisbon; ORCID: 0000-0000-0000-0002; scienceID: 10002; googleScholarID: GS12346; ScopusAuthorID: SA12346
     * @param fn  filename
     */
    public void savePapersGraphTxt(String fn){
        Out fp = new Out(fn);
        writeAuthorsGraphTxt(fp);
        writePapersHashTxt(fp);
        fp.close();
    }

    /**
     * Write graph to txt file
     * @param fp  Out object
     */
    private void writeAuthorsGraphTxt(Out fp){
        fp.println(papersPGraph.V());
        fp.println(papersPGraph.E());

        for (int i = 0; i < papersPGraph.V(); i++){
            for (DirectedEdge w : papersPGraph.adj(i)){
                fp.println(w.from() + " " + w.to() + " " + w.weight());
            }
        }
    }

    /**
     * Write hash to txt file
     * @param fp  Out object
     */
    private void writePapersHashTxt(Out fp){

        fp.println("nPapers: " + papersMap.size());
        for (Integer id : papersMap.keySet()) {
            Paper p = (Paper) papersMap.get(id);

            if (p instanceof PaperConference) {
                fp.println("Key: " + id + "; Paper: " + p.getDoi() + "; Title: " + p.getTitle() + " ;Keywords: " + p.getKeywords() +
                        " ; anAbstract: " + p.getAnAbstract() + " ; Date: " + p.getDate() +
                        " ; TotalNumViews: " + p.getTotalNumViews() + " ; TotalNumLikes: " + p.getTotalNumViews() +
                        " ; numDownloads: " + p.getNumDownloads() + "; editionNumber: " + ((PaperConference) p).getEditionNumber() +
                        " ; Local: " + ((PaperConference) p).getLocal());
            } else if (p instanceof PaperJournal) {

                fp.println("Key: " + id + "; Paper: " + p.getDoi() + "; Title: " + p.getTitle() + " ;Keywords: " + p.getKeywords() +
                        " ; anAbstract: " + p.getAnAbstract() + " ; Date: " + p.getDate() +
                        " ; TotalNumViews: " + p.getTotalNumViews() + " ; TotalNumLikes: " + p.getTotalNumLikes() +
                        " ; numDownloads: " + p.getNumDownloads() + "; Publisher: " + ((PaperJournal) p).getPublisher() +
                        " ; Periodicity: " + ((PaperJournal) p).getPeriodicity() +  "; jcrIF: " + ((PaperJournal) p).getJcrIF() + "; ScopusID: " + ((PaperJournal) p).getScopusID()  );
            }

            fp.println("nAuthors: " + p.getAuthors().size());
            for (Author a : p.getAuthors()) {
                fp.println("Author: " + a.getIdNumber() + " ; birthDate: " + a.getBirthDate() +
                        "; Name: " + a.getName() + "; Address: " + a.getAddress() +
                        "; penName: " + a.getPenName() + " ; Affiliation: " + a.getAffiliation() +
                        "; ORCID: " + a.getOrcID() + "; scienceID: " + a.getScienceID() +
                        "; googleScholarID: " + a.getGoogleScholarID() + "; ScopusAuthorID: " + a.getScopusAuthorID());

            }
        }

    }


    /**
     * read Graph fom txt file
     * @param fn  filename
     */
    public void readPapersGraphTxt(String fn){
        try {
            In fp = new In(fn);
            readGraphTxt(fp);
            readPapersHash(fp);
            fp.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Read graph fom txt file
     * @param fp In
     */
    private void readGraphTxt(In fp) {
        String s = " ";
        int numVertexes = Integer.parseInt(fp.readLine());
        int numEdges = Integer.parseInt(fp.readLine());

        this.papersPGraph = new PGraph(numVertexes);

        for (int i = 0; i < numEdges; i++) {
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(" ");

            // Create a map to store the extracted information
            DirectedEdge e = new DirectedEdge(Integer.parseInt(pairs[0]), Integer.parseInt(pairs[1]), Double.parseDouble(pairs[2]));
            this.papersPGraph.addEdge(e);
        }
    }

    /**
    * Read Hash from txt file
     */
    private void readPapersHash(In fp){
        String s = " ";
        int numPapers = Integer.parseInt(fp.readLine().split(":")[1].trim());

        for(int i = 0; i < numPapers; i++){
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(";");

            // Create a map to store the extracted information
            Map<String, String> infoMap = new HashMap<>();

            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split(": ", 2);
                if (keyValue.length == 2) {
                    infoMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
            String Key = infoMap.get("Key");
            String DOI = infoMap.get("Paper");
            String Title = infoMap.get("Title");
            String Keywords = infoMap.get("Keywords");
            String anAbstract = infoMap.get("anAbstract");
            String Date = infoMap.get("Date").strip();
            String numDownloads = infoMap.get("numDownloads");
            String totalNumLikes = infoMap.get("TotalNumLikes");
            String totalNumViews = infoMap.get("TotalNumViews");

            //System.out.println(Date);

            Paper p = null;

            if (DOI.charAt(DOI.length() - 1) == '1') {
                String editionNumber = infoMap.get("editionNumber");
                String local = infoMap.get("Local");
                p = new PaperConference(DOI, Title, Keywords, anAbstract, new Date(Date), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews), Long.parseLong(numDownloads), Integer.parseInt(editionNumber), local);
            } else {
                String Publisher = infoMap.get("Publisher");
                String Periodicity = infoMap.get("Periodicity");
                String jcrIF = infoMap.get("jcrIF");
                String scopusID = infoMap.get("ScopusID");
                p = new PaperJournal(DOI, Title, Keywords, anAbstract, new Date(Date), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews), Long.parseLong(numDownloads), Publisher, edu.ufp.inf.paper_author.Periodicity.valueOf(Periodicity), Double.parseDouble(jcrIF), scopusID);
            }
            p.setGraphId(Integer.parseInt(Key));
            this.papersMap.put(Integer.parseInt(Key) , (P) p);
            fillPaperAuthors(p, fp);
        }
        this.ids = numPapers;
    }

    /**
     * Fills the Paper with authors related to a given paper.
     *
     * @param p  The paper for whom authors are being filled.
     * @param fp The file input stream to read paper data from.
     */
    private void fillPaperAuthors(Paper p, In fp) {
        int numAuthors = Integer.parseInt(fp.readLine().split(":")[1].trim());

        for (int i = 0; i < numAuthors; i++) {
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(";");

            // Create a map to store the extracted information
            Map<String, String> infoMap = new HashMap<>();

            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split(": ", 2);

                if (keyValue.length == 2) {
                    infoMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }

            String Author = infoMap.get("Author");
            String birthDate = infoMap.get("birthDate").strip();
            String name = infoMap.get("Name");
            String address = infoMap.get("Address");
            String affiliation = infoMap.get("Affiliation");
            String penName = infoMap.get("penName");
            String ORCID = infoMap.get("ORCID");
            String scienceID = infoMap.get("scienceID");
            String googleScholarID = infoMap.get("googleScholarID");
            String ScopusAuthorID = infoMap.get("ScopusAuthorID");
            Date d = new Date(birthDate);
            Author a = new Author(d, name, address,penName, affiliation, ORCID, scienceID, googleScholarID, ScopusAuthorID);
            a.setIdNumber(Integer.parseInt(Author));

            a.addPaper(p);
            p.getAuthors().add(a);
        }
    }

    /**
     * Save graph in bin file
     * @param fn  filename
     */
    public void saveGraphBin(String fn){
        try {
            File fp =new File(fn);
            FileOutputStream fos = new FileOutputStream(fp);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            writeGraphBin(oos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Write graph to bin file
     * @param oos ObjectOutputStream
     */
    private void writeGraphBin(ObjectOutputStream oos){
        try {
            oos.writeObject(papersPGraph);
            oos.writeObject(papersMap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Read Graph from bin file
     * @param fa  filename
     */
    public void readGraphBin(String fa){
        try {
            File f = new File(fa);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.papersPGraph = (PGraph) ois.readObject();
            this.papersMap = (HashMap<Integer, P>) ois.readObject();

            ois.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        EdgeWeightedDigraph pg = new PGraph(new In("./data/test1.txt"));
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


        for(int i= 0; i < 8 ; i++){
            //System.out.println(i);
            if(i % 2 == 0){
                p1.setDate(new Date(10, 1 + i, 2000));
                p1.addAuthor(a2);
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

        PapersGraph newGraph = pa.subGraphArticleFilter("PaperConference");
        System.out.println(newGraph.papersPGraph);

        PGraph pag = new PGraph(8);
        HashMap<Integer, Paper> hm = new HashMap<>();
        //PapersGraph pG = new PapersGraph(pag, hm);
        PapersGraph pG = new PapersGraph();
        pG.readGraphBin("data/test1.bin");
        //pa.savePapersGraphTxt("data/test1.txt");
        System.out.println(pG.papersPGraph);
        System.out.println(pG.papersMap);
        pG.readPapersGraphTxt("data/test1.txt");
          System.out.println(pG.papersPGraph);
          System.out.println(pG.papersMap);
        Date dateb = new Date(10,2, 1100);
        Date datee = new Date(10,2, 1900);
        pG.saveGraphBin("data/test1.bin");
        //pG.listPapersJournalAndTime(dateb,datee);
        //System.out.println(pG.papersPGraph);
        boolean[] marked = new boolean[8];



       // pG.dfsFilter(0, marked);


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
