package app;

import app.jsonQueries.QueryAge;
import app.jsonQueries.QueryLGA;
import app.jsonQueries.QueryLabourForce;
import app.jsonQueries.QueryNonSchool;
import app.jsonQueries.QuerySchool;
import app.jsonQueries.QueryLGAScore;
import app.jsonQueries.QueryLGADistance;
import app.jsonQueries.QueryLGASimilar;
import app.webpages.AboutUsPage;
import app.webpages.PageIndex;
import app.webpages.PageLGASearch;
import app.webpages.PageLearnMore;
import app.webpages.PageWantToHelp;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the
 * Javalin HTTP Server and our web application.
 * 
 * Images are obtained from Adobe Stock under an education license.
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int JAVALIN_PORT = 7001;
    public static final String CSS_DIR = "css/";
    public static final String JS_DIR = "js/";
    public static final String IMAGES_DIR = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7000
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));

            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the JS Directory
            config.addStaticFiles(JS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);

        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // HTML Requests
        app.get(PageIndex.URL, new PageIndex());
        app.get(AboutUsPage.URL, new AboutUsPage());
        app.get(PageLearnMore.URL, new PageLearnMore());
        app.get(PageWantToHelp.URL, new PageWantToHelp());
        app.get(PageLGASearch.URL, new PageLGASearch());

        // JSON Requests
        app.get("/querylga/:metric/:order", new QueryLGA());
        app.get("/querynonschool/:metric/:ratio/:order", new QueryNonSchool());
        app.get("/queryschool/:metric/:ratio/:order", new QuerySchool());
        app.get("/querylabourforce/:metric/:ratio/:order", new QueryLabourForce());
        app.get("/LGABuild/:name", new LGABuild());
        app.get("/queryage/:metric/:ratio/:order", new QueryAge());
        app.get("/querylgascore/:name", new QueryLGAScore());
        app.get("/querylgadistance/:name", new QueryLGADistance());
        app.get("/querylgasimilar/:name", new QueryLGASimilar());

        // POST Commands
        // app.post("/LGASearch.html", ctx -> { // the <> syntax allows slashes ('/') as
        // part of the parameter
        // ctx.result(ctx.pathParam("searchLGA"));
        // });
    }

}
