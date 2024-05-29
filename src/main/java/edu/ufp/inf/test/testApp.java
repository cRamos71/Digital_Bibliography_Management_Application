package edu.ufp.inf.test;

import edu.ufp.inf.ManageGraphs.AuthorsGraph;
import edu.ufp.inf.ManageGraphs.PapersGraph;
import edu.ufp.inf.Util.Date;
import edu.ufp.inf.database.DataBase;
import edu.ufp.inf.database.DataBaseLog;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class testApp {
    static DataBase<Author, Paper> db = new DataBase<>();
    static AuthorsGraph<Author, Paper> aGraph = new AuthorsGraph<>("./data/graphA.txt");
    static PapersGraph<Paper> pGraph = new PapersGraph<>("data/test1.txt");
    static String outputFile = "data/result.txt";

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
        //db.paperAuthorByNamePeriod(id, dStart, dEnd, outputFile");
    }

    public static void testPapersNotDownloadedNotViewed(DataBase<Author, Paper> db){
        System.out.println("\ntestPapersNotDownloadedNotViewed;");
        System.out.println(db.papersNotDownloadedNotViewed());
        db.papersNotDownloadedNotViewed(outputFile);
    }

    public static void top3ArtigosMaisUsados(DataBase<Author, Paper> db){
        System.out.println("\ntop3ArtigosMaisUsados:");
        System.out.println(Arrays.toString(db.top3PapersMostDownloads()));
        db.top3PapersMostDownloads(outputFile);
    }

    public static void testGenerateAuthorsAndPapersReport(){
        dbLog.generateAuthorsAndPapersReport();
    }

    public static void testGenerateArticleUsageReport(int year, int month, int day){
       dbLog.generateArticleUsageReport(year, month, day);
    }


    public static void testNumCoAuthors(){
        Author a = (Author) aGraph.getAuthorsMap().get(0);
        System.out.println("NumCoAuthors: " + aGraph.numberCoAuthors(a));
        aGraph.numberCoAuthors(a, outputFile);
    }

    public static void testNumPapersBetweenAuthors(){
        Author a = (Author) aGraph.getAuthorsMap().get(0);
        Author a1 = (Author) aGraph.getAuthorsMap().get(1);
        System.out.println("NumPapersBetweenAuthors: " + aGraph.numPapersBetweenAuthors(a,a1));
        aGraph.numPapersBetweenAuthors(a,a1,outputFile);
    }

    public static void testSubGraphAuthorsFilter(){
        AuthorsGraph subGraph = aGraph.subGraphAuthorsFilter("PT");
        System.out.println(subGraph.getAuthorsMap());
        aGraph.subGraphAuthorsFilter("PT", outputFile);
    }

    public static void testMinimumHopsBetweenAuthors(){
        Author a = (Author) aGraph.getAuthorsMap().get(0);
        Author a1 = (Author) aGraph.getAuthorsMap().get(1);
        System.out.println("NumPapersBetweenAuthors: " + aGraph.minimumHopsBetweenAuthors(a,a1));
        aGraph.minimumHopsBetweenAuthors(a,a1,outputFile);
    }

    public static void testListVertexAuthorAffilliation(){
        System.out.println(aGraph.listVertexAuthorAffilliation("PT"));
        aGraph.listVertexAuthorAffilliation("PT", outputFile);
    }

    public static void testWriteReadAuthorsGraphTxt(){
        String path1 = "data/graphC.txt";
        String path2 = "data/graphB.txt";
        aGraph.saveAuthorsGraphTxt(path1); //write DB
        System.out.println(aGraph.getAuthorsMap());
        System.out.println(aGraph.getAuthorsUGraph());
        aGraph.readAuthorsGraphTxt(path2); //read DB
        System.out.println(aGraph.getAuthorsMap());
        System.out.println(aGraph.getAuthorsUGraph());
    }

    public static void testWriteReadAuthorsGraphBin(){
        String path = "data/graphA.bin";
        aGraph.saveGraphBin(path);
        aGraph.readGraphBin(path);
        System.out.println(aGraph.getAuthorsMap());
        System.out.println(aGraph.getAuthorsUGraph());
    }

    public static void testListPapersJournalAndTime(){
        Date d = new Date(1,1,2014);
        Date d1 = new Date(1,1,2021);
        pGraph.listPapersJournalAndTime(d,d1);
        pGraph.listPapersJournalAndTime(d,d1,outputFile);
    }

    public static void testListPapersConferenceTime(){
        Date d = new Date(1,1,2014);
        Date d1 = new Date(1,1,2021);
        pGraph.listPapersConferenceTime(d,d1);
        pGraph.listPapersConferenceTime(d,d1,outputFile);
    }

    public static void testCalculateFirstOrderQuotes(){
        Paper p = pGraph.getPapersMap().get(7);
        System.out.println(pGraph.calculateFirstOrderQuotes(p));
        pGraph.calculateFirstOrderQuotes(p, outputFile);
    }

    public static void testCalculateSecondOrderQuotes(){
        Paper p = pGraph.getPapersMap().get(7);
        System.out.println(pGraph.calculateSecondOrderQuotes(p));
        pGraph.calculateSecondOrderQuotes(p, outputFile);
    }

    public static void testSelfQuotes(){
        Paper p = pGraph.getPapersMap().get(0);
        System.out.println("testSelfQuotes: " + pGraph.selfQuotes(p));
        pGraph.selfQuotes(p, outputFile);
    }

    public static void testDijkstraShortestPath(){
        Paper p = pGraph.getPapersMap().get(0);
        Paper p1 = pGraph.getPapersMap().get(6);
        System.out.println(pGraph.dijkstraShortestPath(p,p1));
        pGraph.dijkstraShortestPath(p, p1, outputFile);
    }

    public static void testSubGraphArticleFilter(){
        PapersGraph subGraph = pGraph.subGraphArticleFilter("PaperConference");
        System.out.println(subGraph.getPapersPGraph());
        System.out.println(subGraph.getPapersMap());
    }

    public static void testAuthorQuotesPeriod(){
        Date d = new Date(1,1,2014);
        Date d1 = new Date(1,1,2021);
        ArrayList<Paper> papers = new ArrayList<>();
        papers.add(pGraph.getPapersMap().get(0));
        papers.add(pGraph.getPapersMap().get(1));
        System.out.println(pGraph.authorQuotesPeriod(papers,d,d1));
        pGraph.authorQuotesPeriod(papers,d,d1,outputFile);
    }

    public static void testJournalQuotes(){
        Date d = new Date(1,1,2014);
        Date d1 = new Date(1,1,2021);
        System.out.println(pGraph.journalQuotes(d,d1));
        pGraph.journalQuotes(d,d1,outputFile);
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
            //testWriteReadDBBIN(dbLog);
            //testPapersNotDownloadedNotViewed(db);

        /*AuthorsGraph*/

            //aGraph.readAuthorsGraphTxt("data/graphA.txt");
            //testNumCoAuthors();
            //testNumPapersBetweenAuthors();
            //testSubGraphAuthorsFilter();
            //testMinimumHopsBetweenAuthors();
            //testListVertexAuthorAffilliation();
            //testWriteReadAuthorsGraphTxt();
            //testWriteReadAuthorsGraphBin();



        /*PapersGraph*/

            //testListPapersJournalAndTime();
            //testListPapersConferenceTime();
            //testCalculateFirstOrderQuotes();
            //testCalculateSecondOrderQuotes();
            //testSelfQuotes();
            //testDijkstraShortestPath();
            //testSubGraphArticleFilter();
            //testAuthorQuotesPeriod();
            //testJournalQuotes();
    }
}

