package edu.ufp.inf.ManageGraphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.In;
import edu.ufp.inf.Graph.PGraph;
import edu.ufp.inf.Graph.UGraph;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import edu.ufp.inf.paper_author.PaperConference;
import edu.ufp.inf.paper_author.PaperJournal;

import java.util.HashMap;
import java.util.Map;

public class PapersGraph<P extends Paper> {

    private EdgeWeightedDigraph papersPGraph;
    private HashMap<Integer, P> papersMap = new HashMap<>();
    private Integer ids = 0;


    public PapersGraph(PGraph papersPGraph) {
        this.papersPGraph = papersPGraph;
    }

    public PapersGraph(PGraph papersPGraph, HashMap<Integer, P> hashPapers) {
        this.papersPGraph = papersPGraph;
        for (Integer k : hashPapers.keySet()){
            this.papersMap.put(this.ids++, hashPapers.get(k));
        }
    }


    public void listPapersJournalAndTime(Date start, Date end){

        for (Integer id : papersMap.keySet()){
            if(papersMap.get(id) instanceof PaperJournal ) {
                if (papersMap.get(id).getDate().isBefore(end) && papersMap.get(id).getDate().isAfter(start)) {
                    System.out.println(id);
                }
            }
        }



    }

    public void listPapersConferenceTime(Date start, Date end){
        HashMap<Integer, P> tempPapersMap = new HashMap<>();

        for (Integer id : papersMap.keySet()){
                if( papersMap.get(id) instanceof PaperConference) {
                    System.out.println("olaolaoa");
                    if (papersMap.get(id).getDate().isBefore(end) && papersMap.get(id).getDate().isAfter(start)) {
                        System.out.println(id);
                    }
                }
        }
    }




    public static void main(String[] args) {
        PGraph pg = new PGraph(new In("/Users/claudio/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/test1.txt"));
        HashMap<Integer, Paper> m = new HashMap<>();
        for(int i= 0; i < 8 ; i++){
            if(i % 2 == 0){
                Paper p1 = new PaperConference();
                p1.setDate(new Date(10, 1 + i, 2000));
                m.put(i, p1);
            }else{
                Paper p2 = new PaperJournal();
                p2.setDate(new Date(10, 1 + i, 2000));
                m.put(i, p2);
            }
        }

        PapersGraph pa =new PapersGraph(pg, m);

        pa.listPapersConferenceTime(new Date(10, 1, 2000), new Date(12, 1, 2024));


    }

}
