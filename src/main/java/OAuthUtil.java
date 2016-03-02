
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ilham
 */
public class OAuthUtil {
    
    private static final Logger LOG = Logger.getLogger(OAuthUtil.class.getName());
    
    public static String CONSUMER_SECRET = "Tzn3twO1IJTQjbs4";
    public static String CONSUMER_KEY = "test-88875";
    
    public String signRequest(String urlPath) throws Exception {
        
        try {
            // create a consumer object and configure it with the access
            // token and token secret obtained from the service provider
            OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY,
                                                 CONSUMER_SECRET);
            //consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

            // create an HTTP request to a protected resource
            URL url = new URL(urlPath);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            // sign the request
            consumer.sign(request);

            // send the request
            request.connect();
            
            InputStream is = request.getInputStream();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder body = new StringBuilder();
            while((line = reader.readLine()) != null) {
                body.append(line);
            }
            return body.toString();
            
        } catch(Exception ex) {
            LOG.log(Level.SEVERE, "error when signRequest()", ex);
            throw ex;
        }
    }
    
    public String signUrl(String urlPath) throws Exception {
        
        try {
            OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
            consumer.setSigningStrategy( new QueryStringSigningStrategy());
            String signedUrl = consumer.sign(urlPath);
            return signedUrl;
        } catch(Exception ex) {
            LOG.log(Level.SEVERE, "error when signRequest()", ex);
            throw ex;
        }
    }
}
