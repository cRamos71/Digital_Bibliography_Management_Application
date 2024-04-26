package edu.ufp.inf.database;

import edu.princeton.cs.algs4.In;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestDB {
    static DataBase<Author, Paper> db = new DataBase<>();

    /**
        Insert Authors and Papers through a file
     */
    public static void fillDB(){
        In fp = new In("../../Downloads/Digital_Bibliography_Management_Application_42855_20221211538_aed2_lp2_202324/data/authors.txt");
        String s = " ";
       // fp.readAll();
        //System.out.println(fp.readAll() + " " );
        int numUsers = Integer.parseInt(fp.readLine().split(":")[1].trim());

        for(int i = 0; i < numUsers; i++){
            // Split the input string into key-value pairs
            String[] pairs = fp.readLine().split(";");

            // Create a map to store the extracted information
            Map<String, String> infoMap = new HashMap<>();

            // Process each key-value pair
            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split(": ", 2);

                // Store key-value pair in the map
                if (keyValue.length == 2) {
                    infoMap.put(keyValue[0], keyValue[1]);
                }
            }

            // Extract and print the required information
            String[] birthDate = infoMap.get("user1-birthDate").strip().split("-");
            System.out.println(birthDate[1] + " " + birthDate[2]);
            String name = infoMap.get("Name");
            String address = infoMap.get("Address");
            String affiliation = infoMap.get("Affiliation");
            String penName = infoMap.get("penName");
            String ORCID = infoMap.get("ORCID");
            String scienceID = infoMap.get("scienceID");
            String googleScholarID = infoMap.get("googleScholarID");
            String ScopusAuthorID = infoMap.get("ScopusAuthorID");
            //System.out.println();
            db.insert(new Author(-1L, LocalDate.of(Integer.parseInt(birthDate[2]),Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[0])), name, address, affiliation,penName, ORCID, scienceID, googleScholarID, ScopusAuthorID));
        }

        db.listAuthors();



       /* while( (s = fp.readLine()) != null){
            String s2[] = s.strip().split(":");
            //String s = fp.readLine();
            System.out.println(s.strip().split(":")[1]);
        }
        //String s = fp.readLine();
       // System.out.println(s.strip().split(":")[0]);
        //&String[] a = s.strip().split(":");

        /*System.out.println(a[0]);

        for (String s2: a){
            System.out.println(s2);
        }*/
        fp.close();
    }



    public static void main(String[] args) {
        fillDB();
    }
}
