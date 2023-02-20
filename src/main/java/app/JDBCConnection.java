package app;

import java.util.*;

import app.outcomeClasses.Age;
import app.outcomeClasses.Category;
import app.outcomeClasses.LGA;
import app.outcomeClasses.LabourForce;
import app.outcomeClasses.NonSchool;
import app.outcomeClasses.School;
import app.outcomeClasses.Team_Members;
import app.outcomeClasses.User_Attributes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/ctg.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        // System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * 
     * @return
     *         Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs(String metric, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // TODO: Add gap score
            // The Query
            String query = "SELECT DISTINCT code, lga_name, lga_type, area, latitude, longitude FROM lga_table_sorted_"
                    + metric + " ORDER BY ranking_metric " + order + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("code");
                String name = results.getString("lga_name");
                String type = results.getString("lga_type");
                int area = results.getInt("area");
                double latitude = results.getDouble("latitude");
                double longitude = results.getDouble("longitude");

                // Create a LGA Object
                LGA lga = new LGA(code, name, type, area, latitude, longitude);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    /**
     * Get nearby LGAs.
     * 
     * @return
     *         Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getNearbyLgas(String LGAName) {

        // First, get the main LGA we're checking against.
        LGA mainLGA = getSingleLGA(LGAName);
        // Save its lat and long into variables
        double lat1 = mainLGA.getLatitude();
        double lon1 = mainLGA.getLongitude();

        // Create the ArrayList of LGA objects we will populate and search against
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT code, lga_name, lga_type, area, latitude, longitude FROM lga_table_sorted_age;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("code");
                String name = results.getString("lga_name");
                String type = results.getString("lga_type");
                int area = results.getInt("area");
                double latitude = results.getDouble("latitude");
                double longitude = results.getDouble("longitude");

                // While processing, compare the lat and long to our Main LGA
                double lat2 = latitude;
                double lon2 = longitude;

                Haversine haversine = new Haversine();
                double distance = haversine.Distance(lat1, lon1, lat2, lon2);

                // Create a LGA Object
                LGA lga = new LGA(code, name, type, area, latitude, longitude, distance);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Now we have an array list full of LGAs with distance, we want to sort it to
        // get the closest ones
        Collections.sort(lgas,
                (o1, o2) -> o1.getDistance().compareTo(o2.getDistance()));

        // Finally we return all of the lga
        return lgas;
    }

    /**
     * Get similar LGAs based on type, population and size.
     * 
     * @return
     *         Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getSimilarLgas(String LGAName) {

        // First, get the main LGA we're checking against.
        LGA mainLGA = getSingleLGA(LGAName);
        // Save its lat and long into variables
        int area1 = mainLGA.getArea();
        int pop1 = mainLGA.getPopulation();
        String type1 = mainLGA.getType();

        // Create the ArrayList of LGA objects we will populate and search against
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    SELECT DISTINCT code, lga_name, lga_type, area, latitude, longitude, population FROM lga_table_sorted_age
                    JOIN (
                            --Number of people in each LGA
                            SELECT lga_code, SUM(num_people) AS population FROM Demographic
                            JOIN Age ON demographic_id = Age.id
                            JOIN Indigenous_Status ON indi_status = status
                            GROUP BY demographic.lga_code
                            ) AS pop ON code = pop.lga_code
                            ;
                    """;

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("code");
                String name = results.getString("lga_name");
                String type = results.getString("lga_type");
                int area = results.getInt("area");
                double latitude = results.getDouble("latitude");
                double longitude = results.getDouble("longitude");
                int population = results.getInt("population");

                // While processing, get the values we want to compare to
                int area2 = area;
                int pop2 = population;
                String type2 = type;

                // Get the difference of area and population as a percentage
                double areaDif = (Math.abs(area1 - area2) * 1.00 / ((area1 + area2) * 2)) * 100;

                double popDif = (Math.abs(pop1 - pop2) * 1.00 / ((pop1 + pop2) * 2)) * 100;

                if ((pop1 + pop2 > 0) && area1 > 0 && area2 > 0 && areaDif < 10 && popDif < 10
                        && type1.equalsIgnoreCase(type2)) {

                    // Create a LGA Object
                    LGA lga = new LGA(code, name, type, area, latitude, longitude, population);
                    // Add the lga object to the array
                    lgas.add(lga);
                }
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    /**
     * Get the chosen category with demographic information.
     * 
     * @return
     *         Returns an ArrayList of category objects with the category being
     *         based on the inputCategory argument
     */
    public ArrayList<Category> getCategories(String inputCategory) {
        // Create the ArrayList of Category objects to return
        ArrayList<Category> categories = new ArrayList<Category>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga_code, lga_name, status_description, sex_description, category, num_people FROM Demographic ";
            query = query + "JOIN lga ON lga_code = LGA.code ";
            query = query + "JOIN " + inputCategory + " ON demographic_id = " + inputCategory + ".id ";
            query = query + "JOIN Gender ON sex_id = sex ";
            query = query + "JOIN Indigenous_Status ON indi_status = status;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // int LGAcode, String indiStatus, String sex, String category, int numPeople

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("lga_code");
                String name = results.getString("lga_name");
                String status = results.getString("status_description");
                String sex = results.getString("sex_description");
                String category = results.getString("category");
                int numPeople = results.getInt("num_people");

                // Create a Category Object
                Category categoryRow = new Category(code, name, status, sex, category, numPeople);

                // Add the lga object to the array
                categories.add(categoryRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return categories;
    }

    /**
     * Get a single LGA from the database.
     * 
     * @return
     *         Returns a single LGA object
     */
    public LGA getSingleLGA(String inputLGA) {

        // Create empty LGA object
        LGA singleLGA = new LGA();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                    SELECT code, lga_name, lga_type, area, latitude, longitude, population
                    , age_score , lf_score, ns_score, sc_score, lga_score FROM LGA
                        JOIN (
                        --Number of people in each LGA
                        SELECT lga_code, SUM(num_people) AS population FROM Demographic
                        JOIN Age ON demographic_id = Age.id
                        JOIN Indigenous_Status ON indi_status = status
                        GROUP BY demographic.lga_code
                        ) AS pop ON code = pop.lga_code
                    JOIN
                        (
                        SELECT DISTINCT age.lga_code
                        , age.lga_code, age.ranking_metric AS age_score, lf.ranking_metric AS lf_score
                        , ns.ranking_metric AS ns_score, sc.ranking_metric AS sc_score
                        , (age.ranking_metric + lf.ranking_metric + ns.ranking_metric + sc.ranking_metric) / 4 AS lga_score
                        FROM lga_sorted_age AS age
                        JOIN lga_sorted_labour_force AS lf ON age.lga_code = lf.lga_code
                        JOIN lga_sorted_non_school AS ns ON age.lga_code = ns.lga_code
                        JOIN lga_sorted_school AS sc ON age.lga_code = sc.lga_code
                        GROUP BY age.lga_code, lga_score
                        ORDER BY lga_score DESC NULLS LAST
                        ) as score ON LGA.code = score.lga_code
                                                """;
            // Add inputLGA to query
            query = query + "WHERE lga_name = '" + inputLGA
                    + "'ORDER BY lga_name ASC, lga_score DESC NULLS LAST LIMIT 1;";
            ;

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("code");
                String name = results.getString("lga_name");
                String type = results.getString("lga_type");
                int area = results.getInt("area");
                double latitude = results.getDouble("latitude");
                double longitude = results.getDouble("longitude");
                int population = results.getInt("population");
                double ageScore = Math.round((results.getDouble("age_score")) * 100.0) / 100.0;
                double lfScore = Math.round((results.getDouble("lf_score")) * 100.0) / 100.0;
                double nsScore = Math.round((results.getDouble("ns_score")) * 100.0) / 100.0;
                double scScore = Math.round((results.getDouble("sc_score")) * 100.0) / 100.0;
                double totalScore = Math.round((results.getDouble("lga_score")) * 100.0) / 100.0;

                // Create a LGA Object
                LGA lga = new LGA(code, name, type, area, latitude, longitude, population, ageScore, lfScore, nsScore,
                        scScore, totalScore);

                // Reassign the lga object
                singleLGA = lga;
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return singleLGA;
    }

    /**
     * Get number of the LGAs in the database.
     * 
     * @return
     *         Returns a integer that equals total LGAs in study.
     */
    public int getLGACount() {

        // Create empty LGA object
        int LGACount = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT COUNT(*) FROM LGA;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int numberLGA = results.getInt("COUNT(*)");
                LGACount = numberLGA;
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return LGACount;
    }

    /**
     * Get the Age table in a wide format.
     * 
     * @return
     *         Returns an ArrayList of LGA objects
     */
    public ArrayList<Age> getAgeTable(String metric, String ratio, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the JDBC connection
        Connection connection = null;
        String sortmetric = metric;
        String tableratio = ratio;
        String sortorder = order;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    SELECT DISTINCT maintable.lga_code, lga_name, status_description, sex_description
                    , maintable."0 - 4", maintable."5 - 9", maintable."10 - 14", maintable."15 - 19", maintable."20 - 24"
                    , maintable."25 - 29", maintable."30 - 34", maintable."35 - 39", maintable."40 - 44", maintable."45 - 49"
                    , maintable."50 - 54", maintable."55 - 59" , maintable."60 - 64" , maintable."65+"
                        """;
            query = query + " FROM lga_sorted_" + sortmetric + " AS sortorder JOIN ( SELECT * FROM age_"
                    + tableratio
                    + " ) AS maintable ON sortorder.lga_code = maintable.lga_code "
                    + "ORDER BY ranking_metric " + sortorder + " NULLS LAST;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("lga_code");
                String name = results.getString("lga_name");
                String status = results.getString("status_description");
                String sex = results.getString("sex_description");
                double ageRange1 = results.getDouble("0 - 4");
                double ageRange2 = results.getDouble("5 - 9");
                double ageRange3 = results.getDouble("10 - 14");
                double ageRange4 = results.getDouble("15 - 19");
                double ageRange5 = results.getDouble("20 - 24");
                double ageRange6 = results.getDouble("25 - 29");
                double ageRange7 = results.getDouble("30 - 34");
                double ageRange8 = results.getDouble("35 - 39");
                double ageRange9 = results.getDouble("40 - 44");
                double ageRange10 = results.getDouble("45 - 49");
                double ageRange11 = results.getDouble("50 - 54");
                double ageRange12 = results.getDouble("55 - 59");
                double ageRange13 = results.getDouble("60 - 64");
                double ageRange14 = results.getDouble("65+");
                // Create an Age Object
                Age age = new Age(code, name, status, sex, ageRange1, ageRange2, ageRange3, ageRange4, ageRange5,
                        ageRange6, ageRange7, ageRange8, ageRange9, ageRange10, ageRange11, ageRange12, ageRange13,
                        ageRange14);

                // Add the lga object to the array
                ages.add(age);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return ages;
    }

    /**
     * Get the labour force data in a wide view with categories as columns.
     * 
     * @return
     * 
     */
    public ArrayList<LabourForce> getLabourForceTable(String metric, String ratio, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LabourForce> labourForces = new ArrayList<LabourForce>();

        // Setup the variable for the JDBC connection
        Connection connection = null;
        String sortmetric = metric;
        String tableratio = ratio;
        String sortorder = order;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT maintable.lga_code, maintable.lga_name, maintable.status_description, maintable.sex_description";
            query = query
                    + ", maintable.employed, maintable.unemployed, maintable.governmentIndustry, maintable.privateIndustry";
            query = query + ", maintable.notInLabourForce FROM lga_sorted_" + sortmetric
                    + " AS sortorder JOIN ( SELECT * FROM labour_force_"
                    + tableratio
                    + " ) AS maintable ON sortorder.lga_code = maintable.lga_code "
                    + "ORDER BY ranking_metric " + sortorder + " NULLS LAST;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code = results.getInt("lga_code");
                String name = results.getString("lga_name");
                String status = results.getString("status_description");
                String sex = results.getString("sex_description");
                double employed = results.getDouble("employed");
                double unemployed = results.getDouble("unemployed");
                double governmentIndustry = results.getDouble("governmentIndustry");
                double privateIndustry = results.getDouble("privateIndustry");
                double notInLabourForce = results.getDouble("notInLabourForce");

                // Create a LabourForce Object
                LabourForce labourForce = new LabourForce(code, name, status, sex, employed, unemployed,
                        governmentIndustry, privateIndustry, notInLabourForce);

                // Add the LabourForce object to the array
                labourForces.add(labourForce);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return labourForces;
    }

    /**
     * Get the total population of a group in a given area from the database.
     * 
     * @return
     *         Returns a total population as an int based on indigenous status and
     *         area that are passed in
     */
    public int getPopulation(String area, String status) {

        // Setup the variable for the JDBC connection
        Connection connection = null;
        int population = 1;
        String filterQuery = "";

        // Set the query which will filter which population to set ratio against
        if (area.equalsIgnoreCase("Australia") && status.equalsIgnoreCase("Indigenous")) {
            filterQuery = "WHERE indi_status = 'i'";
        } else if (area.equalsIgnoreCase("Australia") && status.equalsIgnoreCase("Non-Indigenous")) {
            filterQuery = "WHERE indi_status = 'n'";
        } else if (area.equalsIgnoreCase("Australia") && status.equalsIgnoreCase("Total")) {
            filterQuery = "";
        }
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    --Total population Australia
                    SELECT SUM(num_people) FROM Demographic
                    JOIN Age ON demographic_id = Age.id
                    JOIN Gender ON sex_id = sex
                    JOIN Indigenous_Status ON indi_status = status
                    JOIN Age_Bandwidth ON category = age_bandwidth.bandwidth_age
                        """;
            // Add the filter query onto the end
            query = query + " " + filterQuery;
            ;

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process the result
            while (results.next()) {
                // Lookup the columns we need
                population = results.getInt("SUM(num_people)");
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the population
        return population;
    }

    public ArrayList<School> getSchoolTable(String metric, String ratio, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<School> schooling = new ArrayList<School>();

        // Setup the variable for the JDBC connection
        Connection connection = null;
        String sortmetric = metric;
        String tableratio = ratio;
        String sortorder = order;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    SELECT DISTINCT maintable.lga_code, maintable.lga_name, maintable.status_description, maintable.sex_description
                    , maintable.\"Did not go to school\", maintable.\"Year 8\", maintable.\"Year 10\", maintable.\"Year 12\"
                            """;
            query = query + " FROM lga_sorted_" + sortmetric + " AS sortorder JOIN ( SELECT * FROM school_education_"
                    + tableratio
                    + " ) AS maintable ON sortorder.lga_code = maintable.lga_code "
                    + "ORDER BY ranking_metric " + sortorder + " NULLS LAST;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Loo the columns we need
                int code = results.getInt("lga_code");
                String name = results.getString("lga_name");
                String status = results.getString("status_description");
                String sex = results.getString("sex_description");
                double noSchool = results.getDouble("Did not go to school");
                double year8 = results.getDouble("Year 8");
                double year10 = results.getDouble("Year 10");
                double year12 = results.getDouble("Year 12");

                // Create a School Object
                School school = new School(code, name, status, sex, noSchool, year8, year10, year12);

                // Add the School object to the array
                schooling.add(school);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the school education values
        return schooling;
    }

    public ArrayList<NonSchool> getNonSchoolTable(String metric, String ratio, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<NonSchool> nonSchooling = new ArrayList<NonSchool>();

        // Setup the variable for the JDBC connection
        Connection connection = null;
        String sortmetric = metric;
        String tableratio = ratio;
        String sortorder = order;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    SELECT DISTINCT maintable.lga_code, maintable.lga_name, maintable.status_description, maintable.sex_description, maintable.\"Certificate II\"
                    , maintable.\"Advanced Diploma\", maintable.\"Bachelor Degree\", maintable.\"Graduate Diploma/Certificate\", maintable.\"Postgraduate Degree\"
                        """;
            query = query + " FROM lga_sorted_" + sortmetric + " AS sortorder JOIN ( SELECT * FROM non_school_"
                    + tableratio
                    + " ) AS maintable ON sortorder.lga_code = maintable.lga_code "
                    + "ORDER BY ranking_metric " + sortorder + " NULLS LAST;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Loo the columns we need
                int code = results.getInt("lga_code");
                String name = results.getString("lga_name");
                String status = results.getString("status_description");
                String sex = results.getString("sex_description");
                double certii = results.getDouble("Certificate II");
                double advdip = results.getDouble("Advanced Diploma");
                double gradcert = results.getDouble("Graduate Diploma/Certificate");
                double bachelor = results.getDouble("Bachelor Degree");
                double postgrad = results.getDouble("Postgraduate Degree");

                // Create a School Object
                NonSchool nonSchool = new NonSchool(code, name, status, sex, certii, advdip, gradcert, bachelor,
                        postgrad);

                // Add the School object to the array
                nonSchooling.add(nonSchool);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the school education values
        return nonSchooling;
    }

    public ArrayList<User_Attributes> getUser_Attributes() {

        // Create the array list to return the Strings for the user attributes
        ArrayList<User_Attributes> User_Attributes = new ArrayList<User_Attributes>();

        // Set up the variable for the JDBC connection
        Connection connection = null;
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The query
            String query = "SELECT * FROM User_Attributes";

            // Get result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int Persona_ID = results.getInt("Persona_ID");
                int Age = results.getInt("Age");
                String Ethnicity = results.getString("Ethnicity");
                String Occupation = results.getString("Occupation");
                String Description = results.getString("Description");
                String Goals = results.getString("Goals");
                String Pain_Points = results.getString("Pain_Points");
                String Needs = results.getString("Needs");
                String Experiences = results.getString("Experiences");

                // Create a user attribute object
                User_Attributes user_Attributes = new User_Attributes(Persona_ID, Age, Ethnicity, Occupation,
                        Description, Goals, Pain_Points, Needs, Experiences);

                // Add the user attribute object to the array

                User_Attributes.add(user_Attributes);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());

        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the school education values
        return User_Attributes;
    }

    public ArrayList<Team_Members> getTeam_Members() {
        // Create the array list to return the Strings for the user attributes
        ArrayList<Team_Members> Team_Members = new ArrayList<Team_Members>();

        // Set up the variable for the JDBC connection
        Connection connection = null;
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The query
            String query = "SELECT * FROM Team_Members";

            // Get result
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int student_number = results.getInt("student_number");
                String first_name = results.getString("first_name");
                String last_name = results.getString("last_name");
                String email_ID = results.getString("email_ID");

                Team_Members team_Members = new Team_Members(student_number, first_name, last_name, email_ID);

                Team_Members.add(team_Members);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());

        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the school education values
        return Team_Members;
    }

}