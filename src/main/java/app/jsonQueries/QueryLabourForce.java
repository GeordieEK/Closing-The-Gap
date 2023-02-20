package app.jsonQueries;

import java.util.ArrayList;

import app.JDBCConnection;
import app.outcomeClasses.LabourForce;
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
public class QueryLabourForce implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/querylabourforce.html";

    @Override
    public void handle(Context context) throws Exception {

        String metric = context.pathParam("metric");
        String ratio = context.pathParam("ratio");
        String order = context.pathParam("order");

        // JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the Labour Force results
        ArrayList<LabourForce> labourForces = jdbc.getLabourForceTable(metric, ratio, order);

        // DO NOT MODIFY THIS
        // Makes Javalin render a json
        context.json(labourForces);
    }

}
