package edu.ufp.inf.paper_author;

import java.time.LocalDate;
import java.util.Objects;

public class PaperConference extends Paper{
    private Integer editionNumber;
    private String local;


    public PaperConference(){

    }

    public PaperConference(String doi, String title, String keywords, String anAbstract, LocalDate date, Integer editionNumber, String local) {
        super(doi, title, keywords, anAbstract, date);
        this.editionNumber = editionNumber;
        this.local = local;
    }

    public PaperConference(String doi, String title, String keywords, String anAbstract, LocalDate date, Long totalLikes, Long totalViews, Long totalDownloads, Integer editionNumber, String local) {
        super(doi, title, keywords, anAbstract, date, totalLikes, totalViews, totalDownloads);
        this.editionNumber = editionNumber;
        this.local = local;
    }


    public Integer getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(Integer editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperConference that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(editionNumber, that.editionNumber) && Objects.equals(local, that.local);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), editionNumber, local);
    }


    @Override
    public String toString() {
        return "PaperConference{" +
                "doi='" + getDoi() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", keywords='" + getKeywords() + '\'' +
                ", anAbstract='" + getAnAbstract() + '\'' +
                ", date=" + getDate() +
                ", numDownloads=" + getNumDownloads() +
                ", totalNumViews=" + getTotalNumViews() +
                ", totalNumLikes=" + getTotalNumLikes() +
                ", editionNumber=" + editionNumber +
                ", local='" + local + '\'' +
                '}';
    }


    public static void main(String[] args) {

    }
}
