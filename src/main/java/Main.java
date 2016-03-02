import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;
import spark.Request;
import spark.Response;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");
    
    get("/subscriptions", (req, res) -> 
            new SubscriptionService().getAllSubscriptions());
    
    get("/subscriptions/create", (req, res) -> 
            new SubscriptionService().createSubscriptionOrder(res, req.queryParams("url")));
    
    get("/subscriptions/change", (req, res) -> 
            new SubscriptionService().changeSubscriptionOrder(res, req.queryParams("url")));
    
    get("/subscriptions/cancel", (req, res) -> 
            new SubscriptionService().cancelSubscriptionOrder(res, req.queryParams("url")));
    
    get("/subscriptions/status", (req, res) -> 
            new SubscriptionService().statusSubscriptionOrder(res, req.queryParams("url")));

    get("/", (Request request, Response response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

    get("/reset", (req, res) -> 
      new SubscriptionService().reset());
    

  }

}
