package app.webpages;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.Team_Members;
import app.outcomeClasses.User_Attributes;
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

public class AboutUsPage implements Handler {

  // URL of this page relative to http://localhost:7001/
  public static final String URL = "/aboutUs.html";

  @Override
  public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html = html + "<head>" +
        "<title> Our Mission </title>";

    // Add some CSS (external file)
    html = html
        + """
            <!-- Link to CSS general stylesheet -->
            <link rel='stylesheet' href='overallStyle.css' />
            <!-- Link to CSS specific stylesheet -->
            <link rel='stylesheet' href='aboutUsStyle.css' />
            <!-- Link to Google fonts -->
            <!-- TODO: Fix Font -->
            <link rel='preconnect' href='https://fonts.googleapis.com' />
            <link rel='preconnect' href='https://fonts.gstatic.com' crossorigin />
            <link
                href='https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,400;0,500;0,700;1,400;1,500&display=swap'
                rel='stylesheet'
            />


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
              <button>Sign Up</button>
            </div>
          </div>
        </div>
              """;

    // Add header content block
    html = html + """
        <!-- Header -->
        <div class='header'>
          <div class='headerItem'>
            <div id='ourMission'><h1>Our mission.</h1></div>
          </div>
          <div class='contentBlock contentStyling' >
            <p>
              To create awareness amongst the people about the social issue.
              To present the statistical data in a regulated and tabulated manner so that the users can filter data
              according to their requirements. This website is aimed to provide a forum for the users to connect with
              different organisations to step up and contribute to the eradication of this social barrier.
           </p>
          </div>
        </div>
            """;

    // Add Div for page Content
    html = html
        + """
              <div class='contentBlock'>
              <div class='flexCenter'>
                <h2>How to use this site</h2>
              </div>
              <div id='instructions' class='list flexCenter'>
                <ul>
                  <li>
                    This website can be used to filter various data across LGAs in Australia.
                  </li>
                  <li>
                    You can use to the website to view the stats of the results in a graphical manner.
                  </li>
                  <li>
                    You can use this website to directly get in touch with the organistaions that support the cause.
                  </li>
                  <li>
                    Spreading awareness and information about the socio-economic challenge can be done with the help of this website.
                  </li>
                </ul>
              </div>
            </div>
                  """;

    JDBCConnection jdbc = new JDBCConnection();

    // With the object we can ask this for all movie Titles
    ArrayList<User_Attributes> User_Attributes = jdbc.getUser_Attributes();
    html = html + "<div class='contentBlock flexCenter personaBlock' id='personas'>";
    html = html
        + """
            <div>
              <img id = 'ashleyYoung' src= 'ashleyYoung.png' alt = 'A young Filipino woman'
              />
            </div>
            <div>
              <div class='headerItem'>
              <div><h2>Ashley Reyes</h2></div>
              </div>
              """;
    html = html + "<div class='personaText'>";
    html = html + "<ul><b>Age:</b>" + User_Attributes.get(0).Age + " </ul>";
    html = html + "<ul><b>Ethnicity:</b>" + User_Attributes.get(0).Ethnicity + " </ul>";
    html = html + "<ul><b>Occupation:</b>" + User_Attributes.get(0).Occupation + "</ul>";
    html = html + "<ul><b>Description:</b>" + User_Attributes.get(0).Description + "</ul>";
    html = html + "<ul><b>Goals:</b><li>" + User_Attributes.get(0).Goals + "</li></ul>";
    html = html + "<ul><b>Pain Points:</b> <li>" + User_Attributes.get(0).Pain_Points + "</li></ul>";
    html = html + "<ul><b>Needs:</b> <li>" + User_Attributes.get(0).Needs + "</li></ul>";
    html = html + "<ul><b>Experiences:</b> <li>" + User_Attributes.get(0).Experiences + "</li></ul>";

    html = html + "</div>";
    html = html + "</div>";
    html = html + "</div>";
    html = html + "</div>";
    html = html + "</div>";

    html = html + "<div class='contentBlock flexCenter personaBlock'>";
    html = html
        + """
            <div>
              <img id = 'CalebAndersonFinal' src= 'CalebAndersonFinal.jpg' alt = 'A middle aged white man'
              />
            </div>
            <div>
                <div class='headerItem'>
                <div><h2>Caleb Anderson</h2></div>
                </div>
                """;
    html = html + "<div class='personaText'>";
    html = html + "<ul><b>Age:</b>" + User_Attributes.get(1).Age + " </ul>";
    html = html + "<ul><b>Ethnicity:</b>" + User_Attributes.get(1).Ethnicity + " </ul>";
    html = html + "<ul><b>Occupation:</b>" + User_Attributes.get(1).Occupation + "</ul>";
    html = html + "<ul><b>Description:</b>" + User_Attributes.get(1).Description + "</ul>";
    html = html + "<ul><b>Goals:</b><li>" + User_Attributes.get(1).Goals + "</li></ul>";
    html = html + "<ul><b>Pain Points:</b> <li>" + User_Attributes.get(1).Pain_Points + "</li></ul>";
    html = html + "<ul><b>Needs:</b> <li>" + User_Attributes.get(1).Needs + "</li></ul>";
    html = html + "<ul><b>Experiences:</b> <li>" + User_Attributes.get(1).Experiences + "</li></ul>";

    html = html + "</div>";
    html = html + "</div>";
    html = html + "</div>";
    html = html + "</div>";
    html = html + "</div>";

    // Add the footer

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
            <a href=''><h4>About Us</h4></a>
            <a href=''><p>Mission Statement</p></a>
            <a href=''><p>Instructions</p></a>
            <a href=''><p>Personas</p></a>
            <a href=''><p>Our Team</p></a>
          </div>
          <div class='footer2'>
            <a href=''><h4>Our Resources</h4></a>
            <a href=''><p>Organisations</p></a>
            <a href=''><p>Contacts</p></a>
          </div>
          <div class='footer3'>
            <a href=''><h4>The Information</h4></a>
            <a href=''><p>Numbers</p></a>
            <a href=''><p>Graphics</p></a>
            <a href=''><p>Data</p></a>
            <h5> RMIT Studio 1 </h5>
            <h5> Melbourne </h5>
            <h5> Australia </h5>
          </div>
        </div>
          """;

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
  }

}
