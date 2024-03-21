package edu.ufp.inf.database;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

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
        return null;
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

        int id1 = 1;
        Author a1 = new Author();
        Paper p1 = new Paper();
        //a1.addPaper(p1);

        db.insert(a1);

        db.paperAuthorByIdPeriod(id1, LocalDate.now(), LocalDate.now());

    }
}
