package edu.ufp.inf.database;
import edu.ufp.inf.paper_author.Periodicity;
import edu.princeton.cs.algs4.In;
import edu.ufp.inf.paper_author.*;

import java.security.Key;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestDB {
    static DataBase<Author, Paper> db = new DataBase<>();

    /**
        Insert Authors and Papers through a file
     */
    public static void fillDBA(){
        In fp = new In("../../Downloads/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/authors.txt");
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
           // System.out.println();
            Author a = new Author(LocalDate.of(Integer.parseInt(birthDate[2]),Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[0])), name, address,penName, affiliation, ORCID, scienceID, googleScholarID, ScopusAuthorID);
            a.setIdNumber(Long.parseLong(Author));
            db.insert(a);
            fillAuthorPapers(a,fp);
        }

        //db.listAuthors();

        fp.close();
    }

    private static void fillAuthorPapers(Author a, In fp){
        int numPapers = Integer.parseInt(fp.readLine().split(":")[1].trim());
        for(int i = 0; i < numPapers; i++){
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
            String Title= infoMap.get("Title");
            String Keywords =infoMap.get("anAbstract");
            String anAbstract =infoMap.get("anAbstract");
            String[] Date = infoMap.get("Date").strip().split("-");
            String numDownloads = infoMap.get("numDownloads");
            String totalNumLikes = infoMap.get("TotalNumLikes");
            String totalNumViews = infoMap.get("TotalNumViews");

            Paper p = null;

            if ( DOI.charAt(DOI.length() - 1) == '1'){
                String editionNumber = infoMap.get("editionNumber");
                String local = infoMap.get("Local");
                p = new PaperConference(DOI, Title, Keywords , anAbstract, LocalDate.of(Integer.parseInt(Date[2]),Integer.parseInt(Date[1]), Integer.parseInt(Date[0])), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews),Long.parseLong(numDownloads),Integer.parseInt(editionNumber), local);
            }else{
                String Publisher = infoMap.get("Publisher");
                String Periodicity = infoMap.get("Periodicity");
                String jcrIF = infoMap.get("jcrIF");
                String scopusID = infoMap.get("ScopusID");
                p =  new PaperJournal(DOI, Title, Keywords, anAbstract, LocalDate.of(Integer.parseInt(Date[2]),Integer.parseInt(Date[1]), Integer.parseInt(Date[0])), Long.parseLong(totalNumLikes), Long.parseLong(totalNumViews),Long.parseLong(numDownloads), Publisher, edu.ufp.inf.paper_author.Periodicity.valueOf(Periodicity), Double.parseDouble(jcrIF), scopusID);
            }

            p.getAuthors().add(a);
            a.addPaper(p);

            if (!db.getMapDOI().containsKey(DOI)){
                db.insert(p);
            }
        }
    }


    private void fillDBP(){

    }


    public static void main(String[] args) {
        fillDBA();
        db.listAuthors();
        db.listPapers();
    }
}
