package app.webpages;

import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.LGA;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * This page was used for initial and creation of LGA Search feature and is no
 * longer in use.
 *
 */
public class PageLGASearch implements Handler {

  // URL of this page relative to http://localhost:7001/
  public static final String URL = "/LGASearch.html";

  @Override
  public void handle(Context context) throws Exception {

    // // Look up LGA from JDBC
    // JDBCConnection jdbc = new JDBCConnection();
    // LGA lga = jdbc.getSingleLGA(searchLGA);

    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some header information including css and js
    html = html
        + """
              <head>
              <meta charset='UTF-8' />
              <meta http-equiv='X-UA-Compatible' content='IE=edge' />
              <meta name='viewport' content='width=device-width, initial-scale=1.0' />
              <title>Closing The Gap</title>
              <!-- Link to CSS general stylesheet -->
              <link rel='stylesheet' href='overallStyle.css' />
              <!-- Link to CSS specific stylesheet -->
              <link rel='stylesheet' href='LGASearch.css' />
              <!-- Link to Google fonts -->
              <!-- TODO: Fix Font -->
              <link rel='preconnect' href='https://fonts.googleapis.com' />
              <link rel='preconnect' href='https://fonts.gstatic.com' crossorigin />
              <link
                href='https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,400;0,500;0,700;1,400;1,500&display=swap'
                rel='stylesheet'
              />
            </head>
                  """;
    ;

    // Add the body
    html = html + "<body>";

    // Add the topnav
    // This uses a Java v15+ Text Block
    html = html + """
          <div class='navbar'>
          <div class='topLogo'><a href='/'>Closing The Gap</a></div>
          <div class='nav1 navItem'><a href='/learnMore.html'>Want to know more?</a></div>
          <div class='nav2 navItem'><a href='/wantToHelp.html'>Want to help?</a></div>
          <div class='nav3 navItem'><a href='/aboutUs.html'>About Us</a></div>
          <div class='nav4'>
            <div>
            </div>
          </div>
        </div>
              """;

    // Add header content block
    html = html + """
            <div class='header'>
                <h1>Subtask 3.2</h1>
            </div>
        """;

    // Add Div for page Content
    html = html + "<div class='content'>";

    // Add dropdown search
    html = html + """
        <div id='LGASearch' class='contentBlock'>
        <div class='dropdown'>
          <button onclick='dropdown()' class='dropbtn'>Search by LGA</button>
          <div id='myDropdown' class='dropdown-content'>
            <input
              type='text'
              placeholder='Search..'
              id='myInput'
              onkeyup='filterDropdown()'
            />
            """;

    // Look up some information from JDBC
    // First we need to use your JDBCConnection class
    JDBCConnection jdbc = new JDBCConnection();

    // Next we will ask this *class* for the LGAs
    ArrayList<LGA> lgas = jdbc.getLGAs("age", "desc");

    // Print the LGAs into anchor tags and populate the dropdown list
    for (LGA lga : lgas) {
      html = html + "<a href='#' id='" + lga.getName() + "'>" + lga.getName() + "</a>";
    }

    // Close dropdown list and associated divs
    html = html + """
          </div>
        </div>
              """;

    // Open div that will hold dynamic LGA content and give it default content
    html = html
        + """
            <div id='dynamicContent'>
            <h2>Albury</h2>
            <div class='LGASearchInfo'>
            <p><b>Code: </b></p> 10050
            <p><b>Type: </b></p> C
            <p><b>Area: </b></p> 305 km2
            </div>
            <div class='LGASearchInfo'>
             <p><b>State: </b></p> NSW
             <p><b>Population: </b></p> 51102
            </div>
            </div>
                """;

    // Lga Score number
    html = html + """
        <div class='LGASearchInfo'>
        <p><b>LGA Score: </b></p>
        <div id='lgaScore'> 17.79
        </div>
        </div>
           """;

    // Nearby LGAs
    html = html + """
        <div class='LGASearchInfo' id='compareLGAs'>
        <p><b>Nearby LGAs: </b></p>
        <b>Name: </b> <div class='nearbyName'></div>
        <b>Distance: </b> <div class='nearbyDist'></div>
        <b>Name: </b> <div class='nearbyName'></div>
        <b>Distance: </b> <div class='nearbyDist'></div>
        <b>Name: </b> <div class='nearbyName'></div>
        <b>Distance: </b> <div class='nearbyDist'></div>
        <b>Name: </b> <div class='nearbyName'></div>
        <b>Distance: </b> <div class='nearbyDist'></div>
        <b>Name: </b> <div class='nearbyName'></div>
        <b>Distance: </b> <div class='nearbyDist'></div>
        </div>
           """;

    // Close contentBlock
    html = html + "</div>";

    html = html
        + """
            <div id='LGASearchFilter' class='contentBlock'>
            <form id='scoreCheckboxes'>
            <input type='checkbox' id='lgaScoreAge' name='lgaScoreAge' value='3.42'> <label for='lgaScoreAge'> Age </label><br>
            <input type='checkbox' id='lgaScoreLf' name='lgaScoreLf' value='2.36'> <label for='lgaScoreLf'> Employment </label><br>
            <input type='checkbox' id='lgaScoreNs' name='lgaScoreNs' value='27.10'> <label for='lgaScoreNs'> Higher Education </label><br>
            <input type='checkbox' id='lgaScoreSc' name='lgaScoreSc' value='38.28'> <label for='lgaScoreSc'> School Completion</label><br><br>
            </form> </div>
            """;

    // Close Content div
    html = html + "</div>";

    // Footer
    html = html + """
            <div class='footer'>
                <p>COSC2803 - Studio Project Starter Code</p>
            </div>
        """;

    // Add Javascript
    html = html + "<script src='LGASearch.js'></script>";

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
  }

}
