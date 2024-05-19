package edu.ufp.inf.database;

import edu.princeton.cs.algs4.*;
import edu.ufp.inf.ManageGraphs.AuthorsGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.paper_author.PaperConference;
import edu.ufp.inf.paper_author.PaperJournal;
import  edu.ufp.inf.paper_author.Paper;

import edu.princeton.cs.algs4.Date;
import java.util.*;


public class DataBase<A extends Author, P extends edu.ufp.inf.paper_author.Paper> implements ManageAuthorsI<A>, ManagePapersI<P> {
    private RedBlackBST<Long, A> authorsTree = new RedBlackBST<>();
    private RedBlackBST<Date, A> dateAuthorsTree = new RedBlackBST<>();
    private RedBlackBST<Long, P> papersTree = new RedBlackBST<>();
    private RedBlackBST<Date, P> datePapersTree = new RedBlackBST<>();
    private HashMap<Integer, A> mapUID = new HashMap<>();
    private HashMap<String, P> mapDOI = new HashMap<>();

    private HashMap<Integer, String> mapRemovedA = new HashMap<>();

    private AuthorsGraph aGraph = new AuthorsGraph(new UGraph(10), this.mapUID);
    Digraph PapersDigraph = new Digraph(10);
    private Integer uID = 0;

    private Integer graphID;

    public RedBlackBST<Long, A> getAuthorsTree() {
        return authorsTree;
    }

    public void setAuthorsTree(RedBlackBST<Long, A> authorsTree) {
        this.authorsTree = authorsTree;
    }

    public RedBlackBST<Date, A> getDateAuthorsTree() {
        return dateAuthorsTree;
    }

    public void setDateAuthorsTree(RedBlackBST<Date, A> dateAuthorsTree) {
        this.dateAuthorsTree = dateAuthorsTree;
    }

    public RedBlackBST<Long, P> getPapersTree() {
        return papersTree;
    }

    public void setPapersTree(RedBlackBST<Long, P> papersTree) {
        this.papersTree = papersTree;
    }

    public RedBlackBST<Date, P> getDatePapersTree() {
        return datePapersTree;
    }

    public void setDatePapersTree(RedBlackBST<Date, P> datePapersTree) {
        this.datePapersTree = datePapersTree;
    }

    public void setuID(Integer uID) {
        this.uID = uID;
    }

    public HashMap<Integer, A> getMapUID() {
        return mapUID;
    }

    public void setMapUID(HashMap<Integer, A> mapUID) {
        this.mapUID = mapUID;
    }

    public HashMap<String, P> getMapDOI() {
        return mapDOI;
    }

    public void setMapDOI(HashMap<String, P> mapDOI) {
        this.mapDOI = mapDOI;
    }

    public HashMap<Integer, String> getMapRemovedA() {
        return mapRemovedA;
    }

    public void setMapRemovedA(HashMap<Integer, String> mapRemovedA) {
        this.mapRemovedA = mapRemovedA;
    }


    public Digraph getPapersDigraph() {
        return PapersDigraph;
    }

    public void setPapersDigraph(Digraph papersDigraph) {
        PapersDigraph = papersDigraph;
    }

    @Override
    public void insert(A author) {
        if (author.getIdNumber() == null)
            author.setIdNumber(this.uID++);
        if (!mapUID.containsKey(author.getIdNumber())){
            System.out.println("insere db");
            mapUID.put(author.getIdNumber(), author);
        }
    }

    @Override
    public void update(A author) {
        if (!mapUID.containsKey(author.getIdNumber())) return;
        mapUID.put(author.getIdNumber(),author);
    }
    @Override
    public void remove(A author, String fn) {
        mapUID.remove(author.getIdNumber(), author);
        mapRemovedA.put(author.getIdNumber(),author.getName());

        authorsDeletedLog(fn);

        removeAuthorPapersMap((ArrayList<P>) author.getPapers(), author);
    }


    private void authorsDeletedLog(String fn){
        Out out = new Out(fn);

        for (Integer a : mapRemovedA.keySet())
            out.println(mapRemovedA.get(a));

        out.close();
    }

    private void removeAuthorPapersMap(ArrayList<P> papers, A a){
        for(int i = 0; i < papers.size(); i++){
            ArrayList<Author> authorsAL = null;
            authorsAL = papers.get(i).getAuthors();
            // only removes if the paper has only 1 author assigned
            if (authorsAL.size() == 1){
                remove(papers.get(i));
            }else{
                papers.get(i).getAuthors().remove(a);
            }
        }
    }

    @Override
    public ArrayList<Author> listAuthors() {
        ArrayList<Author> a = new ArrayList<>();
        for(Integer l : this.mapUID.keySet()){
            //System.out.println("Key : " + l + " Val: " + this.mapUID.get(l));
            a.add(this.mapUID.get(l));
        }
        return a;
    }

    @Override
    public void insert(P paper) {
        if (paper.getDoi() == null) {
            paper.setDoi(generateDoi());
            if (paper instanceof PaperConference) {
                paper.setDoi(paper.getDoi() + "1");
            } else if (paper instanceof PaperJournal) {
                paper.setDoi(paper.getDoi() + "0");
            }
        }

        mapDOI.put(paper.getDoi() ,paper);
    }
    @Override
    public void update(P paper) {
        if(!mapDOI.containsKey(paper.getDoi())) return;
        mapDOI.put(paper.getDoi(), paper);
    }
    @Override
    public void remove(P paper) {
        ArrayList<Author> authorsAL = null;
        authorsAL = paper.getAuthors();

        for(Author a : authorsAL){
            a.removePaper(paper);
        }

        mapDOI.remove(paper.getDoi());
    }

    @Override
    public ArrayList<String> listPapers() {
        ArrayList<String> pap = new ArrayList<>();
        for(String l : this.mapDOI.keySet()){
           // System.out.println("Key : " + l + " Val: " + this.mapDOI.get(l));
            pap.add(this.mapDOI.get(l).toString());
        }
        return pap;
    }




    @Override
    public String generateDoi() {
        //Combined timestamp with random component
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    public ArrayList<String> paperAuthorByIdPeriodIn(Integer idAuthor, Date startDate, Date endDate) {
        RedBlackBST<Integer, ArrayList<String>> bstDate = new RedBlackBST<>();
        Author author = this.mapUID.get(idAuthor);

        if(author  == null)return null;

        // Update bst with curr paper title and year

        bstDate = author.bstPapersPeriod();

        ArrayList<String> papers = new ArrayList<>();
        for (Integer key : bstDate.keys(startDate.year(), endDate.year())){
            papers.addAll(bstDate.get(key));
        }
        return papers;
    }

    public ArrayList<String> paperAuthorByNamePeriod(String nameAuthor, Date startDate, Date endDate) {

        ArrayList<Author> authorsAL = new ArrayList<>();
        ArrayList<String> authorsAllPapers = new ArrayList<>();

        for (Map.Entry<Integer, A> current : mapUID.entrySet()){
            Author a = current.getValue();
            if (a.getName().compareTo(nameAuthor) == 0){
                authorsAL.add(a);
            }
        }

        for (Author a : authorsAL){
            ArrayList<String> papers;
            papers = paperAuthorByIdPeriodIn(a.getIdNumber(),startDate,endDate);
            if (papers != null){
                for(String title : papers){
                    if (!authorsAllPapers.contains(title)){
                        authorsAllPapers.add(title);
                    }
                }
            }
        }
        if (authorsAllPapers.isEmpty())
            authorsAllPapers.add("There are no papers in the defined timestamp");

        return authorsAllPapers;
    }

    public ArrayList<Paper> top3PapersMostDownloads() {
        ArrayList<Paper> papers = new ArrayList<>(mapDOI.values());

        //this.mapDOI.
        papers.sort((paper1, paper2) -> Math.toIntExact(paper2.getNumDownloads() - paper1.getNumDownloads()));
        System.out.println(papers);

        Paper[] p = new Paper[3];
        for (int i= 0; i < p.length && i < papers.size(); i++){
            p[i] = papers.get(i);
        }

        for (int i= 0; i < p.length; i++){
            System.out.println( i+ " " +p[i] );
        }
        //System.out.println(p);
        return papers;
    }



    public ArrayList<Paper> papersNotDownloadedNotViewed() {
        ArrayList<Paper> papersFound = new ArrayList<>();

        for (String curr : mapDOI.keySet()){
            Paper p = mapDOI.get(curr);
            if (p.getNumDownloads() == 0 && p.getTotalNumViews() == 0){
                papersFound.add(p);
            }
        }

      return papersFound;
    }





    public static void main(String[] args) {
        DataBase db = new DataBase();

        Date bdate = new Date(12,10,2003);
        Date bdate1 = new Date(10,10,1290);


        Long id1 = 1L;
        Author a1 = new Author(null,bdate,"Joel", "Rua dos Pombos", "JJ","Porto Editora", "19","19","joelgamesxd","joelgames23");
        Paper p1 = new Paper();
        Author a2 = new Author(null,bdate,"Joel", "Rua dos Pombos", "JJ","Porto Editora", "19","19","joelgamesxd","joelgames23");

        p1.setDate(bdate);
        p1.setTitle("A historia de Joel, o Homem!");
        Paper p2 = new PaperConference();
        p2.setDate(bdate1);
        p2.setTitle("A historia de Joelzinho, o Rapaz!");
        p2.setDate(bdate1);
        p1.setDate(bdate);
        a1.addPaper(p2);
        a1.addPaper(p1);
        p1.addView();
        p2.addView();

        p2.addView();


        db.insert(a1);
        db.insert(a2);
        db.insert(p1);
        db.insert(p2);

        /*
        Paper test = db.getPaperTest(p1.getDoi());
        System.out.println("Papers:");
        System.out.println(test);
        System.out.println("\n");
        */

       /* ArrayList<Paper> testPaperNoViewsNoDownloads =  db.papersNotDownloadedNotViewed();

        System.out.println(testPaperNoViewsNoDownloads);

       //ArrayList<String> a =   db.paperAuthorByIdPeriodIn(id1, LocalDate.of(999, 10, 21), LocalDate.now());
       ArrayList<String> testAuthorName = db.paperAuthorByNamePeriod("Joel", LocalDate.of(800, 10, 21), LocalDate.now());

       System.out.println(testAuthorName);*/

      // ArrayList<Paper> pex = db.top3PapersMostDownloads();

       //System.out.println(pex);

       //db.listAuthors();
       db.listPapers();
    }
}
