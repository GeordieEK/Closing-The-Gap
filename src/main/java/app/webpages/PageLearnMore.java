package app.webpages;

import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.LGA;
import app.outcomeClasses.Team_Members;

// import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageLearnMore implements Handler {

  // URL of this page relative to http://localhost:7001/
  public static final String URL = "/learnMore.html";

  @Override
  public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html = html + "<head>" +
        "<title>Subtask 2.1</title>";

    // Add CSS
    html = html
        + """
                    <!-- Link to CSS general stylesheet -->
            <link rel='stylesheet' href='overallStyle.css' />
            <!-- Link to CSS specific stylesheets -->
            <link rel='stylesheet' href='learnMoreStyle.css' />
            <link rel='stylesheet' href='LGASearch.css' />
            <!-- Link to Google fonts -->
            <!-- TODO: Fix Font -->
            <link rel='preconnect' href='https://fonts.googleapis.com' />
            <link rel='preconnect' href='https://fonts.gstatic.com' crossorigin />
            <link
              href='https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,400;0,500;0,700;1,400;1,500&display=swap'
              rel='stylesheet'
            />
            <!-- Tabulator CSS -->
            <link href='tabulator.css' rel='stylesheet' />
                """;

    html = html + "</head>";

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
          <div class='headerItem'>
            <div><h1>See for yourself.</h1></div>
          </div>
        </div>
              """;

    // Add Div for page Content
    html = html + "<div class='content'>";

    // Add a segment header
    html = html + """
        <div id='LGASearchHeader' class='segmentHeader'><h2>Search By LGA</h2></div>
            """;

    // Add dropdown search
    html = html + """
        <div id='LGASearch' class='contentBlock'>
        <div class='dropdown'>
          <button onclick='dropdown()' class='dropbtn'><img
          width='30px'
          src='searchiconsmall.png'
          alt=''
        /></button>
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
      html = html + "<a id='" + lga.getName() + "'>" + lga.getName() + "</a>";
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

    // Nearby LGA Names
    html = html + """
        <div class='LGASearchInfo' id='compLgas'>
        <p><b>Nearby LGAs: </b></p>
        <div id='nearbyLgaList'>
        <div id='nearbyNameList'>
        <div class='nearbyName'>Wodonga</div>
        <div class='nearbyName'>Orange</div>
        <div class='nearbyName'>Greater Hume Shire</div>
        <div class='nearbyName'>Towong</div>
        <div class='nearbyName'>Wangaratta</div>
        </div>
           """;

    // Nearby LGA Distances
    html = html + """
        <p></p>
        <div id='nearbyDistList'>
        <div class='nearbyDist'>15.88 km&#178</div>
        <div class='nearbyDist'>31.35 km&#178</div>
        <div class='nearbyDist'>35.68 km&#178</div>
        <div class='nearbyDist'>67.15 km&#178</div>
        <div class='nearbyDist'>79.12 km&#178</div>
        </div>
        </div>
        </div>
           """;

    // Similar LGAs
    html = html + """
        <div class='LGASearchInfo'>
        <p><b>Similar LGAs: </b></p>
        <div id='similarLgaList'>
        <div>
        <b></b> <div class='similarName'><a>Wodonga</a></div>
        <div class='similarName'><a>Orange</a></div>
        <div class='similarName'><a>Albury</a></div>
        </div>
        </div>
        </div>
           """;

    // Close contentBlock
    html = html + "</div>";

    // Add the checkboxes to filter the LGA score
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

    // Add content divider
    html = html + """
        <!-- Content divider -->
        <div class='flexCenter'>
          <div class='contentDivider'></div>
        </div>
                    """;

    // Next section: LGA Search Table

    // Add a segment header
    html = html + """
        <div id='tableHeader' class='segmentHeader'><h2>Explore the data</h2></div>
            """;

    // Add top filter bar
    html = html + """
          <div id='filterBarTop' class='filterBar contentBlock'>
          <div>
            <button class='filterButton' id='allLGAsTrigger'>Show all LGAs</button>
          </div>

          <div><button id='allAgesTrigger'>Show all Ages</button></div>

          <div><button id='schoolTrigger'>Show Schooling</button></div>

          <div><button id='nonschoolTrigger'>Show Education</button></div>

          <div><button id='labourForceTrigger'>Show Labour Force</button></div>

          <div><button id='filter-clear'>Clear All Filters</button></div>
        </div>


            """;

    // Add sort by dropdown menu
    html = html + """
        <div id='filterBarBottom' class='filterBar flexCenter'>
        <div class='filterElement'>
        <form>
        <label for='sortOrder'> Order by: </label>
        <select id='sortOrder' name='sortOrder' class='queryFilter'>
        <option value='age'>Age</option>
        <option value='labour_force'>Employment</option>
        <option value='school'>School completion</option>
        <option value='non_school'>Higher Education</option>
        </select>
        </form>
        </div>
                """;

    // Add ratio dropdown menu
    html = html + """
        <div class='filterElement'>
        <form>
        <label for='ratioChoice'> Display as: </label>
        <select id='ratioChoice' name='ratioChoice' class='queryFilter'>
        <option value='total'>Total Values</option>
        <option value='aus'>Percentage of Australia</option>
        <option value='state'>Percentage of State</option>
        <option value='lga'>Percentage of LGA</option>
        </select>
        </form>
        </div>
                """;

    // Add order button
    html = html + """
        <div class='filterElement queryFilter'><button id='dirTrigger' value='desc'>Descending</button></div>
          </div>
                  """;

    // Add Tabulator table
    html = html + """
        <div class='contentBlock'>
          <div id='table-container'>
            <div id='data-table'></div>
          </div>
        </div>
            """;

    // // Add Tabulator filter
    // html = html + """
    // <div id='filterBarBottom' class='filterBar contentBlock flexCenter'>
    // <select id='filter-field'>
    // <option></option>
    // <option value='code'>LGA Code</option>
    // <option value='name'>LGA Name</option>
    // <option value='area'>Area Size</option>
    // <option value='latitude'>Latitude</option>
    // <option value='longitude'>Longitude</option>
    // </select>

    // <select id='filter-type'>
    // <option value='='>Equals</option>
    // <option value='<'>Less than</option>
    // <option value='<='>Less than or equal to</option>
    // <option value='>'>Greater than</option>
    // <option value='>='>Greater than or equal to</option>
    // <option value='!='>Doesn't equal</option>
    // <option value='like'>Contains</option>
    // </select>

    // <input id='filter-value' type='text' placeholder='value to filter' />

    // <button id='filter-clear'>Clear Filter</button>
    // </div>
    // """;

    // Close Content div
    html = html + "</div>";

    // Add the Footer

    ArrayList<Team_Members> Team_Members = jdbc.getTeam_Members();

    html = html + """
        <!-- FOOTER -->
        <div class='footer'>
          <div class='footerLogo'>
            <h2>Closing the gap.</h2>""";

    html = html + "<p>" + Team_Members.get(0).first_name + " " + Team_Members.get(0).last_name
        + " " + Team_Members.get(0).student_number + "</p>";
    html = html + "<p>" + Team_Members.get(0).email_ID + "</p>";
    html = html + "<p>" + Team_Members.get(1).first_name + " " + Team_Members.get(1).last_name
        + " " + Team_Members.get(1).student_number + "</p>";
    html = html + "<p>" + Team_Members.get(1).email_ID + "</p>";
    html = html + """

          </div>
          <div class='footer1'>
            <a href='/aboutUs.html'><h4>About Us</h4></a>
            <a href='/aboutUs.html#ourMission'><p>Mission Statement</p></a>
            <a href='/aboutUs.html#instructions'><p>Instructions</p></a>
            <a href='/aboutUs.html#personas'><p>Personas</p></a>
          </div>
          <div class='footer2'>
            <a href='/wantToHelp.html'><h4>Other Resources</h4></a>
            <a href='/wantToHelp.html'><p>Organisations</p></a>
            <a href='/wantToHelp.html'><p>Contacts</p></a>
          </div>
          <div class='footer3'>
            <a href='/learnMore.html'><h4>The Information</h4></a>
            <a href='/learnMore.html#LGASearch'><p>Numbers</p></a>
            <a href='/learnMore.html#tableHeader'><p>Data</p></a>
            <h5> RMIT Studio 1, Melbourne, Australia </h5>
          </div>
        </div>
          """;

    // Add scripts
    html = html + "<script type='text/javascript' src='tabulator.js'></script>"
        + " <script src='data-table.js'></script> "
        + " <script src='LGASearch.js'></script> ";

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
  }

}
