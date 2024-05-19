package edu.ufp.inf.database;
import edu.princeton.cs.algs4.Date;
import edu.ufp.inf.paper_author.Periodicity;
import edu.princeton.cs.algs4.In;
import edu.ufp.inf.paper_author.*;



public class TestDB {
    static DataBase<Author, Paper> db = new DataBase<>();

    static  DataBaseLog dbLog = new DataBaseLog("data/db.txt", db);


    public static void testRemoveAuthor(){

        db.remove(db.getMapUID().get(0), "./data/deletedAuthorsLog.txt");
       //db.remove(db.getMapUID().get(1), "./data/deletedAuthorsLog.txt");

    }

    public static void testWriteReadDBTxt(){
        String path = "data/db.txt";
        dbLog.saveDBTxt(path);
        dbLog.fillDB(path);
        System.out.println(db.listPapers());
    }



    public static void main(String[] args) {

       //testRemoveAuthor();
       testWriteReadDBTxt();
       // db.listPapers();
    }
}
