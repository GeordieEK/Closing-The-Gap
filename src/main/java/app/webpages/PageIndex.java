package app.webpages;

import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.Team_Members;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 * 
 * Images are obtained from Adobe Stock under an education license.
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageIndex implements Handler {

  // URL of this page relative to http://localhost:7001/
  public static final String URL = "/";

  @Override
  public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Header information
    html = html + "<head>" +
        "<title>Homepage</title>";

    // Add some CSS (external file)

    html = html
        + """
            <!-- Link to CSS general stylesheet -->
            <link rel='stylesheet' href='overallStyle.css' />
            <!-- Link to CSS specific stylesheet -->
            <link rel='stylesheet' href='indexStyle.css' />
            <!-- Link to Google fonts -->
            <!-- TODO: Fix Font -->
            <link rel='preconnect' href='https://fonts.googleapis.com' />
            <link rel='preconnect' href='https://fonts.gstatic.com' crossorigin />
            <link
              href='https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,400;0,500;0,700;1,400;1,500&display=swap'
              rel='stylesheet'
            />""";

    // Close the head
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
        <!-- Header -->
        <div class='header'>
          <div class='headerItem'>
            <div><h1>It's time to close the gap.</h1></div>
          </div>
          <div class='headerItem'>
            <!-- <button>Find out more</button> -->
          </div>
        </div>
            """;

    // Add 3 key figures content block
    html = html + """
        <!-- 3 key Figures Content Block -->
        <div class='contentBlock flexCenter' id='threeFactContentBlock'>
          <div class='factBlock'>
            <h1>3x</h1>
            <p>
              Indigenous Australians are 3x more likely to be unemployed than
              non-Indigenous Australians
            </p>
          </div>
          <div class='factBlock'>
            <h1>43.9%</h1>
            <p>43.9% of Indigenous Australians leave school before year 11</p>
          </div>
          <div class='factBlock'>
            <h1>21.17%</h1>
            <p>
              21.17% of adult Indigenous Australians have a bachelor&#39;s degree or
              higher compared to 59.72% of non-Indigenous Australians
            </p>
          </div>
        </div>
                """;

    // Add content divider
    html = html + """
        <!-- Content divider -->
        <div class='flexCenter'>
          <div class='contentDivider'></div>
        </div>
                    """;

    // Get total number of LGAs in the data set.
    // JDBC connection.
    JDBCConnection jdbc = new JDBCConnection();
    // Get total number LGAs from database.
    int LGACount = jdbc.getLGACount();
    // Get total population from database.
    int totalPop = jdbc.getPopulation("Australia", "Total");

    // Add 4 more figures content block
    html = html + """
        <!-- 4 more figures Content Block -->
        <div class='contentBlock flexCenter' id='fourFactContentBlock'>
          <div class='factBlock'>
            <h1>
            """;
    // Add total number LGAs from database then Continue more figures content block
    html = html + LGACount + """
          </h1>
          <p>
            Total number of LGAs in the data.
          </p>
        </div>
        <div class='factBlock'>
          <h1>
        """;
    html = html + totalPop + """
                </h1>
            <p>Total number of people in the data.</p>
          </div>
          <div class='factBlock'>
            <h1>7.692</h1>
            <p>
              million km&#178 <br>
              Total area of Australia.
            </p>
          </div>
          <div class='factBlock'>
            <h1>3.3</h1>
            <p>
              People per km&#178 in Australia.
            </p>
          </div>
        </div>
                        """;

    // Add images and text content block
    html = html
        + """
            <!-- Images and text content block -->
            <div class='contentBlock flexCenter'>
              <div class='imageTextBlock'>
              <p>
              It has been 17 years since The Aboriginal and Torres Strait Islander Social Justice Commissioner, Professor Tom Calma AO,
              urged Australian governments to commit to achieving equality for Aboriginal
              and Torres Strait Islander people in health and life expectancy, within 25 years.
            </p>
                <div>
                  <img
                    width='100%'
                    src='art1.jpeg'
                    alt='Aboriginal artwork in red and orange colours with dots and swirls'
                  />
                </div>
              </div>
              <div class='imageTextBlock'>
                <div>
                  <img
                    width='100%'
                    src='art2.jpeg'
                    alt='Aboriginal artwork in red orange and black colours with a kangaroo and a lizard'
                  />
                </div>
                <p>
                  We still have a long way to go, but dissecting information from reports can be difficult. <br>
                  Now, you can explore the data for yourself using this website.
                </p>
              </div>
            </div>
                            """;

    // Add sign up form
    html = html + """
        <!-- Sign-up form content block -->
        <div class='contentBlock'>
          <div class='flexCenter'>
            <div>
              <img
                width='600vw'
                src='hands.jpeg'
                alt=''
              />
            </div>
          </div>
          <div class='flexCenter'>
            <h1>Stay up to date</h1>
          </div>
          <div class='flexCenter'>
            <input type='email' />
            <button id='signUpTrigger'>Sign Up</button>
          </div>
        </div>
                        """;

    // Add a Footer

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

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
  }

}
