package edu.ufp.inf.database;
import edu.princeton.cs.algs4.Date;
import edu.ufp.inf.paper_author.Periodicity;
import edu.princeton.cs.algs4.In;
import edu.ufp.inf.paper_author.*;



public class TestDB {
    static DataBase<Author, Paper> db = new DataBase<>();

    static  DataBaseLog dbLog = new DataBaseLog(db);


    public static  void testRemoveAuthor(){

        db.remove(db.getMapUID().get(0), "./data/deletedAuthorsLog.txt");
       //db.remove(db.getMapUID().get(1), "./data/deletedAuthorsLog.txt");

    }



    public static void main(String[] args) {
        dbLog.fillDB("./data/db.txt");
        System.out.println(db.listAuthors());
        db.listPapers();
        System.out.println(db.getMapDOI().get("fagaaaa1").getAuthors());

       testRemoveAuthor();
       // System.out.println(db.getMapUID());

       // System.out.println("olsdmlsçfmsf");
        //System.out.println(db.listAuthors());
        db.listPapers();
    }
}
