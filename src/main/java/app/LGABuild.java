package app;

import app.outcomeClasses.LGA;
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
public class LGABuild implements Handler {

    // URL of this page relative to http://localhost:7001/
    // public static final String URL = "/LGABuild.html";

    @Override
    public void handle(Context context) throws Exception {
        // Capture which LGA is selected from dropdown menu
        String searchLGA = context.pathParam("name");

        // Create a simple HTML webpage in a String
        String html = "";

        // Look up LGA from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        LGA lga = jdbc.getSingleLGA(searchLGA);

        // String scoreZero = "Not enough data";
        // double lgaScore = lga.getTotalScore();
        // if (lgaScore == 0) {
        // }

        // Print the LGA info into some html that will be inserted into the page with JS
        html = html + "<h2>" + lga.getName() + "</h2>"
                + "<div class='LGASearchInfo'>"
                + "<p><b>Code: </b></p>" + lga.getCode()
                + "<p><b>Type: </b></p>" + lga.getType()
                + "<p><b>Area: </b></p>" + lga.getArea() + " km2"
                + "</div>"
                + "<div class='LGASearchInfo'>"
                + " <p><b>State: </b></p>" + lga.getState()
                + " <p><b>Population: </b></p>" + lga.getPopulation()
                + "</div>";

        // DO NOT MODIFY THIS
        // Makes Javalin render html
        context.html(html);
    }

}
