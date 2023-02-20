package app.jsonQueries;

import java.util.ArrayList;

import app.JDBCConnection;
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
public class QueryLGA implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/querylga.html";

    @Override
    public void handle(Context context) throws Exception {

        // JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        String metric = context.pathParam("metric");
        String order = context.pathParam("order");

        if (metric.equalsIgnoreCase("age")) {
            lgas = jdbc.getLGAs("age", order);
        } else if (metric.equalsIgnoreCase("labour_force")) {
            lgas = jdbc.getLGAs("labour_force", order);
        } else if (metric.equalsIgnoreCase("school")) {
            lgas = jdbc.getLGAs("school", order);
        } else if (metric.equalsIgnoreCase("non_school")) {
            lgas = jdbc.getLGAs("non_school", order);
        }

        // Makes Javalin render a json
        context.json(lgas);
    }

}
