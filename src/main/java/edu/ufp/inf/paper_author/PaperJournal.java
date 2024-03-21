package edu.ufp.inf.paper_author;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class PaperJournal extends Paper{
    private String publisher;
    private Periodicity periodicity;
    private double jcrIF;
    private String scopusID;


    public PaperJournal(String title, String keywords, String anAbstract, LocalDate date, Author a, String publisher, Periodicity periodicity, double jcrIF, String scopusID) {
        super(title, keywords, anAbstract, date, a);
        this.publisher = publisher;
        this.periodicity = periodicity;
        this.jcrIF = jcrIF;
        this.scopusID = scopusID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public double getJcrIF() {
        return jcrIF;
    }

    public void setJcrIF(double jcrIF) {
        this.jcrIF = jcrIF;
    }

    public String getScopusID() {
        return scopusID;
    }

    public void setScopusID(String scopusID) {
        this.scopusID = scopusID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperJournal that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(jcrIF, that.jcrIF) == 0 && Objects.equals(publisher, that.publisher) && periodicity == that.periodicity && Objects.equals(scopusID, that.scopusID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publisher, periodicity, jcrIF, scopusID);
    }

    @Override
    public String toString() {
        return "PaperJournal{" +
                "publisher='" + publisher + '\'' +
                ", periodicity=" + periodicity +
                ", jcrIF=" + jcrIF +
                ", scopusID='" + scopusID + '\'' +
                '}';
    }

    public static void main(String[] args) {

    }
}
