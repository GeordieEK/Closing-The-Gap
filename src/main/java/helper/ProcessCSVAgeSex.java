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
public class ProcessCSVAgeSex {

      // MODIFY these to load/store to/from the correct locations

      private static final String DATABASE = "jdbc:sqlite:database/ctg.db";
      private static final String CSV_FILE = "database/lga_indigenous_status_by_age_by_sex_census_2016.csv";

      public static void main(String[] args) {
            // The following arrays define the order of columns in the INPUT CSV.
            // The order of each array MUST match the order of the CSV.
            // These are specific to the given file and should be changed for each file.
            // This is a *simple* way to help you get up and running quickly wihout being
            // confusing
            String category[] = {
                        "0 - 4",
                        "5 - 9",
                        "10 - 14",
                        "15 - 19",
                        "20 - 24",
                        "25 - 29",
                        "30 - 34",
                        "35 - 39",
                        "40 - 44",
                        "45 - 49",
                        "50 - 54",
                        "55 - 59",
                        "60 - 64",
                        "65+"
            };
            String status[] = {
                        "i",
                        "n",
                        "u"
            };
            String sex[] = {
                        "f",
                        "m"
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

                  // Initialise Table ~~~~~~~~~~~~~~~~~~~~~~
                  // Prepare a new SQL Query & Set a timeout
                  Statement clearStatement = connection.createStatement();
                  // statement.setQueryTimeout(30);

                  // This code empties the table and adds the demographic columns we will need to
                  // match the demographic_id
                  String clearQuery = """
                              DELETE FROM Age;
                              """;

                  String columnQuery1 = """
                              ALTER TABLE Age
                              ADD sex CHAR(1);
                                    """;
                  String columnQuery2 = """
                              ALTER TABLE Age
                              ADD status CHAR(1);
                                    """;
                  String columnQuery3 = """
                              ALTER TABLE Age
                              ADD lga INTEGER;
                                    """;

                  // Execute the initalise statement
                  System.out.println("Executing: " + clearQuery);
                  clearStatement.execute(clearQuery);

                  // Execute the 1st column statement
                  System.out.println("Executing: " + columnQuery1);
                  clearStatement.execute(columnQuery1);

                  // Execute the 2nd column statement
                  System.out.println("Executing: " + columnQuery2);
                  clearStatement.execute(columnQuery2);

                  // Execute the 3rd column statement
                  System.out.println("Executing: " + columnQuery3);
                  clearStatement.execute(columnQuery3);

                  // String columnQuery = "columnQuery";

                  // // Loop through the column statements
                  // for (int i = 1; i < 4; ++i) {
                  // columnQuery = "columnQuery" + i;
                  // // Execute the column addition statement
                  // System.out.println("Executing: " + columnQuery);
                  // clearStatement.execute(columnQuery);
                  // }

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

                        // Go through the data for the row
                        // If we run out of categories, stop for safety (so the code doesn't crash)
                        while (rowScanner.hasNext() && indexCategory < category.length) {
                              String count = rowScanner.next();

                              // Prepare a new SQL Query & Set a timeout
                              Statement statement = connection.createStatement();
                              // statement.setQueryTimeout(30);

                              // Create Insert Statement that populates demographic attributes without
                              // demographic ID
                              String query = "INSERT into Age (category, status, sex, lga, num_people) VALUES ("
                                          + "'" + category[indexCategory] + "',"
                                          + "'" + status[indexStatus] + "',"
                                          + "'" + sex[indexSex] + "',"
                                          + lgaCode + ","
                                          + count + ")";

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

                              ++row;

                        }
                  }

                  // Insert Demographic ID
                  // Prepare a new SQL Query & Set a timeout
                  Statement UpdateDelete = connection.createStatement();

                  // This statement inserts demographic IDs that match the sex, indigenous status
                  // and lga
                  String idInsert = """
                              UPDATE Age
                              SET id = (SELECT Demographic.demographic_id
                              FROM Demographic
                              WHERE Demographic.sex_id = Age.sex AND
                                    Demographic.indi_status = Age.status AND
                                    Demographic.lga_code = Age.lga)
                                 """;

                  // This statement removes the sex, indigenous status and lga columns as they are
                  // superfluous having been matched with the demographic IDs
                  String columnDelete = """
                              ALTER TABLE Age
                              DROP COLUMN sex, status, lga;
                                    """;

                  // Execute the update statement, updating with matched demographic_id
                  System.out.println("Executing: " + idInsert);
                  UpdateDelete.execute(idInsert);

                  // Execute the column delete statement, removing superfluous columns
                  System.out.println("Executing: " + columnDelete);
                  UpdateDelete.execute(columnDelete);

            } catch (Exception e) {
                  e.printStackTrace();
            }

      }
}
