package edu.ufp.inf.database;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

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
        mapUID.put(this.uID++, author);
    }

    @Override
    public void update(A author) {

    }
    @Override
    public void remove(A author) {
        mapUID.remove(author.getIdNumber(), author);
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
        ArrayList<Author> authorsAL = new ArrayList<>();
        authorsAL = paper.getAuthors();

        for(Author a : authorsAL){
            a.removePaper(paper);
        }
        mapDOI.remove(paper.getDoi());
    }

    @Override
    public String generateDoi() {
        //Combined timestamp with random component
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Paper getPaperTest(String doi){
        // This is a test function
        return this.mapDOI.get(doi);
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

    public ArrayList<Paper> paperAuthorByNamePeriod(String nameAuthor, LocalDate startDate, LocalDate endDate) {
        //authorsTree.get();
        return null;
    }

    public ArrayList<Paper> paperAuthorByOrcidPeriod(String orcidAuthor, LocalDate startDate, LocalDate endDate) {
        //authorsTree.get();
        return null;
    }


    public static void main(String[] args) {
        DataBase db = new DataBase();

        LocalDate bdate =LocalDate.of(2000,10,30);
        LocalDate bdate1 =LocalDate.of(1000,10,30);


        Long id1 = 1L;
        Author a1 = new Author(1L,bdate,"Joel", "Rua dos Pombos", "JJ","Porto Editora", "19","19","joelgamesxd","joelgames23");
        Paper p1 = new Paper();
        p1.setDate(bdate);
        p1.setTitle("A historia de Joel, o Homem!");
        Paper p2 = new Paper();
        p2.setDate(bdate1);
        p2.setTitle("A historia de Joelzinho, o Rapaz!");
        a1.addPaper(p2);
        a1.addPaper(p1);

        db.insert(a1);
        db.insert(p1);

        Paper test = db.getPaperTest(p1.getDoi());

        System.out.println(test);

       ArrayList<String> a =   db.paperAuthorByIdPeriodIn(id1, LocalDate.of(999, 10, 21), LocalDate.now());


    }
}
