package edu.ufp.inf.ManageGraphs;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.Util.Date;
import edu.ufp.inf.Util.DirectedEdge;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.paper_author.PaperConference;
import edu.ufp.inf.paper_author.PaperJournal;

import java.io.*;
import java.util.*;

/**
 * Represents a graph structure that connects authors.
 * The edges represent the co-author connection
 * @param <A> The type of authors.
 * @param <P> The type of papers.
 */
public class AuthorsGraph<A extends Author, P extends Paper> implements Serializable {
    /**
     * Graph
     */
   private UGraph authorsUGraph;
    /**
     * Map authors with a graph id Key: int -> Val: Author
     */
   private HashMap<Integer, A> authorsMap = new HashMap<>();
    /**
     * Var to store the count of ids that are assigned to each author
     */
   private Integer ids = 0;


  public AuthorsGraph(){
  }

    public UGraph getAuthorsUGraph() {
        return authorsUGraph;
    }

    public void setAuthorsUGraph(UGraph authorsUGraph) {
        this.authorsUGraph = authorsUGraph;
    }

    public HashMap<Integer, A> getAuthorsMap() {
        return authorsMap;
    }

    public void setAuthorsMap(HashMap<Integer, A> authorsMap) {
        this.authorsMap = authorsMap;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public AuthorsGraph(Integer v) {
      this.authorsUGraph = new UGraph(v);
   }

   public AuthorsGraph(Integer v, HashMap<Integer, A> hashAuthor) {
      this.authorsUGraph = new UGraph(v);
       for (Integer k : hashAuthor.keySet()){
           this.authorsMap.put(this.ids++, hashAuthor.get(k));
       }
   }

    public AuthorsGraph(UGraph ug, HashMap<Integer, A> hashAuthor) {
        this.authorsUGraph = ug;
        for (Integer k : hashAuthor.keySet()){
            this.authorsMap.put(this.ids++, hashAuthor.get(k));
        }
    }

    public AuthorsGraph(String fn) {
      readAuthorsGraphTxt(fn);
    }


   private Integer getIDMapGraph(Author a){
       for (Integer k : authorsMap.keySet()){
           if(authorsMap.get(k).equals((a))) return k;
       }
       return -1;
   }

    public void addEdge(A a1, A a2){
        addHash(a1);
        addHash(a2);
        this.authorsUGraph.addEdge(a1.getIdNumber(),a2.getIdNumber());
    }

    private void addHash(A a){
      if (!this.authorsMap.containsKey(a.getIdNumber())){
          Integer id1 = this.ids++;
          a.setIdNumber(id1);
          System.out.println("I got in!");
          this.authorsMap.put(id1, a);
      }
    }

    /**
     * Calculates the number of co-authors for a given author.
     * Time Complexity: O(V), where V is the number of adjacent vertices to the given author in the graph.
     * Extra Space: O(V), where V is the number of adjacent vertices to the given author in the graph.
     * @param a The author for which to calculate the number of co-authors.
     * @return The number of co-authors.
     */
   public int numberCoAuthors(Author a){
       int id = getIDMapGraph(a);
       ArrayList<Integer> arr = new ArrayList<>();
       int count = 0;

       //check adj vertex and count each unique connection (co-author)
       for (Integer key : authorsUGraph.adj(id)){
           if(!arr.contains(key)){
               arr.add(id);
               count++;
           }
       }
       return count;
   }

    /**
     * Calculates the number of co-authors for a given author and output to txt file.
     * Time Complexity: O(V), where V is the number of adjacent vertices to the given author in the graph.
     * Extra Space: O(V), where V is the number of adjacent vertices to the given author in the graph.
     * @param a The author for which to calculate the number of co-authors.
     * @return The number of co-authors.
     */
    public void numberCoAuthors(Author a, String fn){
       int i = numberCoAuthors(a);
       writeTotxt(i, fn);
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
     * Calculates the number of papers co-authored by both given authors.
     * Time Complexity: O(E), where E is the number of edges (papers) between the two authors.
     * Extra Space: O(1)
     * @param a1 The first author.
     * @param a2 The second author.
     * @return The number of papers co-authored by both authors.
     */
   public int numPapersBetweenAuthors(Author a1, Author a2) {
      int idA1 = getIDMapGraph(a1);
      int idA2 = getIDMapGraph(a2);

       // If there is no edge between the two authors, return 0
       if(!authorsUGraph.hasEdge(idA1, idA2)){
         return 0;
      }

      int sharedEdges = 0;

       // Iterate over the adjacent vertices of the first author and count shared edges with the second author
       for(Integer n : this.authorsUGraph.adj(idA1)){
         if(n.compareTo(idA2) == 0){
            sharedEdges++;
         }
      }
      return sharedEdges;
   }

    /**
     * Calculates the number of papers co-authored by both given authors an doutput to txt file.
     * Time Complexity: O(E), where E is the number of edges (papers) between the two authors.
     * Extra Space: O(1)
     * @param a1 The first author.
     * @param a2 The second author.
     * @param fn file name
     */
    public void numPapersBetweenAuthors(Author a1, Author a2, String fn) {
        int i = numPapersBetweenAuthors(a1, a2);
        writeTotxt(i, fn);
    }


    /**
     * Generates a subgraph containing authors with the given affiliation.
     * Time Complexity: O(V + E), where V is the number of authors with the specified affiliation
     * and E is the number of edges in the resulting subgraph.
     * Extra Space: O(V), where V is the number of authors with the specified affiliation.
     * @param affiliation The affiliation to filter authors.
     * @return A subgraph containing authors with the specified affiliation, or null if no authors match the affiliation.
     */
   public AuthorsGraph<A, Paper> subGraphAuthorsFilter(String affiliation){
      HashMap<Integer, A> setVertex = new HashMap<>();
      //search in map the Authors who are of a given affiliation
      for (Integer id : this.authorsMap.keySet()){
         if(authorsMap.get(id).getAffiliation().compareTo(affiliation) == 0){
            setVertex.put(id,authorsMap.get(id));
         }
      }

      if(setVertex.isEmpty()) return null;
      UGraph ug = new UGraph(setVertex.size());
       // Add edges to the subgraph between authors with the specified affiliation

       for (Integer v : setVertex.keySet()){
         for(Integer w : this.authorsUGraph.adj(v)){
            if(setVertex.containsKey(w)){
               ug.addEdge(v, w);
            }
         }
      }
       return new AuthorsGraph<>(ug, setVertex);
   }

    /**
     * Generates a subgraph containing authors with the given affiliation and output to txt.
     * Time Complexity: O(V + E), where V is the number of authors with the specified affiliation
     * and E is the number of edges in the resulting subgraph.
     * Extra Space: O(V), where V is the number of authors with the specified affiliation.
     * @param affiliation The affiliation to filter authors.
     * @param fn file name
     */
    public void subGraphAuthorsFilter(String affiliation, String fn){
        AuthorsGraph<A, Paper> a = subGraphAuthorsFilter(affiliation);
        a.saveAuthorsGraphTxt(fn);
    }


    /**
     * Finds the minimum number of hops (edges) between two vertices in the graph.
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It performs a breadth-first search traversal to find the minimum number of hops between two vertices.
     * Extra Space: O(V). It uses additional space for the boolean array marked[], the edgeTo[] array, and the distTo[] array.
     * @param author1 from author1
     * @param author2 to author2
     * @return The minimum number of hops between the vertices, or -1 if there is no path between them.
     */
   public Integer minimumHopsBetweenAuthors(A author1, A author2){
       int a1 = getIDMapGraph(author1);
       int a2 = getIDMapGraph(author2);

       return this.authorsUGraph.minimumHopsBetween(a1, a2);
   }

    /**
     * Finds the minimum number of hops (edges) between two vertices in the graph and output to txt file.
     * Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
     * It performs a breadth-first search traversal to find the minimum number of hops between two vertices.
     * Extra Space: O(V). It uses additional space for the boolean array marked[], the edgeTo[] array, and the distTo[] array.
     * @param author1 from author1
     * @param author2 to author2
     * @param fn file name
     */
    public void minimumHopsBetweenAuthors(A author1, A author2, String fn){
        int i = minimumHopsBetweenAuthors(author1, author2);
        writeTotxt(i, fn);
    }



    /**
     * Lists the vertex IDs of authors belonging to a specific affiliation.
     * Time Complexity: O(N) N = number of entries in authorsMap
     * Extra Space: O(M)    M = number of authors with the specified affiliation
     * @param affiliation The affiliation to search for.
     * @return arr ArrayList of strings
     */
   public ArrayList<String> listVertexAuthorAffilliation(String affiliation){
       HashMap<Integer, A> vertexMap = new HashMap<>();
       ArrayList<String> arr = new ArrayList<>();
       //search in map the Authors who are of a given affiliation
       for (Integer id : this.authorsMap.keySet()){
           if(authorsMap.get(id).getAffiliation().compareTo(affiliation) == 0){
               vertexMap.put(id,authorsMap.get(id));
               System.out.println(id);
           }
       }

       //add to the arrayList the Edge
       for(Integer v : vertexMap.keySet()){
           for(Integer w : this.authorsUGraph.adj(v)){
               if(vertexMap.containsKey(w))
                  arr.add("EDGE: " + v + " - "   + w);
           }
       }
       return arr;
   }

    /**
     * Lists the vertex IDs of authors belonging to a specific affiliation and output to txt
     * Time Complexity: O(N) N = number of entries in authorsMap
     * Extra Space: O(M)    M = number of authors with the specified affiliation
     * @param affiliation The affiliation to search for.
     * @param fn filename
     */
    public void listVertexAuthorAffilliation(String affiliation, String fn){
      ArrayList<String> arr = listVertexAuthorAffilliation(affiliation);
      writeTotxt(arr, fn);
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
     * Write graph to txt file
     * @param fn    file name
     */
    public void writeGraphToFile(String fn){
        Out fp = new Out(fn);
        boolean[] visited = new boolean[this.authorsUGraph.V()];
        fp.println(this.authorsUGraph.V());
        fp.println(this.authorsUGraph.E());
        for(int i = 0; i < this.authorsUGraph.V(); i++){
            visited[i] = true;
            for (Integer id : this.authorsUGraph.adj(i)){
                if(!visited[id]) {
                    fp.println(id + " " + i);
                }
            }
        }
    }


    /**
     * @return number of edges
     */
    private int edges(){
      return this.authorsUGraph.E();
   }

    /**
     * @return number of vertex
     */
    private int vertex(){
      return this.authorsUGraph.V();
   }


    /**
     * Write Graph to txt file
     * @param fa  filename
     */
    public void saveAuthorsGraphTxt(String fa) {
        Out fp = new Out(fa);
        writeAuthorsGraphTxt(fp);
        writeAuthorsHashTxt(fp);
        fp.close();
    }


    /**
     * Write authorHash
     * @param fp
     */
    private void writeAuthorsHashTxt(Out fp){

            fp.println("nAuthors: " + authorsMap.size());
            for (Integer id : authorsMap.keySet()) {
                Author a = (Author) authorsMap.get(id);
                fp.println("Key: " + id + " ; Author: " + a.getIdNumber() + " ; birthDate: " + a.getBirthDate() +
                        "; Name: " + a.getName() + "; Address: " + a.getAddress() +
                        "; penName: " + a.getPenName() + " ; Affiliation: " + a.getAffiliation() +
                        "; ORCID: " + a.getOrcID() + "; scienceID: " + a.getScienceID() +
                        "; googleScholarID: " + a.getGoogleScholarID() + "; ScopusAuthorID: " + a.getScopusAuthorID());

                fp.println("nPapers: " + a.getPapers().size());
                for (Paper p : a.getPapers()) {
                    if (p instanceof PaperConference) {
                        fp.println("Paper: " + p.getDoi() + "; Title: " + p.getTitle() + " ;Keywords: " + p.getKeywords() +
                                " ; anAbstract: " + p.getAnAbstract() + " ; Date: " + p.getDate() +
                                " ; TotalNumViews: " + p.getTotalNumViews() + " ; TotalNumLikes: " + p.getTotalNumViews() +
                                " ; numDownloads: " + p.getNumDownloads() + "; editionNumber: " + ((PaperConference) p).getEditionNumber() +
                                " ; Local: " + ((PaperConference) p).getLocal());
                    } else if (p instanceof PaperJournal) {

                        fp.println("Paper: " + p.getDoi() + "; Title: " + p.getTitle() + " ;Keywords: " + p.getKeywords() +
                                " ; anAbstract: " + p.getAnAbstract() + " ; Date: " + p.getDate() +
                                " ; TotalNumViews: " + p.getTotalNumViews() + " ; TotalNumLikes: " + p.getTotalNumLikes() +
                                " ; numDownloads: " + p.getNumDownloads() + "; Publisher: " + ((PaperJournal) p).getPublisher() +
                                " ; Periodicity: " + ((PaperJournal) p).getPeriodicity() +  "; jcrIF: " + ((PaperJournal) p).getJcrIF() + "; ScopusID: " + ((PaperJournal) p).getScopusID()  );
                    }
                }
            }

    }

    /**
     * Writes graph to txt file with following format:
     * 3
     * 2
     * 0 2
     * 0 1
     * nAuthors: 1
     * Key: 0 ; Author: 0 ; birthDate: 01/01/2001; Name: null; Address: null; penName: olaoao ; Affiliation: PT; ORCID: null; scienceID: null; googleScholarID: null; ScopusAuthorID: null
     *  nPapers: 1
     * Paper: fagaaaa1; Title: O joaozinho ;Keywords: null ; anAbstract: null ; Date: 10/20/2023 ; TotalNumViews: 54656 ; TotalNumLikes: 54656 ; numDownloads: 9873425; editionNumber: 1 ; Local: Porto
     * Key: 1 ; Author: 1 ; birthDate: 08/21/2020; Name: null; Address: null; penName: mundodd ; Affiliation: PT; ORCID: null; scienceID: null; googleScholarID: null; ScopusAuthorID: null
     * nPapers: 0
     * @param fp The output file stream where the author and paper details will be written.
     */
    private void writeAuthorsGraphTxt(Out fp){
       Boolean marked[] = new Boolean[authorsUGraph.V()];
       Arrays.fill(marked,false);


       fp.println(authorsUGraph.V());
       fp.println(authorsUGraph.E());

       for (int i = 0; i < authorsUGraph.V(); i++){
           for (Integer w : authorsUGraph.adj(i)){
                if (!marked[w]){
                    fp.println(i + " " + w);
                }
           }
           marked[i] = true;
       }
    }

    /**
     * Write graph to bin file
     * @param fn file name
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
    * Write graph fomr bin file
     * @param oos
     */
    private void writeGraphBin(ObjectOutputStream oos){
        try {
            oos.writeObject(authorsUGraph);
            oos.writeObject(authorsMap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Read graph from Bin file
     * @param fa file author path
     */
    public void readGraphBin(String fa){
        try {
            File f = new File(fa);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.authorsUGraph = (UGraph) ois.readObject();
            this.authorsMap = (HashMap<Integer, A>) ois.readObject();


            ois.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * Read Authors Hash object from txt file
     */
    public void readAuthorsGraphTxt(String fn){
       try {
           In fp = new In(fn);
           readGraphTxt(fp);
           readAuthorsHash(fp);
           fp.close();

       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     * Reads graph from a text file.
     * 3
     * 2
     * 0 2
     * 0 1
     * nAuthors: 3
     * Key: 0 ; Author: 0 ; birthDate: 01/01/2001; Name: null; Address: null; penName: olaoao ; Affiliation: PT; ORCID: null; scienceID: null; googleScholarID: null; ScopusAuthorID: null
     * nPapers: 1
     * Paper: fagaaaa1; Title: O joaozinho ;Keywords: null ; anAbstract: null ; Date: 10/20/2023 ; TotalNumViews: 54656 ; TotalNumLikes: 54656 ; numDownloads: 9873425; editionNumber: 1 ; Local: Porto
     * @param fp The input file stream from which author and paper details will be read.
     */
    private void readGraphTxt(In fp) {
        String s = " ";
        int numVertexes = Integer.parseInt(fp.readLine());
        int numEdges = Integer.parseInt(fp.readLine());

        this.authorsUGraph = new UGraph(numVertexes);

        for (int i = 0; i < numEdges; i++) {
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(" ");

            // Create a map to store the extracted information
            this.authorsUGraph.addEdge(Integer.parseInt(pairs[0]), Integer.parseInt(pairs[1]));
        }
    }

    /**
     * Reads authors and their associated papers from a text file.
     * nAuthors: 3
     * Key: 0 ; Author: 0 ; birthDate: 01/01/2001; Name: null; Address: null; penName: olaoao ; Affiliation: PT; ORCID: null; scienceID: null; googleScholarID: null; ScopusAuthorID: null
     * nPapers: 1
     * Paper: fagaaaa1; Title: O joaozinho ;Keywords: null ; anAbstract: null ; Date: 10/20/2023 ; TotalNumViews: 54656 ; TotalNumLikes: 54656 ; numDownloads: 9873425; editionNumber: 1 ; Local: Porto
     * @param fp The input file stream from which author and paper details will be read.
     */
    private void readAuthorsHash(In fp){
        String s = " ";
        int numAuthors = Integer.parseInt(fp.readLine().split(":")[1].trim());

        for(int i = 0; i < numAuthors; i++){
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
            this.authorsMap.put(Integer.parseInt(Key), (A) a);
            fillAuthorPapers(a,fp);
        }
        this.ids = numAuthors;
    }

    /**
     * Fills the Author with papers related to a given author.
     *
     * @param a  The author for whom papers are being filled.
     * @param fp The file input stream to read paper data from.
     */
    private void fillAuthorPapers(Author a, In fp) {
        int numPapers = Integer.parseInt(fp.readLine().split(":")[1].trim());
        for (int i = 0; i < numPapers; i++) {
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
            a.addPaper(p);
            p.getAuthors().add(a);
        }
    }






   public static void main(String[] args) {
      HashMap<Integer, Author> aMap = new HashMap<>();
      Author a1 = new Author();
      a1.setPenName("olaoao");
      a1.setAffiliation("PT");
      a1.setIdNumber(0);
      aMap.put(0, a1);
      Author a2 = new Author();
      a2.setPenName("mundodd");
      a2.setAffiliation("PT");
      a2.setIdNumber(1);
      aMap.put(1, a2);

       Author a3 = new Author();
       a3.setPenName("olasolas");
       a3.setAffiliation("nigeria");
       a3.setIdNumber(2);
       aMap.put(2, a3);

      Paper p1 = new Paper();
      p1.setTitle("jkjk");
      UGraph ug = new UGraph(3);
      ug.addEdge(0, 1);
       ug.addEdge(0, 2);

       //AuthorsGraph aG = new AuthorsGraph(ug, aMap);
       AuthorsGraph aG = new AuthorsGraph();
       System.out.println(aG.authorsMap);
       System.out.println(aG.authorsUGraph);
       //System.out.println(aG.numberCoAuthors(a1));
       boolean[] visited = new boolean[2];
      int num[] = new int[1];
      num[0] = 0;

       //aG.saveGraphBin("data/graphA.bin");
      // aG.saveAuthorsGraphTxt("./data/graphA.txt");
       //aG.readAuthorsGraphTxt("./data/graphA.txt");

        aG.readGraphBin("data/graphA.bin");
       System.out.println(aG.authorsMap);
       System.out.println(aG.authorsUGraph);
     // aG.addCoAuthor(a1,a2,  p1);
      //System.out.println("Edges: " + aG.edges());
      //ug.dfsConnected(0, visited, num);
      //System.out.println(num[0]);
      //System.out.println(ug.isConexo());
      //System.out.println(ug.isConexo());
      //AuthorsGraph ag2 = aG.subGraphAuthorsFilter("PT");
      //aG.listVertexAuthorAffilliation("PT");
      // aG.writeGraphToFile("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/tinyG.txt");

       //UGraph ug1 = new UGraph(13);
       //HashMap<Integer, Author> aMap1 = new HashMap<>();
      //AuthorsGraph aGTest = new AuthorsGraph(ug1 ,aMap1);
      //aGTest.readGraphFromFile("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/tinyG.txt");
       //System.out.println(aGTest.authorsUGraph);
      //System.out.println(ug.adj(0));
      //System.out.println(ag2.edges());

   }
}
