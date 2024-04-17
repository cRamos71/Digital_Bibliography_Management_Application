package edu.ufp.inf.paper_author;

import java.time.LocalDate;
import java.util.*;

public class Paper {

   private String doi;
   private String title;
   private String keywords;
   private String anAbstract;
   private LocalDate date;
   private Long numDownloads = 0L;
   private Long totalNumViews = 0L;
   private Long totalNumLikes = 0L;
   private Map<Date, Long> numViewsPerDay = new HashMap<>();
   private Map<Date, Long> numLikesPerDay = new HashMap<>();

   private ArrayList<Author> authors = new ArrayList<>();

    public Paper() {
    }

    public Paper(String doi, String title, String keywords, String anAbstract, LocalDate date, Author a) {
        this.doi = doi;
        this.title = title;
        this.keywords = keywords;
        this.anAbstract = anAbstract;
        this.date = date;
    }


    public Long getTotalNumViews() {
        return totalNumViews;
    }

    public void setTotalNumViews(Long totalNumViews) {
        this.totalNumViews = totalNumViews;
    }

    public Long getTotalNumLikes() {
        return totalNumLikes;
    }

    public void setTotalNumLikes(Long totalNumLikes) {
        this.totalNumLikes = totalNumLikes;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAnAbstract() {
        return anAbstract;
    }

    public void setAnAbstract(String anAbstract) {
        this.anAbstract = anAbstract;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(Long numDownloads) {
        this.numDownloads = numDownloads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paper paper)) return false;
        return Objects.equals(title, paper.title) && Objects.equals(keywords, paper.keywords) && Objects.equals(anAbstract, paper.anAbstract) && Objects.equals(date, paper.date) && Objects.equals(numDownloads, paper.numDownloads) && Objects.equals(numViewsPerDay, paper.numViewsPerDay) && Objects.equals(numLikesPerDay, paper.numLikesPerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, keywords, anAbstract, date, numDownloads, numViewsPerDay, numLikesPerDay);
    }

    @Override
    public String toString() {
        return "Paper{" +
                "doi='" + doi + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", anAbstract='" + anAbstract + '\'' +
                ", date=" + date +
                ", numDownloads=" + numDownloads +
                ", totalNumViews=" + totalNumViews +
                ", totalNumLikes=" + totalNumLikes +
                ", numViewsPerDay=" + numViewsPerDay +
                ", numLikesPerDay=" + numLikesPerDay +
                ", authors=" + authors +
                '}';
    }

    /**
     * @
     */
    public void addAuthor(Author a){
        if(!this.authors.contains(a)) this.authors.add(a);
    }

    /**
     *
     */
    public void addView(){
        Date curr = new Date();
        if(!this.numViewsPerDay.containsKey(curr)){
            this.numViewsPerDay.put(curr, (long) 1);
            this.totalNumViews++;
        } else{
            this.numViewsPerDay.put(curr, (this.numViewsPerDay.get(curr)) + 1);
            this.totalNumViews++;
        }
    }

    public void addLike(){
        Date curr = new Date();
        if(!this.numLikesPerDay.containsKey(curr)){
            this.numLikesPerDay.put(curr, (long) 1);
        } else{
            this.numLikesPerDay.put(curr, (this.numLikesPerDay.get(curr)) + 1);
            this.totalNumLikes++;
        }

    }

    public void addNumDownload(){
       this.numDownloads++;
    }

    public long getNumViewsDay(Date d){
        if(!this.numViewsPerDay.containsKey(d)) return 0;
        return this.numViewsPerDay.get(d);
    }

    public long getNumLikesDay(Date d){
        if(!this.numLikesPerDay.containsKey(d)) return 0;
        return this.numLikesPerDay.get(d);
    }



    public static void main(String[] args) {
        Paper p = new Paper();
        Date d1 = new Date();
        Date d2 = new Date(1, 12, 2020);
        p.addView();
        p.addView();
        p.addView();
        p.addView();
        System.out.println("Num views in " + d1.getDate()+ " " + p.getNumViewsDay(d1));
        System.out.println(p.getNumViewsDay(d2));
    }
}
