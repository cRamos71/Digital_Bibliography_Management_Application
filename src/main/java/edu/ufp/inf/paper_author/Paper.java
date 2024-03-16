package edu.ufp.inf.paper_author;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Paper {
   private String title;
   private String keywords;
   private String anAbstract;
   private Date date;
   private Long numDownloads;
   private Map<Date, Long> numViewsPerDay;
   private Map<Date, Long> numLikesPerDay;

   private ArrayList<Author> authors = new ArrayList<>();

    public Paper() {
    }

    public Paper(String title, String keywords, String anAbstract, Date date, Author a) {
        this.title = title;
        this.keywords = keywords;
        this.anAbstract = anAbstract;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
                "title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", anAbstract='" + anAbstract + '\'' +
                ", date=" + date +
                ", numDownloads=" + numDownloads +
                ", numViewsPerDay=" + numViewsPerDay +
                ", numLikesPerDay=" + numLikesPerDay +
                '}';
    }

    /**
     * @
     */
    public void addAuthor(Author a){
        if(!this.authors.contains(a)) this.authors.add(a);
    }

    public static void main(String[] args) {

    }
}
