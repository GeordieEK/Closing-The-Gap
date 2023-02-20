package app.webpages;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import app.JDBCConnection;
import app.outcomeClasses.Team_Members;
import java.util.ArrayList;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageWantToHelp implements Handler {

  // URL of this page relative to http://localhost:7001/
  public static final String URL = "/wantToHelp.html";

  @Override
  public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html = html + "<head>" +
        "<title>Want to Help</title>";

    // Add some CSS (external file)
    html = html
        + """
            <!-- Link to CSS general stylesheet -->
            <link rel='stylesheet' href='overallStyle.css' />
            <!-- Link to CSS specific stylesheet -->
            <link rel='stylesheet' href='wantToKnowMore.css' />
            <link rel='stylesheet' href='aboutUsStyle.css' />
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
            <div><h1>Your help is important.</h1></div>
          </div>
          <div class='headerItem'>
            <p>
                We believe that information is only as good as what
                you do with it, so we've collected some of our favourite
                organisations that you can contribute to.

            </p>
          </div>
        </div>
            """;

    // Add content divider
    html = html + """
        <div class='contentBlock'>
            <div class='flexCenter'>
                <h2>Our favorite organisations</h2>
            </div>
        </div>
            """;

    // Add content
    html = html + """
         <!-- 6 container figures and pictures of the organisations-- -->
         <div class='gridWrapper'>
         <div class='top-left imageGrid flexCenter'>
           <img id='PayTheRentLogo' src='PayTheRentLogo.png' width = 150px alt='Pay the Rent'>
           <div class='textGrid'>
            <div><h3><b><a href = 'https://paytherent.net.au/'> Pay the Rent</a></b></h3></div>
             <div><i>Pay The Rent is a grassroots collective that accepts one-off or regular donations (or rent) that are
             distributed amongst a variety of First Nation interests across Australia. Pay The Rent is focused on
             direct support & action that can be taken by focusing on accepting donations and ensuring they
             are distributed.</i></div>
            </div>

         </div>
         <div class='top-middle imageGrid flexCenter'>
           <img id='sunflower' src='sunflower.jpg' width = 150px alt='Children's Ground>
           <div class='textGrid'>
            <div><h3><b><a href = 'https://childrensground.org.au/'>Children's Ground </a></b></h3></div>
            <div><i>Integration includes First Nations and Western/international leading practices working together. 
            This ensures children have the very best throughout life. With their First Culture and language at the heart
            of all that they do, children obtain skills that create local, national and global opportunities.</i></div>
           </div>

         </div>
         <div class='top-right imageGrid flexCenter' >
           <img src='foundation.png' width = 150px alt='Healing Foundation'>
           <div class='textGrid'>
            <div><h3><b><a href ='https://healingfoundation.org.au/'> Healing Foundation </a></b></h3></div>
            <div><i>The Healing Foundation is a national Aboriginal and Torres Strait Islander organisation that provides a platform to 
            amplify the voices and lived experience of Stolen Generations survivors and their families and help them. We promote trauma-aware, 
            healing-informed practice to help government, policymakers, and workforces to understand their role.</i></div>
           </div>

         </div>

         <div class='bottom-left imageGrid flexCenter'>
          <img id='hr' src='hr.jpg' width = 150px alt='Change the Record'>
          <div class= 'textGrid' >
            <div><h3><b><a href = 'https://www.changetherecord.org.au/'>Change The Record</a></b></h3></div>
            <div><p><i>Change the Record, works with Aboriginal and Torres Strait Islander communities to invest in holistic 
            intervention, prevention and diversion. These are smarter, evidence-based and more cost-effective solutions 
            that increase safety, address the root causes of violence and build stronger communities.</i></p></div>
          </div>

         </div>


         <div class='bottom-middle imageGrid flexCenter'>
          <img id='R' src='R.jpg' width = 200px alt='Sister Works'>
          <div class='textGrid'>
            <div><h3><b><a href = 'https://sisterworks.org.au/'>SisterWorks </a></b></h3></div>
            <div><i>SisterWorks Empowerment Hubs are workplaces and training centres that
            provide women who are Aboriginal, refugees, asylum seekers or migrants with meaningful opportunities to 
            develop pathways of education, employment, entrepreneurship and leadership. They believe in the 
            empowerement of women by working and being financially independent.</i></div>
           </div>

         </div>
         <div class='bottom-right imageGrid flexCenter'>
          <img src='Untitled.png' width = 200px alt='Happy Boxes Project'>
          <div class='textGrid'>
            <div><h3><b><a href = 'https://happyboxesproject.com/'>Happy Boxes Project</a></b></h3></div>
            <div><i>19% of Aboriginal and Torres Strait Islander people are living in unacceptable living standards due to overcrowded housing and structural faults
            We want to send as many Happy Boxes filled with self care products as possible. 
            To provide women, regardless of their location and situation, the enjoyment of basic necessitues that we regularly take for granted. </i></div>
           </div>

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
