package edu.ufp.inf.database;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.inf.paper_author.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataBaseLog {
    private DataBase db;

    public DataBaseLog(DataBase db) {
        this.db = db;
    }

    public void fillDBA() {
        In fp = new In("../../Downloads/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/authors.txt");
        String s = " ";
        int numAuthors = Integer.parseInt(fp.readLine().split(":")[1].trim());

        for (int i = 0; i < numAuthors; i++) {
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(";");

            // Create a map to store the extracted information
            Map<String, String> infoMap = new HashMap<>();

            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split(": ", 2);

                if (keyValue.length == 2) {
                    infoMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
            String Author = infoMap.get("Author");
            String[] birthDate = infoMap.get("birthDate").strip().split("-");
            System.out.println(birthDate[1] + " " + birthDate[2]);
            String name = infoMap.get("Name");
            String address = infoMap.get("Address");
            String affiliation = infoMap.get("Affiliation");
            String penName = infoMap.get("penName");
            String ORCID = infoMap.get("ORCID");
            String scienceID = infoMap.get("scienceID");
            String googleScholarID = infoMap.get("googleScholarID");
            String ScopusAuthorID = infoMap.get("ScopusAuthorID");
            edu.ufp.inf.paper_author.Author a = new Author(LocalDate.of(Integer.parseInt(birthDate[2]), Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[0])), name, address, penName, affiliation, ORCID, scienceID, googleScholarID, ScopusAuthorID);
            a.setIdNumber(Long.parseLong(Author));
            db.insert(a);
            fillAuthorPapers(a, fp);
        }

        //db.listAuthors();

        fp.close();
    }

    private void fillAuthorPapers(Author a, In fp) {
        int numPapers = Integer.parseInt(fp.readLine().split(":")[1].trim());
        for (int i = 0; i < numPapers; i++) {
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(";");

            // Create a map to store the extracted information
            Map<String, String> infoMap = new HashMap<>();

            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split(": ", 2);

                if (keyValue.length == 2) {
                    infoMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }

            String DOI = infoMap.get("Paper");
            String Title = infoMap.get("Title");
            String Keywords = infoMap.get("anAbstract");
            String anAbstract = infoMap.get("anAbstract");
            String[] Date = infoMap.get("Date").strip().split("-");
            String numDownloads = infoMap.get("numDownloads");
            String totalNumLikes = infoMap.get("TotalNumLikes");
            String totalNumViews = infoMap.get("TotalNumViews");

            Paper p = null;

            if (DOI.charAt(DOI.length() - 1) == '1') {
                String editionNumber = infoMap.get("editionNumber");
                String local = infoMap.get("Local");
                p = new PaperConference(DOI, Title, Keywords, anAbstract, LocalDate.of(Integer.parseInt(Date[2]), Integer.parseInt(Date[1]), Integer.parseInt(Date[0])), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews), Long.parseLong(numDownloads), Integer.parseInt(editionNumber), local);
            } else {
                String Publisher = infoMap.get("Publisher");
                String Periodicity = infoMap.get("Periodicity");
                String jcrIF = infoMap.get("jcrIF");
                String scopusID = infoMap.get("ScopusID");
                p = new PaperJournal(DOI, Title, Keywords, anAbstract, LocalDate.of(Integer.parseInt(Date[2]), Integer.parseInt(Date[1]), Integer.parseInt(Date[0])), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews), Long.parseLong(numDownloads), Publisher, edu.ufp.inf.paper_author.Periodicity.valueOf(Periodicity), Double.parseDouble(jcrIF), scopusID);
            }

            p.getAuthors().add(a);
            a.addPaper(p);

            if (!db.getMapDOI().containsKey(DOI)) {
                db.insert(p);
            }
        }
    }

    public void saveAuthorsTxt(String fn) {
        Out fp = new Out(fn);

        fp.println("nAuuthors: " + db.getMapUID().size());
        for (Object entry : db.getMapUID().keySet()) {
            Long id = (Long) entry;
            Author a = (Author) db.getMapUID().get(id);
            fp.println("Author: " + a.getIdNumber() + " ; birthDate: " + a.getBirthDate() +
                    "; Name: " + a.getName() + "; Address: " + a.getAddress() +
                    "; penName: " + a.getPenName() + " ; Affiliation: " + a.getAffiliation() +
                    "; ORCID: " + a.getOrcID() + "; scienceID: " + a.getScienceID() +
                    "; googleScholarID: " + a.getGoogleScholarID() + "; ScopusAuthorID: " + a.getScopusAuthorID());

            fp.println("nPapers: " + a.getPapers().size());
            for (Paper p : a.getPapers()) {
                if (p instanceof PaperConference) {
                    fp.println("Paper: " + p.getDoi() + "; Title: " + p.getTitle() + " ;Keywords: " + p.getKeywords() +
                            " ; anAbstract: " + p.getAnAbstract() + " ; Date: " + p.getDate() +
                            " ; TotalNumViews: " + p.getTotalNumViews() + " ; TotalNumLikes: " + p.getTotalNumViews() +
                            " ; numDownloads: " + p.getNumDownloads() + "; editionNumber: " + ((PaperConference) p).getEditionNumber() +
                            " ; Local: " + ((PaperConference) p).getLocal());
                } else if (p instanceof PaperJournal) {

                    fp.println("Paper: " + p.getDoi() + "; Title: " + p.getTitle() + " ;Keywords: " + p.getKeywords() +
                            " ; anAbstract: " + p.getAnAbstract() + " ; Date: " + p.getDate() +
                            " ; TotalNumViews: " + p.getTotalNumViews() + " ; TotalNumLikes: " + p.getTotalNumLikes() +
                            " ; numDownloads: " + p.getNumDownloads() + "; Publisher: " + ((PaperJournal) p).getPublisher() +
                            " ; Periodicity: " + ((PaperJournal) p).getPeriodicity() +  "; jcrIF: " + ((PaperJournal) p).getJcrIF() + "; ScopusID: " + ((PaperJournal) p).getScopusID()  );
                }
            }
        }

    }

    public static void main(String[] args) {
        DataBase db = new DataBase();
        DataBaseLog dbLog = new DataBaseLog(db);

        dbLog.fillDBA();

        db.listPapers();

        dbLog.saveAuthorsTxt("/Users/gabrielferreira/Downloads/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/teste.txt");

    }
}

