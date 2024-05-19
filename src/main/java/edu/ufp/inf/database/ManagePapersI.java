package edu.ufp.inf.database;

import edu.ufp.inf.paper_author.Paper;

import java.util.ArrayList;

public interface ManagePapersI<P extends Paper> {
    public void insert(P paper);

    public void update(P paper);

    public void remove(P paper);

    public ArrayList<String> listPapers();

    public String generateDoi();

}
