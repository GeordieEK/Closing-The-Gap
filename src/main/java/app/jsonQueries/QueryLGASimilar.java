package app.jsonQueries;

import java.util.ArrayList;

import app.JDBCConnection;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import app.outcomeClasses.LGA;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class QueryLGASimilar implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/querylgasimilar.html";

    @Override
    public void handle(Context context) throws Exception {

        String searchLGA = context.pathParam("name");

        // Look up main LGA from JDBC
        JDBCConnection jdbc = new JDBCConnection();

        // Create an ArrayList and call the getNearbyLgas method
        ArrayList<LGA> lgaList = new ArrayList<LGA>();
        lgaList = jdbc.getSimilarLgas(searchLGA);

        // Return first five from ArrayList
        context.json(lgaList);
    }

}
