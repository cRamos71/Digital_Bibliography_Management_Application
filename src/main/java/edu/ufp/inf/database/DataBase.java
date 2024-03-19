package edu.ufp.inf.database;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataBase {
   private RedBlackBST<Long, Author> authorsTree = new RedBlackBST<>();
   private RedBlackBST<Long, Paper> papersTree = new RedBlackBST<>();

   public ArrayList<Paper> paperAuthorPeriod(String name, LocalDate startDate, LocalDate endDate){
       return null;
   }

}
