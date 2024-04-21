package edu.ufp.inf.database;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.time.LocalDate;
import java.util.*;

public class DataBase<A extends Author, P extends Paper> implements ManageAuthorsI<A>, ManagePapersI<P> {
    private RedBlackBST<Long, A> authorsTree = new RedBlackBST<>();
    private RedBlackBST<Date, A> dateAuthorsTree = new RedBlackBST<>();
    private RedBlackBST<Long, P> papersTree = new RedBlackBST<>();
    private RedBlackBST<Date, P> datePapersTree = new RedBlackBST<>();
    private HashMap<Long, A> mapUID = new HashMap<>();
    private HashMap<String, P> mapDOI = new HashMap<>();
    private long uID;

    @Override
    public void insert(A author) {
        author.setIdNumber(this.uID++);
        mapUID.put(author.getIdNumber(), author);
    }

    @Override
    public void update(A author) {

    }
    @Override
    public void remove(A author) {
        mapUID.remove(author.getIdNumber(), author);
    }

    @Override
    public void listAuthors() {
        for(Long l : this.mapUID.keySet()){
            System.out.println("Key : " + l + " Val: " + this.mapUID.get(l));
        }
    }

    @Override
    public void insert(P paper) {
        paper.setDoi(generateDoi());
        mapDOI.put(paper.getDoi() ,paper);
    }
    @Override
    public void update(P paper) {

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
    public void listPapers() {
        for(String l : this.mapDOI.keySet()){
            System.out.println("Key : " + l + " Val: " + this.mapDOI.get(l));
        }
    }


    @Override
    public String generateDoi() {
        //Combined timestamp with random component
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    public ArrayList<String> paperAuthorByIdPeriodIn(Long idAuthor, LocalDate startDate, LocalDate endDate) {
        RedBlackBST<Integer, ArrayList<String>> bstDate = new RedBlackBST<>();
        Author author = this.mapUID.get(idAuthor);

        if(author  == null)return null;

        // Update bst with curr paper title and year

        bstDate = author.bstPapersPeriod();

        ArrayList<String> papers = new ArrayList<>();
        for (Integer key : bstDate.keys(startDate.getYear(), endDate.getYear())){
            papers.addAll(bstDate.get(key));
        }
        return papers;
    }

    public ArrayList<String> paperAuthorByNamePeriod(String nameAuthor, LocalDate startDate, LocalDate endDate) {

        ArrayList<Author> authorsAL = new ArrayList<>();
        ArrayList<String> authorsAllPapers = new ArrayList<>();

        for (Map.Entry<Long, A> current : mapUID.entrySet()){
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


    public ArrayList<Paper> paperAuthorByOrcidPeriod(String orcidAuthor, LocalDate startDate, LocalDate endDate) {
        //authorsTree.get();
        return null;
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

        LocalDate bdate = LocalDate.of(2000,10,30);
        LocalDate bdate1 = LocalDate.of(100,10,30);


        Long id1 = 1L;
        Author a1 = new Author(null,bdate,"Joel", "Rua dos Pombos", "JJ","Porto Editora", "19","19","joelgamesxd","joelgames23");
        Paper p1 = new Paper();
        Author a2 = new Author(null,bdate,"Joel", "Rua dos Pombos", "JJ","Porto Editora", "19","19","joelgamesxd","joelgames23");

        p1.setDate(bdate);
        p1.setTitle("A historia de Joel, o Homem!");
        Paper p2 = new Paper();
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

       ArrayList<Paper> pex = db.top3PapersMostDownloads();

       System.out.println(pex);

       db.listAuthors();
       db.listPapers();
    }
}
