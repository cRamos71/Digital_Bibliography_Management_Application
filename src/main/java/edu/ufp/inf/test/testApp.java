package edu.ufp.inf.test;

import edu.ufp.inf.ManageGraphs.AuthorsGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

public class testApp {
    private static final String PATH_U_GRAPH = "./data/tinyG.txt";
    private static  final String PATH_EW_DIGRAPH = "./data/test1.txt";
    private AuthorsGraph<Author, Paper> GA = new AuthorsGraph<Author, Paper>(PATH_U_GRAPH);
   //private AuthorsGraph<Author, Paper> GA = new AuthorsGraph<Author, Paper>(PATH_U_GRAPH);


}

