package edu.ufp.inf.test;

import edu.ufp.inf.ManageGraphs.AuthorsGraph;
import edu.ufp.inf.ManageGraphs.PapersGraph;
import edu.ufp.inf.Util.Date;
import edu.ufp.inf.database.DataBase;
import edu.ufp.inf.database.DataBaseLog;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.paper_author.PaperConference;

import java.util.Arrays;

public class testApp {
    static DataBase<Author, Paper> db = new DataBase<>();
    static AuthorsGraph<Author, Paper> aGraph = new AuthorsGraph<>();
    static PapersGraph<Paper> pGraph = new PapersGraph<>();

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
        System.out.println(db.getMapUID().get(0));
    }


    public static void testWriteReadDBBIN(DataBaseLog dbLog){
        String fa = "data/authors.bin";
        String fp = "data/papers.bin";
        dbLog.saveDBBin(fa, fp);      //write DB
        dbLog.readDBin(fa, fp);         //read DB
        System.out.println(db.listPapers());
        System.out.println(db.getMapUID().get(0));
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

    public static void testGenerateAuthorsAndPapersReport(){
        dbLog.generateAuthorsAndPapersReport();
    }

    public static void testGenerateArticleUsageReport(int year, int month, int day){
       dbLog.generateArticleUsageReport(year, month, day);
    }



    public static void main(String[] args) {

        /*Database*/

            //testRemoveAuthor(db);
           // testWriteReadDBTxt(dbLog);
            //testSearchPapersByAuthorPeriod(db);
            //db.listPapers();
            //top3ArtigosMaisUsados(db);
           // testGenerateAuthorsAndPapersReport();
           // testGenerateArticleUsageReport(2024 ,5 ,28);
            testWriteReadDBBIN(dbLog);


        /*AuthorsGraph*/

            //aGraph.readAuthorsGraphTxt("data/graphA.txt");
            //System.out.println(aGraph.getAuthorsMap());

        /*PapersGraph*/

            //pGraph.readGraphFromFile();

    }
}

