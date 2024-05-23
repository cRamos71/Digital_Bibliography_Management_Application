package edu.ufp.inf.test;

import edu.ufp.inf.Util.Date;
import edu.ufp.inf.database.DataBase;
import edu.ufp.inf.database.DataBaseLog;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

public class testApp {
    static DataBase<Author, Paper> db = new DataBase<>();
    //design pattern singleton getInstance()
    //main instancia baseded daos e cada funco de teste recebe bd

    static DataBaseLog dbLog = new DataBaseLog("data/db.txt", db);


    public static void testRemoveAuthor(DataBase<Author, Paper> db){

        db.remove(db.getMapUID().get(0), "./data/deletedAuthorsLog.txt");
        db.listAuthors();
        //db.remove(db.getMapUID().get(1), "./data/deletedAuthorsLog.txt");

    }

    public static void testWriteReadDBTxt(DataBaseLog dbLog){
        String path = "data/db.txt";
        dbLog.saveDBTxt(path);      //write DB
        dbLog.fillDB(path);         //read DB
        System.out.println(db.listPapers());
    }

    public static void testSearchPapersByAuthorPeriod(DataBase<Author, Paper> db){
        Date dStart = new Date("11/05/2020");
        Date dEnd = new Date("11/05/2023");
        int id = 0;

        System.out.println(db.paperAuthorByIdPeriodIn(id, dStart, dEnd));
    }



    public static void main(String[] args) {

        //testRemoveAuthor();
        testWriteReadDBTxt(dbLog);
        testSearchPapersByAuthorPeriod(db);
         db.listPapers();
    }
}
