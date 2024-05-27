package edu.ufp.inf.test;

import edu.ufp.inf.Util.Date;
import edu.ufp.inf.database.DataBase;
import edu.ufp.inf.database.DataBaseLog;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.util.Arrays;

public class testApp {
    static DataBase<Author, Paper> db = new DataBase<>();
    //design pattern singleton getInstance()
    //main instancia baseded daos e cada funco de teste recebe bd

    static DataBaseLog dbLog = new DataBaseLog("data/db.txt", db);

    public static void testRemoveAuthor(DataBase<Author, Paper> db){

        db.remove(db.getMapUID().get(0), "./data/deletedAuthorsLog.txt");
        db.listAuthors();
        System.out.println(db.getMapRemovedA());
        //db.remove(db.getMapUID().get(1), "./data/deletedAuthorsLog.txt");

    }

    public static void testWriteReadDBTxt(DataBaseLog dbLog){
        String path = "data/db.txt";
        dbLog.saveDBTxt(path);      //write DB
        dbLog.fillDB(path);         //read DB
        System.out.println(db.listPapers());
    }

    public static void testSearchPapersByAuthorPeriod(DataBase<Author, Paper> db){
        System.out.println("\ntestSearchPapersByAuthorPeriod:");
        Date dStart = new Date("11/05/2020");
        Date dEnd = new Date("11/05/2023");
        int id = 0;

        System.out.println(db.paperAuthorByIdPeriodIn(id, dStart, dEnd));
    }

    public static void top3ArtigosMaisUsados(DataBase<Author, Paper> db){
        System.out.println("\ntop3ArtigosMaisUsados:");
        System.out.println(Arrays.toString(db.top3PapersMostDownloads()));
    }

    public static void generateAuthorsAndPapersReport(DataBase<Author, Paper> db){
        System.out.println("\nlistAuthorsAndPapers:");
        System.out.println(db.listAuthors());
        System.out.println(db.listPapers());
    }

    public static void generateArticleUsageReport(DataBase<Author, Paper> db){
        //TODO
    }

    public static void main(String[] args) {

        //testRemoveAuthor(db);
        testWriteReadDBTxt(dbLog);
        testSearchPapersByAuthorPeriod(db);
         //db.listPapers();
        top3ArtigosMaisUsados(db);
        generateAuthorsAndPapersReport(db);
        generateArticleUsageReport(db);


    }
}

