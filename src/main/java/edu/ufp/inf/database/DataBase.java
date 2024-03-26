package edu.ufp.inf.database;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.io.CharArrayReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataBase<A extends Author, P extends Paper> implements ManageAuthorsI<A>, ManagdePapersI<P> {
    private RedBlackBST<Long, A> authorsTree = new RedBlackBST<>();
    private RedBlackBST<Date, A> dateAuthorsTree = new RedBlackBST<>();
    private RedBlackBST<Long, P> papersTree = new RedBlackBST<>();
    private RedBlackBST<Date, P> datePapersTree = new RedBlackBST<>();
    private HashMap<Long, A> mapUID = new HashMap<>();
    private long uID;

    //insert // remove....
    public void insert(A author) {
        mapUID.put(this.uID++, author);
    }

    public void update(A author) {

    }

    public void remove(A author) {
        //Ver depois
        //mapUID.remove(1, author);
    }

    public void insert(P paper) {

        //datePapersTree.put();
    }

    public void update(P paper) {

    }

    public void remove(P paper) {

    }

    public ArrayList<Paper> paperAuthorByIdPeriod(int idAuthor, LocalDate startDate, LocalDate endDate) {
        //authorsTree.get();
        BST<Long, A> bstDate = new BST<>();

        return null;
    }

    public ArrayList<String> paperAuthorByIdPeriodIn(Long idAuthor, LocalDate startDate, LocalDate endDate) {
        BST<Integer, ArrayList<String>> bstDate = new BST<>();
        Author author = this.mapUID.get(idAuthor);

        if(author  == null)return null;

        // Update bst with curr paper title and year
        for (Paper p:author.listPapers()){
            Integer key = p.getDate().getYear();
            if(bstDate.contains(key)){
              ArrayList<String> a = bstDate.get(key);
              if(!a.contains(p.getTitle())){
                  a.add(p.getTitle());
                  bstDate.put(key, a);
              }
            }
            ArrayList<String>temp =  new ArrayList<>();
            temp.add(p.getTitle());
            bstDate.put(key, temp);
        }

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
        Author a1 = new Author(1,bdate,"Joel", "Rua dos Pombos", "JJ","Porto Editora", "19","19","joelgamesxd","joelgames23");
        Paper p1 = new Paper();
        p1.setDate(bdate);
        p1.setTitle("A historia de Joel, o Homem!");
        Paper p2 = new Paper();
        p2.setDate(bdate1);
        p2.setTitle("A historia de Joelzinho, o Rapaz!");
        a1.addPaper(p2);
        a1.addPaper(p1);

        db.insert(a1);



       ArrayList<String> a =   db.paperAuthorByIdPeriodIn(id1, LocalDate.of(999, 10, 21), LocalDate.now());

       for (String k : a){
          System.out.println(k);
       }
    }
}
