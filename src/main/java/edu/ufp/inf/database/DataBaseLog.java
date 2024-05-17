package edu.ufp.inf.database;

import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.inf.paper_author.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataBaseLog {

    private DataBase<Author, Paper> db;
    public DataBaseLog(DataBase<Author, Paper> db) {
        this.db = db;
    }

    /**
     * Fills the database with author information from a specified file.
     * The file should contain information about authors in a specific format.
     * Each author's information should be on a new line and key-value pairs separated by semicolons
     * The expected file format:
     * Number of authors: X
     * Author: 1; Name: John Doe; birthDate: 1980-12-15; Address: 123 Main St; Affiliation: University; penName: J.D.; ORCID: 0000-0001-2345-6789; scienceID: 123456; googleScholarID: ABCD1234; ScopusAuthorID: 56789
     * Author: 2; Name: Jane Smith; birthDate: 1975-11-20; Address: 456 Elm St; Affiliation: Institute; penName: J.S.; ORCID: 0000-0002-3456-7890; scienceID: 789012; googleScholarID: EFGH5678; ScopusAuthorID: 12345
     */
    public void fillDB(String fn) {
        In fp = new In(fn);
        String s = " ";
        int numAuthors = Integer.parseInt(fp.readLine().split(":")[1].trim());

        for(int i = 0; i < numAuthors; i++){
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
            String birthDate = infoMap.get("birthDate").strip();
            String name = infoMap.get("Name");
            String address = infoMap.get("Address");
            String affiliation = infoMap.get("Affiliation");
            String penName = infoMap.get("penName");
            String ORCID = infoMap.get("ORCID");
            String scienceID = infoMap.get("scienceID");
            String googleScholarID = infoMap.get("googleScholarID");
            String ScopusAuthorID = infoMap.get("ScopusAuthorID");

            Date d = new Date(birthDate);
            Author a = new Author(d, name, address,penName, affiliation, ORCID, scienceID, googleScholarID, ScopusAuthorID);
            a.setIdNumber(Integer.parseInt(Author));
            db.insert(a);
            fillAuthorPapers(a,fp);
        }
        fp.close();
    }

    /**
     * Fills the database with papers related to a given author.
     *
     * @param a  The author for whom papers are being filled.
     * @param fp The file input stream to read paper data from.
     */
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
            String Keywords = infoMap.get("Keywords");
            String anAbstract = infoMap.get("anAbstract");
            String Date = infoMap.get("Date").strip();
            String numDownloads = infoMap.get("numDownloads");
            String totalNumLikes = infoMap.get("TotalNumLikes");
            String totalNumViews = infoMap.get("TotalNumViews");

            Paper p = null;

            if (DOI.charAt(DOI.length() - 1) == '1') {
                String editionNumber = infoMap.get("editionNumber");
                String local = infoMap.get("Local");
                p = new PaperConference(DOI, Title, Keywords, anAbstract, new Date(Date), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews), Long.parseLong(numDownloads), Integer.parseInt(editionNumber), local);
            } else {
                String Publisher = infoMap.get("Publisher");
                String Periodicity = infoMap.get("Periodicity");
                String jcrIF = infoMap.get("jcrIF");
                String scopusID = infoMap.get("ScopusID");
                p = new PaperJournal(DOI, Title, Keywords, anAbstract, new Date(Date), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews), Long.parseLong(numDownloads), Publisher, edu.ufp.inf.paper_author.Periodicity.valueOf(Periodicity), Double.parseDouble(jcrIF), scopusID);
            }

            p.getAuthors().add(a);
            a.addPaper(p);

            if (!db.getMapDOI().containsKey(DOI)) {
                db.insert(p);
            }else{
                db.getMapDOI().get(DOI).addAuthor(a);
            }
        }
    }

    public void saveAuthorsTxt(String fn) {
        Out fp = new Out(fn);

        fp.println("nAuthors: " + db.getMapUID().size());
        for (Integer id : db.getMapUID().keySet()) {
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

        dbLog.fillDB("./data/db.txt");
        db.listPapers();


        dbLog.saveAuthorsTxt("./data/db.txt");

    }
}

