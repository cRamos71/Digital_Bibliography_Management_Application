package edu.ufp.inf.database;

import edu.ufp.inf.paper_author.Author;

import java.util.ArrayList;

public interface ManageAuthorsI<A extends Author> {
    public void insert(A author);

    public void update(A author);

    public void remove(A author);

    public ArrayList<Author> listAuthors();
}