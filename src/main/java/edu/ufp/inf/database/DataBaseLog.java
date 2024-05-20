package edu.ufp.inf.database;

import edu.ufp.inf.Util.Date;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.ufp.inf.paper_author.*;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataBaseLog {

    private DataBase<Author, Paper> db = new DataBase<>();
    public DataBaseLog(DataBase<Author, Paper> db) {
        this.db = db;
    }
    public DataBaseLog(String fn, DataBase<Author, Paper> db) {
        this.db = db;
        fillDB(fn);
    }

    public DataBaseLog(String fa, String fp, DataBase<Author, Paper> db) {
        this.db = db;
        readDBin(fa, fp);
    }



    /**
     * Fills the database with data from a txt file.
     * The file should contain information  in a specific format.
     * The expected file format:
     * Number of authors: X
     * Author: 1; Name: John Doe; birthDate: 1980-12-15; Address: 123 Main St; Affiliation: University; penName: J.D.; ORCID: 0000-0001-2345-6789; scienceID: 123456; googleScholarID: ABCD1234; ScopusAuthorID: 56789
     * Paper: reeeeeg1; Title: A horta ;Keywords: null ; anAbstract: null ; Date: 11/15/2020 ; TotalNumViews: 50000 ; TotalNumLikes: 50000 ; numDownloads: 9873100; editionNumber: 2 ; Local: Porto
     * Author: 2; Name: Jane Smith; birthDate: 1975-11-20; Address: 456 Elm St; Affiliation: Institute; penName: J.S.; ORCID: 0000-0002-3456-7890; scienceID: 789012; googleScholarID: EFGH5678; ScopusAuthorID: 12345
     */
    public void fillDB(String fn) {
        In fp = new In(fn);
        String s = " ";
        Integer idMax = -1;
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
            if(Integer.parseInt(Author) > idMax){
                idMax = Integer.parseInt(Author);
            }
        }
        db.setuID(idMax + 1);
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

    /**
     * Store the database in a txt file.
     * File format:
     * nAuthors: X
     * Author: 1; Name: John Doe; birthDate: 1980-12-15; Address: 123 Main St; Affiliation: University; penName: J.D.; ORCID: 0000-0001-2345-6789; scienceID: 123456; googleScholarID: ABCD1234; ScopusAuthorID: 56789
     * nPapers : X
     * Paper: reeeeeg1; Title: A horta ;Keywords: null ; anAbstract: null ; Date: 11/15/2020 ; TotalNumViews: 50000 ; TotalNumLikes: 50000 ; numDownloads: 9873100; editionNumber: 2 ; Local: Porto
     * Author: 2; Name: Jane Smith; birthDate: 1975-11-20; Address: 456 Elm St; Affiliation: Institute; penName: J.S.; ORCID: 0000-0002-3456-7890; scienceID: 789012; googleScholarID: EFGH5678; ScopusAuthorID: 12345
     */
    public void saveDBTxt(String fn) {
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

    /**
     * Saves the database information to binary files.
     *
     * @param fa the name of the file to save author information
     * @param fp the name of the file to save paper information
     */
    public void saveDBBin(String fa, String fp) {
        saveAuthorsBin(fa);
        savePapersBin(fp);
    }

    /**
     * Saves author information to a binary file.
     *
     * @param fa the name of the file to save author information
     */
    private void saveAuthorsBin(String fa) {
        try {
            File f = new File(fa);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);


            //Write number of authors
            oos.write(db.getMapUID().size());
            for(Object a : db.getMapUID().keySet()){
                oos.writeObject(db.getMapUID().get(a));
            }
            oos.flush();
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Saves paper information to a binary file.
     *
     * @param fp the name of the file to save paper information
     */
    private void savePapersBin(String fp) {
        try {
            File f = new File(fp);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            //Write number of papers
            oos.write(db.getMapDOI().size());
            for(Object a : db.getMapDOI().keySet()){
                oos.writeObject(db.getMapDOI().get(a));
            }
            oos.flush();
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /**
     * Reads the database information from binary files.
     *
     * @param fa the name of the file to read author information
     * @param fp the name of the file to read paper information
     */
    public void readDBin(String fa, String fp) {
        readAuthorsBin(fa);
        readPapersBin(fp);
    }

    /**
     * Reads author information from a binary file.
     *
     * @param fa the name of the file to read author information
     */
    private void readAuthorsBin(String fa){
        try {
            File f = new File(fa);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            int num_authors = ois.read();
            System.out.println(num_authors);
            for (int i = 0; i < num_authors; i++){
                System.out.println(i);
                Author a = (Author) ois.readObject();
                db.insert(a);
            }
            ois.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads paper information from a binary file.
     *
     * @param fp the name of the file to read paper information
     */
    private void readPapersBin(String fp){
        try {
            File f = new File(fp);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            int numPapers = ois.read();
            System.out.println(numPapers);
            for (int i = 0; i < numPapers; i++){
                System.out.println(i);
                Paper p = (Paper) ois.readObject();
                db.insert(p);
            }
            ois.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        DataBase db = new DataBase();
        DataBaseLog dbLog = new DataBaseLog(db);

       // dbLog.fillDB("./data/db.txt");
       // dbLog.saveDBBin("./data/authors.bin", "./data/papers.bin");
        dbLog.readDBin("./data/authors.bin", "./data/papers.bin");
        System.out.println( db.listPapers());


       // dbLog.saveAuthorsTxt("./data/db.txt");

    }
}

