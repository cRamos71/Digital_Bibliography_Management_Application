package edu.ufp.inf.ManageGraphs;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.util.*;


public class AuthorsGraph<A extends Author, P extends Paper> {
   private UGraph authorsUGraph;
   private HashMap<Integer, A> authorsMap = new HashMap<>();
   private Integer ids = 0;


  public AuthorsGraph(UGraph authorsUGraph) {
      this.authorsUGraph = authorsUGraph;
   }

   public AuthorsGraph(UGraph authorsUGraph, HashMap<Integer, A> hashAuthor) {
      this.authorsUGraph = authorsUGraph;
       for (Integer k : hashAuthor.keySet()){
           this.authorsMap.put(this.ids++, hashAuthor.get(k));
       }
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


    public void readGraphFromFile(String fn){
       In fp = new In(fn);
       int vertexes = fp.readInt();
       int edges = fp.readInt();

       for (int i = 0; i < vertexes; i++){
           this.authorsUGraph.addEdge(fp.readInt(), fp.readInt());
       }
    }



    private int edges(){
      return this.authorsUGraph.E();
   }

   private int vertex(){
      return this.authorsUGraph.V();
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
       a3.setAffiliation("nigga");
       a3.setIdNumber(2);
       aMap.put(2, a3);

      Paper p1 = new Paper();
      p1.setTitle("jkjk");
      UGraph ug = new UGraph(3);
      ug.addEdge(0, 1);
       ug.addEdge(0, 2);


       AuthorsGraph aG = new AuthorsGraph(ug, aMap);
      //System.out.println(aG.numberCoAuthors(a1));
      boolean[] visited = new boolean[2];
      int num[] = new int[1];
      num[0] = 0;
     // aG.addCoAuthor(a1,a2,  p1);
      //System.out.println("Edges: " + aG.edges());
      //ug.dfsConnected(0, visited, num);
      //System.out.println(num[0]);
      //System.out.println(ug.isConexo());
      //System.out.println(ug.isConexo());
      //AuthorsGraph ag2 = aG.subGraphAuthorsFilter("PT");
      aG.listVertexAuthorAffilliation("PT");
      // aG.writeGraphToFile("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/tinyG.txt");

       UGraph ug1 = new UGraph(13);
       HashMap<Integer, Author> aMap1 = new HashMap<>();
      AuthorsGraph aGTest = new AuthorsGraph(ug1 ,aMap1);
      aGTest.readGraphFromFile("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/tinyG.txt");
       System.out.println(aGTest.authorsUGraph);
      //System.out.println(ug.adj(0));
      //System.out.println(ag2.edges());

   }
}
