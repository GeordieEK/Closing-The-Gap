package app.webpages;

import app.JDBCConnection;
import java.util.ArrayList;
import app.outcomeClasses.Team_Members;
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
public class PageAboutUs implements Handler {

  // URL of this page relative to http://localhost:7001/
  // public static final String URL = "/aboutUs.html";

  @Override
  public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html = html + "<head>" +
        "<title>Our Mission</title>";

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
        <!-- NAVBAR -->
        <div class='navbar'>
            <div class='topLogo'>Closing The Gap</div>
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
            <div><h1>Our mission.</h1></div>
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
              <div class='list flexCenter'>
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

    // Add Div class for persona content block
    html = html
        + """
            <div class='contentBlock flexCenter personaBlock'>
            <div>
              <img id = 'ashleyYoung' src= 'ashleyYoung.png' alt=""
              />
            </div>
            <div>
              <div class='headerItem'>
              <div><h2>Ashley Reyes</h2></div>
              </div>
              <div>
                <div class='personaText'>
                  <ul><b>Age:</b> 29 </ul>
                  <ul><b>Ethnicity:</b> Filipino-Australian </ul>
                  <ul><b>Occupation:</b> Nurse at the Royal Darwin Hospital</ul>
                  <ul><b>Description:</b> Ashley was born in Tennant Creek and moved to Darwin with her Mum and sister when
                  she started high school. In her work as a Nurse at various hospitals in the NT, Ashley has seen firsthand
                  the challenges and injustices that First Nations people face’. Ashley wishes to educate her friends and
                  family in the hope that they will contribute to ‘closing the gap’.</ul>
                  <ul><b>Goals:</b> <li>To raise awareness about Indigenous life expectancy rates in order to enact positive change
                  in the name of ‘closing the gap’.</li>
                  <li>To leverage community support to raise money, pressure policy makers or bolster volunteers.</li>
                  </ul>
                  <ul><b>Pain Points: </b> <li>Overly statistical, number heavy and generally boring websites that fail to
                  impress the issue upon her peers who do not see it so clearly.</li>
                  <li>Social injustice</li>
                  <li>Slow and cumbersome websites/apps</li>
                  </ul>
                  <ul><b>Needs: </b> <li>A website that can be easily shared amongst her friends, family and extended contacts. </li>
                  <li>A website that can be easily navigated and provides relevant information that is clear, concise and immediately impactful.</li>
                  <li>Additional resources that can be easily contributed to have a direct effect.</li>
                  </ul>
                  <ul><b>Skills & Experience: </b>
                    <li>Technically adept and a frequent user of social media.</li>
                    <li>Well versed in health statistics but is aware her peers are not.</li>
                    <li>Has a greater understanding of the health issues as well as lived experience due to work and location.</li>
                    </ul>

                </div>
              </div>
            </div>
            </div>

            <div class='contentBlock flexCenter personaBlock'>
                <div>
                    <div class='headerItem'>
                    <div><h2>Caleb Anderson</h2></div>
                    </div>
                    <div class='personaText'>

                        <div>
                        <ul><b>Age:</b> 37 </ul>
                        <ul><b>Ethnicity:</b> American </ul>
                        <ul><b>Occupation:</b> Chartered Accountant </ul>
                        <ul><b>Description:</b> Moved to Melbourne when he was 13 years old where he completed HS and University. Caleb is
                        married and has a young daughter. Spending his childhood in the states and starting a family young means Caleb
                        hasn’t had much of a chance to explore Australia outside Melbourne and its surrounds. While helping his daughter
                        research some homework one day, he was shocked to learn of the disparity in indigenous and non-indigenous life
                        expectancy rates. Caleb wishes to find more information and potentially ways in which he could contribute to positive change.</ul>
                        </div>
                        <div>
                            <div>
                            <ul><b>Goals: </b><li>
                            To learn more about the injustices and challenges faced by Aboriginal and Torres Strait Islander people.</li>
                            <li>Wishes to contribute in a way that will make an impact.</li>
                            </ul>
                            </div>
                            <div>
                            <ul><b>Needs:</b>
                            <li>A website that can provide him with true insight and knowledge about the issue. </li>
                            <li>Options that clearly state how they will assist in solving the issue and how he can contribute.</li></ul>
                            </div>
                        </div>
                        <div>
                        <ul><b>Pain Points:</b>
                        <li>Frustrated that he has just heard about this issue.</li>
                        <li>Struggling to find ways in which he can contribute effectively.</li>
                        <li>Social injustice.</li></ul>
                        </div>
                        <div>
                        <ul><b>Skills & Experience:</b>
                        <li>Technology power user. </li>
                        <li>Very financially literate.</li>
                        <li>Has an intimate knowledge of various financial structures and organisations.</li></ul>
                        </div>
                    </div>
                </div>
                    <div>
                    <img id = 'CalebAndersonFinal' src= 'CalebAndersonFinal.jpg' alt=""
                    />
                    </div>
            </div>

                """;

    // Add the Footer
    // JDBC connection.
    JDBCConnection jdbc = new JDBCConnection();

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
