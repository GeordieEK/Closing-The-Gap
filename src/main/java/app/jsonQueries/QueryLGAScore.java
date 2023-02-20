package app.jsonQueries;

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
public class QueryLGAScore implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/querylgascore.html";

    @Override
    public void handle(Context context) throws Exception {

        String searchLGA = context.pathParam("name");

        // Look up LGA from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        LGA lga = jdbc.getSingleLGA(searchLGA);

        // DO NOT MODIFY THIS
        // Makes Javalin render a json
        context.json(lga);
    }

}
