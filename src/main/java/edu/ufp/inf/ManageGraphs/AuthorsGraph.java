package edu.ufp.inf.ManageGraphs;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.Util.Date;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.paper_author.PaperConference;
import edu.ufp.inf.paper_author.PaperJournal;

import java.io.*;
import java.util.*;


public class AuthorsGraph<A extends Author, P extends Paper> implements Serializable {
   private UGraph authorsUGraph;
   private HashMap<Integer, A> authorsMap = new HashMap<>();
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
       // this.authorsUGraph = new UGraph(fn);

    }


   private Integer getIDMapGraph(Author a){
       for (Integer k : authorsMap.keySet()){
           if(authorsMap.get(k).equals((a))) return k;
       }
       return -1;
   }

   /**
    @param a -
    Function to get the number of co-authors of a given author
    */
   public int numberCoAuthors(Author a){
       int id = getIDMapGraph(a);
       ArrayList<Integer> arr = new ArrayList<>();
       int count = 0; // Cost-effective

       for (Integer key : authorsUGraph.adj(id)){
           if(!arr.contains(key)){
               arr.add(id);
               count++;
           }
       }
       return count;
   }

   /**
    Function to get number of papers between 2 Authors

    */
   public int numPapersBetweenAuthors(Author a1, Author a2) {
      int idA1 = getIDMapGraph(a1);
      int idA2 = getIDMapGraph(a2);
      if(!authorsUGraph.hasEdge(idA1, idA2)){
         return 0;
      }

      int sharedEdges = 0;

      for(Integer n : this.authorsUGraph.adj(idA1)){
         if(n.compareTo(idA2) == 0){
            sharedEdges++;
         }
      }
      return sharedEdges;
   }

   public AuthorsGraph subGraphAuthorsFilter(String affiliation){
      HashMap<Integer, A> setVertex = new HashMap<>();
      //search in map the Authors who are of a given affiliation
      for (Integer id : this.authorsMap.keySet()){
         if(authorsMap.get(id).getAffiliation().compareTo(affiliation) == 0){
            setVertex.put(id,authorsMap.get(id));
         }
      }

      if(setVertex.isEmpty()) return null;
      UGraph ug = new UGraph(setVertex.size());

      for (Integer v : setVertex.keySet()){
         for(Integer w : this.authorsUGraph.adj(v)){
            if(setVertex.containsKey(w)){
               ug.addEdge(v, w);
            }
         }
      }

      AuthorsGraph subGraph = new AuthorsGraph(ug, setVertex);
      return subGraph;
   }

   public Integer minimumHopsBetweenAuthors(A author1, A author2){
       int a1 = getIDMapGraph(author1);
       int a2 = getIDMapGraph(author2);

       return this.authorsUGraph.minimumHopsBetween(a1, a2);
   }

   public void listVertexAuthorAffilliation(String affiliation){
       HashMap<Integer, A> vertexMap = new HashMap<>();
       //search in map the Authors who are of a given affiliation
       for (Integer id : this.authorsMap.keySet()){
           if(authorsMap.get(id).getAffiliation().compareTo(affiliation) == 0){
               vertexMap.put(id,authorsMap.get(id));
               System.out.println(id);
           }
       }

       for(Integer v : vertexMap.keySet()){
           for(Integer w : this.authorsUGraph.adj(v)){
               if(vertexMap.containsKey(w))
                   System.out.println("EDGE: " + v + " : "   + w);
           }
       }

   }

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





    private int edges(){
      return this.authorsUGraph.E();
   }

   private int vertex(){
      return this.authorsUGraph.V();
   }



    public void saveAuthorsGraphTxt(String fa) {
        Out fp = new Out(fa);
        writeAuthorsGraphTxt(fp);
        writeAuthorsHashTxt(fp);
        fp.close();
    }

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


    private void writeGraphBin(ObjectOutputStream oos){
        try {
            oos.write(authorsUGraph.V());
            oos.write(authorsUGraph.E());
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private void readGraphBin(String fa){
        try {
            File f = new File(fa);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

           this.authorsMap = (HashMap<Integer, A>) ois.readObject();
           this.authorsUGraph = (UGraph) ois.readObject();

            ois.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


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
      //System.out.println(aG.numberCoAuthors(a1));
      boolean[] visited = new boolean[2];
      int num[] = new int[1];
      num[0] = 0;
      //aG.readGraphBin("./data/graphA.bin");
       //aG.saveAuthorsGraphTxt("./data/graphA.txt");
       aG.readAuthorsGraphTxt("./data/graphA.txt");
       System.out.println(aG.authorsMap);
       System.out.println(aG.authorsUGraph);
      //aG.saveAuthorsBin("./data/graphA.bin");
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
