package edu.ufp.inf.ManageGraphs;

import edu.princeton.cs.algs4.Graph;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class AuthorsGraph<A extends Author, P extends Paper> {
   private UGraph authorsUGraph;
   private HashMap<Integer, A> authorsMap;

   public AuthorsGraph(UGraph authorsUGraph, HashMap<Integer, A> hashA) {
      this.authorsUGraph = authorsUGraph;
      this.authorsMap = hashA;
   }

   public AuthorsGraph(UGraph authorsUGraph) {
      this.authorsUGraph = authorsUGraph;
   }


  /* public void addCoAuthor(Author v, Author w, Paper p){
      edgesMap.put(authorsUGraph.E(),  p);
      authorsUGraph.addEdge(v.getIdNumber(), w.getIdNumber());
   }*/

   /**
    @param a -
    Function to get the number of co-authors of a given author
    */
   public int numberCoAuthors(Author a){
      return this.authorsUGraph.degree(a.getIdNumber());
   }

   /**
    Function to get number of papers between 2 Authors

    */
   public int numPapersBetweenAuthors(Author a1, Author a2) {
      int idA1 = a1.getIdNumber();
      int idA2 = a2.getIdNumber();
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
      Set<Integer> setVertex = new HashSet<>();
      //search in map the Authors who are of a given affiliation
      for (Integer id : this.authorsMap.keySet()){
         if(authorsMap.get(id).getAffiliation().compareTo(affiliation) == 0){
            setVertex.add(id);
         }
      }

      if(setVertex.isEmpty()) return null;
      UGraph ug = new UGraph(setVertex.size());

      for (Integer v : setVertex){
         for(Integer w : this.authorsUGraph.adj(v)){
            if(setVertex.contains(w)){
               ug.addEdge(v, w);
            }
         }
      }

      AuthorsGraph subGraph = new AuthorsGraph(ug);
      return subGraph;
   }


   public int edges(){
      return this.authorsUGraph.E();
   }

   public int vertex(){
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
      Paper p1 = new Paper();
      p1.setTitle("jkjk");
      UGraph ug = new UGraph(2);
      ug.addEdge(1, 0);
      AuthorsGraph aG = new AuthorsGraph(ug, aMap);
      System.out.println(aG.numberCoAuthors(a1));
      boolean[] visited = new boolean[2];
      int num[] = new int[1];
      num[0] = 0;
     // aG.addCoAuthor(a1,a2,  p1);
      //System.out.println("Edges: " + aG.edges());
      ug.dfsConnected(0, visited, num);
      System.out.println(num[0]);
      System.out.println(ug.isConexo());
      System.out.println(ug.isConexo());
      AuthorsGraph ag2 = aG.subGraphAuthorsFilter("PT");
      System.out.println(ag2.edges());

   }
}
