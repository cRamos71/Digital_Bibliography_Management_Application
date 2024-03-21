package edu.ufp.inf.paper_author;

import edu.ufp.inf.person_user.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Author extends Person {

private String penName;

private String affiliation;

private String orcID;

private String scienceID;

private String googleScholarID;

private String scopusAuthorID;

private ArrayList<Paper> papers = new ArrayList<>();
    public Author() {
    }

    public Author(int idNumber, LocalDate birthDate, String name, String address, String penName, String affiliation, String orcID, String scienceID, String googleScholarID, String scopusAuthorID) {
        super(idNumber, birthDate, name, address);
        this.penName = penName;
        this.affiliation = affiliation;
        this.orcID = orcID;
        this.scienceID = scienceID;
        this.googleScholarID = googleScholarID;
        this.scopusAuthorID = scopusAuthorID;
    }

    public void addPaper(Paper p){
        for (Paper paper : papers) {
            if (paper.getTitle().equals(p.getTitle()) && paper.getDate().equals(p.getDate())) {
                return;
            }
        }
        papers.add(p);
    }

    public Paper removePaper(String t, Long y){
        Paper pap = searchPaperYear(t, y);
        if (pap != null){
            papers.remove(pap);
            /*
            System.out.println(pap.getDate().getYear());
            System.out.println("sucess!");
            */
            return pap;
        }
        return null;
    }

    public Paper searchPaperYear(String t, long y){

        for (Paper paper : papers){
            LocalDate date = LocalDate.of(paper.getDate().getYear(), paper.getDate().getMonth(), paper.getDate().getDay());
            if (date.getYear() == y && paper.getTitle().equals(t)){
                return paper;
            }
        }
        return null;
    }

    public Paper searchPaperTitle(String t){
        for(Paper paper : papers){
            if (paper.getTitle().equals(t)){
                return paper;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LocalDate bdate = LocalDate.of(2000, 10, 10);
        Author a = new Author(10, bdate, "ola", "4500-368", "1", "2", "3", "4", "5", "6" );

        Date d2 = new Date();
        Paper p = new Paper("ola", "1", "2", d2, a);



        p.setTitle("ola");
        a.papers.add(p);

        System.out.println(a.searchPaperTitle("ola").getTitle());
        System.out.println("\n--\n");
        System.out.println(a.searchPaperYear("ola", (long)p.getDate().getYear()).getTitle());
        System.out.println("\n--\n");
        a.removePaper("ola", (long)p.getDate().getYear());


    }
}
