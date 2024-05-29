package edu.ufp.inf.paper_author;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.inf.person_user.Person;

import edu.ufp.inf.Util.Date;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an author of academic papers, extending the Person class.
 */
public class Author extends Person implements Serializable {
/**
 * Pen Name ex = Paulo, J.
 */
private String penName;
/**
 * Affiliation ex = UFP
 */
private String affiliation;
/**
 * ORCID
 */
private String orcID;
/**
 * scienceID
 */
private String scienceID;
/**
 * googleScholar ID
 */
private String googleScholarID;
/**
 * scopusAuthor ID
 */
private String scopusAuthorID;
/**
 * List holding papers of Author
 */
private ArrayList<Paper> papers = new ArrayList<>();
public Author() {
    }

    public Author(Integer idNumber, Date birthDate, String name, String address, String penName, String affiliation, String orcID, String scienceID, String googleScholarID, String scopusAuthorID) {
        super(idNumber, birthDate, name, address);
        this.penName = penName;
        this.affiliation = affiliation;
        this.orcID = orcID;
        this.scienceID = scienceID;
        this.googleScholarID = googleScholarID;
        this.scopusAuthorID = scopusAuthorID;
    }

    public Author( Date birthDate, String name, String address, String penName, String affiliation, String orcID, String scienceID, String googleScholarID, String scopusAuthorID) {
        super(birthDate, name, address);
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

    /**
     * Adds a paper to the author's list of papers if it does not already exist in the list.
     *
     * @param p the paper to add
     */
    public void addPaper(Paper p){
        for (Paper paper : papers) {
            if (paper.getTitle().equals(p.getTitle()) && paper.getDate().equals(p.getDate())) {
                return;
            }
        }
        papers.add(p);
    }

    /**
     * Removes a paper by title and year from the author's list of papers.
     *
     * @param t the title of the paper to remove
     * @param y the year of the paper to remove
     * @return the removed paper if it was found and removed, null otherwise
     */
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

    /**
     * Searches for a paper by title and year in the author's list of papers.
     *Time complexity: O(P) P = num papers in ArrayList
     * Extra Space: O(1)
     * @param t the title of the paper to search for
     * @param y the year of the paper to search for
     * @return the paper if found, null otherwise
     */
    public Paper searchPaperYear(String t, long y){
        for (Paper paper : papers){
            Date date = new Date(paper.getDate().month(),paper.getDate().day(), paper.getDate().year());
            if (date.year() == y && paper.getTitle().equals(t)){
                return paper;
            }
        }
        return null;
    }

    /**
     * Searches for a paper by title in the author's list of papers.
     * Time complexity: O(P) P = num papers in ArrayList
     * Extra Space: O(1)
     * @param t the title of the paper to search for
     * @return the paper if found, null otherwise
     */
    public Paper searchPaperTitle(String t){
        for(Paper paper : papers){
            if (paper.getTitle().equals(t)){
                return paper;
            }
        }
        return null;
    }

    /**
     * Returns the list of papers authored by the author.
     *
     * @return the list of papers
     */
    public ArrayList<Paper> listPapers(){
        return this.papers;
    }

    /**
     * Creates a RedBlackBST with the years as keys and lists of paper titles as values.
     * Time complexity : O(NlogN)
     * Extra Space : O (N) N =  number of keys in bst
     * @return the RedBlackBST containing the years and paper titles
     */
    public RedBlackBST<Integer, ArrayList<String>> bstPapersPeriod(){
        RedBlackBST<Integer, ArrayList<String>> bstDate = new RedBlackBST<>();

        //build bst with key = year and val = Arraylist of papers published in that year
        for (Paper p: listPapers()){
            Integer key = p.getDate().year();
            if(bstDate.contains(key)){
                ArrayList<String> a = bstDate.get(key);
                //if not already added add to the arrayList
                if(!a.contains(p.getTitle())){
                    a.add(p.getTitle());
                    bstDate.put(key, a);
                }
            }else{
                //Add arrayList of papers to key
                ArrayList<String> temp =  new ArrayList<>();
                temp.add(p.getTitle());
                bstDate.put(key, temp);
            }
        }
        return bstDate;
    }

    /**
     * Removes a paper from the author's list of papers.
     *
     * @param p the paper to remove
     * @return true if the paper was removed, false otherwise
     */
    public boolean removePaper(Paper p){
        return this.papers.remove(p);
    }


    /**
     * Searches for a paper by DOI in the author's list of papers.
     *
     * @param doi the DOI of the paper to search for
     * @return the paper if found, null otherwise
     */
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
        Date bdate = new Date(10, 10, 2000);
        Author a = new Author(10, bdate, "ola", "4500-368", "1", "2", "3", "4", "5", "6" );

        //Date d2 = new Date();
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
