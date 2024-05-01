package edu.ufp.inf.ManageGraphs;

import edu.princeton.cs.algs4.Graph;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.util.Arrays;
import java.util.HashMap;



public class AuthorsGraph<A extends Author, P extends Paper> {
   private UGraph authorsUGraph;
   private HashMap<Integer, A> authorsMap;

   public AuthorsGraph(UGraph authorsUGraph, HashMap<Integer, A> hashA) {
      this.authorsUGraph = authorsUGraph;
      this.authorsMap = hashA;
   }

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
   public int numberPapersBetweenAuthors(Author a1, Author a2) {
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






   public static void main(String[] args) {
      HashMap<Integer, Author> aMap = new HashMap<>();
      Author a1 = new Author();
      a1.setPenName("olaoao");
      a1.setIdNumber(1);
      aMap.put(1, a1);
      aMap.put(0, a1);
      UGraph ug = new UGraph(2);
      ug.addEdge(1, 0);
      AuthorsGraph aG = new AuthorsGraph(new UGraph(new Graph(10)), aMap);
      System.out.println(aG.numberCoAuthors(a1));
      boolean[] visited = new boolean[2];
      int num[] = new int[1];
      num[0] = 0;
      ug.dfsConnected(0, visited, num);
      System.out.println(num[0]);
      System.out.println(ug.isConexo());

   }
}
