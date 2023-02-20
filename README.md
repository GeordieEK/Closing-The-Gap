# Re-upload of Closing The Gap - School Project (April 2022)

Current Libraries:

- org.xerial.sqlite-jdbc (SQLite JDBC library)
- javalin (lightweight Java Webserver)
- thymeleaf (HTML template) - https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html

Libraries required as dependencies:

- By javalin
  - slf4j-simple (lightweight logging)
- By xerial/jdbc
  - sqlite-jdbc

# Building & Running the code

There are two places code can be run from

1. The **main** web server program
2. the **optional** helper program to use JDBC to load your SQLite database from the CSVs using Java

## Running the Main web server

You can run the main webserver program similar to the project workshop activities

1. Open this project within VSCode
2. Allow VSCode to read the pom.xml file

- Allow the popups to run and "say yes" to VSCode configuring the build
- Allow VSCode to download the required Java libraries

3. To Build & Run

- Open the `src/main/java/app/App.java` source file, and select "Run" from the pop-up above the main function

4. Go to: http://localhost:7001

## Running the Helper Program

The helper program in `src/main/java/helper/ProcessCSV.java` can be run separetly from the main webserver. This gives a demonstration of how you can use Java to read the provided CSV files and store the information in an SQLite database. This example transforms the data in the `database/lga_indigenous_status_by_age_by_sex_census_2016.csv` file to match the format of the `PopulationStatistics` entity as given in the example ER Model for Milestone 1 for the Cloing-the-Gap social challenge. That is, the code converts the columns of the CSV into rows that can be loaded into the SQLite database using `INSERT` statements.

You can run the optional helper program by

1. Open this `src/main/java/helper/ProcessCSV.java` source file
1. Select "Debug" from the pop-up above the main function (or "Debug Java" from the top-right dropdown)
1. Allow the program to run

You can modify this file as you wish, for other tables and CSVs. When modifying you may need to pay attention to:

- `DATABASE` field to change the database location
- `CSV_FILE` to change which CSV file is bring read
- `categoty`, `status`, and `sex` arrays which should match the setup of the CSV file being read
- `INSERT` statement construction to:
  - Change the table being used
  - Column data being stored

## Testing on GitHub Codespaces

In Semester 1 2022, you will have access to GitHub Codespaces through the RMIT GitHub Organisation. It is highly recommended to test that your code is fully functional in Codespaces.

GitHub Codespaces will be used as the common location to test and verify your studio project. Specifically, GitHub Codespaces will be used to verify your project in the event the code does not correctly function on the local assessor's computer.

# DEV Container for GitHub Codespaces

The `.devcontainer` folder contains configuration files for GitHub Codespaces.
This ensures that when the GitHub classroom is cloned, the workspace is correctly configured for Java (V16) and with the required VSCode extensions.
This folder will not affect a _local_ VSCode setup on a computer.

**🚨 DO NOT MODIFY THE CONTENTS OF THIS FOLDER. 🚨**

# Authors

- Dr. Timothy Wiley, School of Computing Technologies, STEM College, RMIT University.
- Prof. Santha Sumanasekara, School of Computing Technologies, STEM College, RMIT University.

Copyright RMIT University (c) 2022
