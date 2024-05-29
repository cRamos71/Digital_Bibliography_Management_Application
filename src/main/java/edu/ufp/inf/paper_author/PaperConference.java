package edu.ufp.inf.paper_author;
import edu.ufp.inf.Util.Date;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a conference paper with specific attributes such as edition number and location.
 */
public class PaperConference extends Paper implements Serializable {
    /**
     * edition Number
     */
    private Integer editionNumber;
    /**
     * Local of the Conference
     */
    private String local;


    public PaperConference(){

    }

    public PaperConference(String doi, String title, String keywords, String anAbstract, Date date, Integer editionNumber, String local) {
        super(doi, title, keywords, anAbstract, date);
        this.editionNumber = editionNumber;
        this.local = local;
    }

    public PaperConference(String doi, String title, String keywords, String anAbstract, Date date, Long totalLikes, Long totalViews, Long totalDownloads, Integer editionNumber, String local) {
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
