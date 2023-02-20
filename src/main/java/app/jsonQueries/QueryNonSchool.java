package app.jsonQueries;

import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.NonSchool;
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
public class QueryNonSchool implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/querynonschool.html";

    @Override
    public void handle(Context context) throws Exception {

        String metric = context.pathParam("metric");
        String ratio = context.pathParam("ratio");
        String order = context.pathParam("order");

        // JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the Labour Force results
        ArrayList<NonSchool> nonSchooling = jdbc.getNonSchoolTable(metric, ratio, order);

        // Javalin renders the json
        context.json(nonSchooling);
    }

}
