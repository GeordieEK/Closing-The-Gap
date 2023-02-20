package app.jsonQueries;

import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.Age;
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
public class QueryAge implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/queryage.html";

    @Override
    public void handle(Context context) throws Exception {

        String metric = context.pathParam("metric");
        String ratio = context.pathParam("ratio");
        String order = context.pathParam("order");

        System.out.println("metric:" + metric + "ratio: " + ratio + "order: " + order);

        // JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Ask jdbc for the rows of the table
        ArrayList<Age> categories = jdbc.getAgeTable(metric, ratio, order);

        // Javalin renders the json
        context.json(categories);

        // NOTE: THIS METHOD IS REDUNDANT, INSTEAD USE NEXT METHOD
        // ArrayList<Category> categories = jdbc.getCategories("age");
    }

}
