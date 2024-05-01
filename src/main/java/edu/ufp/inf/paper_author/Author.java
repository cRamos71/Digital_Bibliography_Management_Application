package edu.ufp.inf.paper_author;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.RedBlackBST;
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

    public Author(Integer idNumber, LocalDate birthDate, String name, String address, String penName, String affiliation, String orcID, String scienceID, String googleScholarID, String scopusAuthorID) {
        super(idNumber, birthDate, name, address);
        this.penName = penName;
        this.affiliation = affiliation;
        this.orcID = orcID;
        this.scienceID = scienceID;
        this.googleScholarID = googleScholarID;
        this.scopusAuthorID = scopusAuthorID;
    }

    public Author( LocalDate birthDate, String name, String address, String penName, String affiliation, String orcID, String scienceID, String googleScholarID, String scopusAuthorID) {
        super( birthDate, name, address);
        this.penName = penName;
        this.affiliation = affiliation;
        this.orcID = orcID;
        this.scienceID = scienceID;
        this.googleScholarID = googleScholarID;
        this.scopusAuthorID = scopusAuthorID;
    }



    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getOrcID() {
        return orcID;
    }

    public void setOrcID(String orcID) {
        this.orcID = orcID;
    }

    public String getScienceID() {
        return scienceID;
    }

    public void setScienceID(String scienceID) {
        this.scienceID = scienceID;
    }

    public String getGoogleScholarID() {
        return googleScholarID;
    }

    public void setGoogleScholarID(String googleScholarID) {
        this.googleScholarID = googleScholarID;
    }

    public String getScopusAuthorID() {
        return scopusAuthorID;
    }

    public void setScopusAuthorID(String scopusAuthorID) {
        this.scopusAuthorID = scopusAuthorID;
    }

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Paper> papers) {
        this.papers = papers;
    }

    public void addPaper(Paper p){
        for (Paper paper : papers) {
            if (paper.getTitle().equals(p.getTitle()) && paper.getDate().equals(p.getDate())) {
                return;
            }
        }
        papers.add(p);
    }

    public Paper removePaperTitleYear(String t, Long y){
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
            LocalDate date = LocalDate.of(paper.getDate().getYear(), paper.getDate().getMonth(), paper.getDate().getDayOfYear());
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

    public ArrayList<Paper> listPapers(){
        return this.papers;
    }

    public RedBlackBST<Integer, ArrayList<String>> bstPapersPeriod(){
        RedBlackBST<Integer, ArrayList<String>> bstDate = new RedBlackBST<>();

        for (Paper p: listPapers()){
            Integer key = p.getDate().getYear();
            if(bstDate.contains(key)){
                ArrayList<String> a = bstDate.get(key);
                if(!a.contains(p.getTitle())){
                    a.add(p.getTitle());
                    bstDate.put(key, a);
                }
            }else{
                ArrayList<String>temp =  new ArrayList<>();
                temp.add(p.getTitle());
                bstDate.put(key, temp);
            }
        }
        return bstDate;
    }


    public boolean removePaper(Paper p){
        return this.papers.remove(p);
    }



    private Paper getPaperDoi(String doi){
        for(Paper a : papers){
            if(a.getDoi().compareTo(doi) == 0){
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Author{" +
                "penName='" + penName + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", orcID='" + orcID + '\'' +
                ", scienceID='" + scienceID + '\'' +
                ", googleScholarID='" + googleScholarID + '\'' +
                ", scopusAuthorID='" + scopusAuthorID + '\'' +
                ", papers=" + papers +
                '}';
    }

    public static void main(String[] args) {
        LocalDate bdate = LocalDate.of(2000, 10, 10);
        Author a = new Author(10, bdate, "ola", "4500-368", "1", "2", "3", "4", "5", "6" );

        Date d2 = new Date();
       // Paper p = new Paper("ola", "1", "2", d2, a);

       /* p.setTitle("ola");
        a.papers.add(p);

        System.out.println(a.searchPaperTitle("ola").getTitle());
        System.out.println("\n--\n");
        System.out.println(a.searchPaperYear("ola", (long)p.getDate().getYear()).getTitle());
        System.out.println("\n--\n");
        a.removePaperTitleYear("ola", (long)p.getDate().getYear());
*/
    }
}
