package edu.ufp.inf.paper_author;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;

import edu.ufp.inf.Util.Date;

public class Paper implements Serializable {
   private String doi;
   private String title;
   private String keywords;
   private String anAbstract;
   private Date date;
   private Integer graphId = -1;
   private Long numDownloads = 0L;
   private Long totalNumViews = 0L;
   private Long totalNumLikes = 0L;
   private Map<Date, Long> numViewsPerDay = new HashMap<>();
   private Map<Date, Long> numLikesPerDay = new HashMap<>();

   private ArrayList<Author> authors = new ArrayList<>();

   private ArrayList<Paper> quotes = new ArrayList<>();

    public Paper() {
    }

    public Paper(String doi, String title, String keywords, String anAbstract, Date date) {
        this.doi = doi;
        this.title = title;
        this.keywords = keywords;
        this.anAbstract = anAbstract;
        this.date = date;
    }

    public Paper(String doi, String title, String keywords, String anAbstract, Date date, Long totalLikes, Long totalViews, Long totalDownloads) {
        this.doi = doi;
        this.title = title;
        this.keywords = keywords;
        this.anAbstract = anAbstract;
        this.date = date;
        this.totalNumLikes = totalLikes;
        this.totalNumViews = totalViews;
        this.numDownloads = totalDownloads;
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

    public Integer getGraphId() {
        return graphId;
    }

    public void setGraphId(Integer graphId) {
        this.graphId = graphId;
    }

    public Map<Date, Long> getNumViewsPerDay() {
        return numViewsPerDay;
    }

    public void setNumViewsPerDay(Map<Date, Long> numViewsPerDay) {
        this.numViewsPerDay = numViewsPerDay;
    }

    public Map<Date, Long> getNumLikesPerDay() {
        return numLikesPerDay;
    }

    public void setNumLikesPerDay(Map<Date, Long> numLikesPerDay) {
        this.numLikesPerDay = numLikesPerDay;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<Paper> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Paper> quotes) {
        this.quotes = quotes;
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
        return Objects.equals(doi, paper.doi) && Objects.equals(title, paper.title) && Objects.equals(keywords, paper.keywords) && Objects.equals(anAbstract, paper.anAbstract) && Objects.equals(date, paper.date) && Objects.equals(graphId, paper.graphId) && Objects.equals(numDownloads, paper.numDownloads) && Objects.equals(totalNumViews, paper.totalNumViews) && Objects.equals(totalNumLikes, paper.totalNumLikes) && Objects.equals(numViewsPerDay, paper.numViewsPerDay) && Objects.equals(numLikesPerDay, paper.numLikesPerDay) && Objects.equals(authors, paper.authors) && Objects.equals(quotes, paper.quotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi, title, keywords, anAbstract, date, graphId, numDownloads, totalNumViews, totalNumLikes, numViewsPerDay, numLikesPerDay, authors, quotes);
    }

    @Override
    public String toString() {
        return "Paper{" +
                "doi='" + doi + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", anAbstract='" + anAbstract + '\'' +
                ", date=" + date +
                ", graphId=" + graphId +
                ", numDownloads=" + numDownloads +
                ", totalNumViews=" + totalNumViews +
                ", totalNumLikes=" + totalNumLikes +
                ", numViewsPerDay=" + numViewsPerDay +
                ", numLikesPerDay=" + numLikesPerDay +
                ", authors=" + authors +
                ", quotes=" + quotes +
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
        java.util.Date d =new java.util.Date(); // auto day gen
        Date curr = new Date(d.getMonth(), d.getDay(), d.getYear());
        if(!this.numViewsPerDay.containsKey(curr)){
            this.numViewsPerDay.put(curr, (long) 1);
            this.totalNumViews++;
        } else{
            this.numViewsPerDay.put(curr, (this.numViewsPerDay.get(curr)) + 1);
            this.totalNumViews++;
        }
    }

    public void addLike(){
        LocalDate l = LocalDate.now();
        Date curr = new Date(l.getMonthValue(), l.getDayOfMonth(), l.getYear());
        if(!this.numLikesPerDay.containsKey(curr)){
            this.numLikesPerDay.put(curr, (long) 1);
            this.totalNumLikes++;
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

    public long getNumViewsYear(int year){
        long totalViews = 0;
        for (Date d1 : numViewsPerDay.keySet()) {
            long views = numViewsPerDay.get(d1);

            int year2 = d1.year();

            // If the year matches the given year, add views to the total count
            if (year2 == year) {
                totalViews += views;
            }
        }
        return totalViews;
    }

    public long getNumViewsMonth(short month,int year){
        long totalViews = 0;
        for (Date d1 : numViewsPerDay.keySet()) {
            long views = numViewsPerDay.get(d1);

            int year2 = d1.year();
            int m = d1.month();
            if (year2 == year && m == month) {
                totalViews += views;
            }
        }
        return totalViews;
    }

    public long getNumLikesYear(int year){
        long totalLikes = 0;

        for ( Date d1 : numLikesPerDay.keySet()) {
            long views = numLikesPerDay.get(d1);

            int year2 = d1.year();

            if (year2 == year) {
                totalLikes += views;
            }
        }
        return totalLikes;
    }

    public long getNumLikesMonth(int month, int year){
        long totalLikes = 0;
        for (Date d1 : numLikesPerDay.keySet()) {
            long likes = numLikesPerDay.get(d1);

            if (d1.year() == year && d1.month() == month) {
                totalLikes += likes;
            }
        }
        return totalLikes;
    }


    public static void main(String[] args) {
        Paper p = new Paper();
        Date d1 = new Date(1,2,2);
        Date d2 = new Date(1, 12, 2020);

        //p.setDate(d2);
        p.addView();
        p.addView();
        p.addView();
        p.addView();
        p.addLike();
        System.out.println("Num views in " + d1 + " " + p.getNumViewsDay(d1));
       // System.out.println(p.getNumViewsDay(new Date()));


        //System.out.println(p.getNumLikesYear());
       // System.out.println(p.totalNumLikes);


    }
}
