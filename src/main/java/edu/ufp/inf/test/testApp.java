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

    public static void generateArticleUsageReport(DataBase<Author, Paper> db, int year, int month, int day){
        System.out.println("\ngenerateArticleUsageReport:");
        Date date1 = new Date(5,29,2024);
        for (String k : db.getMapDOI().keySet()){
            System.out.println("------------------");
            db.getMapDOI().get(k).addView();
            db.getMapDOI().get(k).addLike();
            System.out.println("numLikesYear: " + db.getMapDOI().get(k).getNumLikesYear(year));
            System.out.println("numLikesMonth: " + db.getMapDOI().get(k).getNumLikesMonth(month, year));
            System.out.println("numLikesDay: " + db.getMapDOI().get(k).getNumLikesDay(date1));

            System.out.println("numViewsYear: " + db.getMapDOI().get(k).getNumViewsYear(year));
            System.out.println("numViewsMonth: " + db.getMapDOI().get(k).getNumViewsMonth((short) month, year));
            System.out.println("numViewsDay: " + db.getMapDOI().get(k).getNumViewsDay(date1));
        }
    }



    public static void main(String[] args) {

        /*Database*/
/*
            //testRemoveAuthor(db);
            testWriteReadDBTxt(dbLog);
            testSearchPapersByAuthorPeriod(db);
            //db.listPapers();
            top3ArtigosMaisUsados(db);
            generateAuthorsAndPapersReport(db);
            generateArticleUsageReport(db,2024 ,5 ,28);
*/

        /*AuthorsGraph*/

            aGraph.readAuthorsGraphTxt("data/graphA.txt");
            System.out.println(aGraph.getAuthorsMap());

        /*PapersGraph*/

            //pGraph.readGraphFromFile();

    }
}

