package helper;

import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Closing-the-Gap data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are in the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statments!
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * 
 */
public class CreateDemographicID {

   // MODIFY these to load/store to/from the correct locations

   private static final String DATABASE = "jdbc:sqlite:database/ctg.db";
   private static final String CSV_FILE = "database/lgas16.csv";

   public static void main(String[] args) {

      String sex[] = {
            "'f'",
            "'m'"
      };

      String status[] = {
            "'i'",
            "'n'",
            "'u'"
      };

      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(CSV_FILE));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         connection = DriverManager.getConnection(DATABASE);

         // Clear Table Code ~~~~~~~~~~~~~~~~~~~~~~
         // Prepare a new SQL Query & Set a timeout
         Statement clearStatement = connection.createStatement();
         // statement.setQueryTimeout(30);

         String clearQuery = """
               DELETE FROM Demographic;
               """;

         // Execute the rebuild statement
         System.out.println("Executing: " + clearQuery);
         clearStatement.execute(clearQuery);

         // End clear table code ~~~~~~~~~~~~~~~~~~~

         int demographicID = 00000000;

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();

            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Skip lga_name
            String lgaName = rowScanner.next();

            // Skip lga_type
            String lgaType = rowScanner.next();

            // Skip lga_area
            String lgaArea = rowScanner.next();

            // Skip lga_latitude
            String lgaLatitude = rowScanner.next();

            // demographicID
            demographicID++;

            while (rowScanner.hasNext()) {
               // Get lga_longitude
               String lgaLongitude = rowScanner.next();
               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();
               // statement.setQueryTimeout(30);

               for (int i = 0; i < 6; ++i) {

                  // Create Insert Statement
                  String query = "INSERT into Demographic VALUES ("
                        + demographicID + ","
                        + sex[indexSex] + ","
                        + status[indexStatus] + ","
                        + lgaCode +
                        ")";

                  // Execute the INSERT
                  System.out.println("Executing: " + query);
                  statement.execute(query);

                  // Update indices - go to next sex
                  indexSex++;
                  if (indexSex >= sex.length) {
                     // Go to next status
                     indexSex = 0;
                     indexStatus++;

                     if (indexStatus >= status.length) {
                        // Go to next Category
                        indexStatus = 0;
                        indexCategory++;
                     }
                  }

                  ++demographicID;

               }

            }

            // // Go through the data for the row
            // // If we run out of categories, stop for safety (so the code doesn't crash)
            // while (rowScanner.hasNext() && indexType < type.length) {
            // String count = rowScanner.next();

            // // Prepare a new SQL Query & Set a timeout
            // Statement statement = connection.createStatement();
            // // statement.setQueryTimeout(30);

            // // Create Insert Statement
            // String query = "INSERT into LGA VALUES ("
            // + lgaCode + ","
            // + "'" + lgaName + "',"
            // + "'" + lgaType + "',"
            // + lgaArea + ","
            // + lgaLatitude + ","
            // + lgaLongitude + ")";

            // // Execute the INSERT
            // System.out.println("Executing: " + query);
            // statement.execute(query);

            // // Update indices - go to next sex
            // // indexSex++;
            // // if (indexSex >= sex.length) {
            // // // Go to next status
            // // indexSex = 0;
            // // indexStatus++;

            // // if (indexStatus >= status.length) {
            // // // Go to next Category
            // // indexStatus = 0;
            // // indexCategory++;
            // // }
            // // }
            // row++;
            // }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}
