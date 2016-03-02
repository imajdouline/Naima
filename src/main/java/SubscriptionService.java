
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import entity.Account;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Creator;
import model.Event;
import model.Item;
import model.Marketplace;
import model.Payload;
import spark.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ilham
 */
public class SubscriptionService {
    
    private static final Logger LOG = Logger.getLogger(SubscriptionService.class.getName());
    
    private static final String SUCCESS_MESSAGE = 
            "<result>\n" +
            "    <success>$Status</success>\n" +
            "    <accountIdentifier>$Id</accountIdentifier>\n" +
            "</result>";
    
    private static final String ERROR_MESSAGE = 
            "<result>\n" +
            "    <success>$Status</success>\n" +
            "    <errorCode>$errorCode</errorCode>\n" +
            "    <message>$Message</message>\n" +
            "</result>";
            
    
	
    public String getAllSubscriptions() {
        
        try {
            List<Account> accounts = new DbUtil().listAccounts();
            //fetch users
            StringBuilder sb = new StringBuilder();
            sb.append("<div>");
            sb.append("list of subscriptions : ");
            sb.append("</br>");
            for(Account a : accounts) {
                sb.append(a.print());
                sb.append("</br>");
            }
            sb.append("</div>");
            return sb.toString();
        } catch(Exception e) {
            LOG.log(Level.SEVERE, "getAllSubscriptions", e);
            String response = ERROR_MESSAGE;
            response = response.replace("$Status", "false");
            response = response.replace("$Message", "Error when listing account");
            response = response.replace("$errorCode", "UNKNOWN_ERROR");
            return response;
        }
    }
    
    
    public String createSubscriptionOrder(
            Response res,
            String urlToCall
            ) throws IOException {
        
        OAuthUtil connector = new OAuthUtil();
        String response;
        try {
            String eventXml = connector.signRequest(urlToCall);

            LOG.log(Level.SEVERE, "eventXml" + eventXml);
            
            Event event = getEvent(eventXml);
            Account acc = event.toAccount();
            
            DbUtil dbUtil = new DbUtil();
            if(dbUtil.getAccount(acc.getUuid()) != null) {
                res.status(409);
                response = ERROR_MESSAGE;
                response = response.replace("$Status", "false");
                response = response.replace("$Message", "Error when creating account");
                response = response.replace("$errorCode", "USER_ALREADY_EXISTS");
                
            } else {
                res.status(200);
                int id = dbUtil.insertAccount(acc);

                response = SUCCESS_MESSAGE;

                response = response.replace("$Status", "true");
                response = response.replace("$Message", "Account creation successful for : ");
                response = response.replace("$Id", String.valueOf(id));
            }
            
            
        } catch(Exception ex) {
            LOG.log(Level.SEVERE, "createSubscriptionOrder", ex);
            response = ERROR_MESSAGE;
            response = response.replace("$Status", "false");
            response = response.replace("$Message", "Error when creating account");
            response = response.replace("$errorCode", "");
        }
        LOG.log(Level.SEVERE, "response" + response);
        return response;
    }
    
    
    public String changeSubscriptionOrder(
            Response res,
            String urlToCall
            ) throws IOException {
        
        DbUtil dbUtil = new DbUtil();
        OAuthUtil connector = new OAuthUtil();
        String response;
        try {
            String eventXml = connector.signRequest(urlToCall);
            LOG.log(Level.SEVERE, "eventXml" + eventXml);
            
            Event event = getEvent(eventXml);
            
            Account acc = event.toAccount();
            if(dbUtil.getAccount(acc.getUuid()) == null) {
                response = ERROR_MESSAGE;
                response = response.replace("$Status", "false");
                response = response.replace("$Message", "Error when changing account");
                response = response.replace("$errorCode", "ACCOUNT_NOT_FOUND");
                res.status(404);
                
            } else {
                dbUtil.updateAccount(event.toAccount());

                response = SUCCESS_MESSAGE;
                response = response.replace("$Status", "true");
                response = response.replace("$Message", "Account change successful for : ");
                response = response.replace("$Id", "");
                
                res.status(200);
            }
            
        } catch(Exception ex) {
            LOG.log(Level.SEVERE, "changeSubscriptionOrder", ex);
            response = ERROR_MESSAGE;
            response = response.replace("$Status", "false");
            response = response.replace("$Message", "Error when creating account");
            response = response.replace("$errorCode", "");
        }
        LOG.log(Level.SEVERE, "response" + response);
        return response;
    }
    
    
    public String cancelSubscriptionOrder(
            Response res,
            String urlToCall
            ) throws IOException {
        
        OAuthUtil connector = new OAuthUtil();
        DbUtil dbUtil = new DbUtil();
        String response;
        try {
            String eventXml = connector.signRequest(urlToCall);
            LOG.log(Level.SEVERE, "eventXml" + eventXml);
            
            Event event = getEvent(eventXml);
            
            Account acc = event.toAccount();
            if(dbUtil.getAccount(acc.getUuid()) == null) {
                response = ERROR_MESSAGE;
                response = response.replace("$Status", "false");
                response = response.replace("$Message", "Error when changing account");
                response = response.replace("$errorCode", "ACCOUNT_NOT_FOUND");
                res.status(404);
                
            } else {
                // update subscription to log and user to system
            dbUtil.updateAccount(event.toAccount());
            
            response = SUCCESS_MESSAGE;
            response = response.replace("$Status", "true");
            response = response.replace("$Id", "");
            }
            
            
        } catch(Exception ex) {
            LOG.log(Level.SEVERE, "cancelSubscriptionOrder", ex);
            response = ERROR_MESSAGE;
            response = response.replace("$Status", "false");
            response = response.replace("$Message", "Error when creating account");
            response = response.replace("$errorCode", "");
        }
        LOG.log(Level.SEVERE, "response" + response);
        return response;
    }
    
    
    public String statusSubscriptionOrder(
            Response res,
            String urlToCall
            ) throws IOException {
        
        OAuthUtil connector = new OAuthUtil();
        DbUtil dbUtil = new DbUtil();
        String response;
        try {
            String eventXml = connector.signRequest(urlToCall);
        
             Event event = getEvent(eventXml);
             LOG.log(Level.SEVERE, "eventXml" + eventXml);
             
             Account acc = event.toAccount();
             if(dbUtil.getAccount(acc.getUuid()) == null) {
                response = ERROR_MESSAGE;
                response = response.replace("$Status", "false");
                response = response.replace("$Message", "Error when changing account");
                response = response.replace("$errorCode", "ACCOUNT_NOT_FOUND");
                res.status(404);
                
            } else {
                // update subscription to log and user to system
               dbUtil.updateAccount(event.toAccount());


                // add subscription to log and user to system
                response = SUCCESS_MESSAGE;
                response = response.replace("$Status", "true");
                response = response.replace("$Message", "Account creation successful for : ");
                response = response.replace("$Id", "");
             }
        } catch(Exception ex) {
            LOG.log(Level.SEVERE, "statusSubscriptionOrder", ex);
            response = ERROR_MESSAGE;
            response = response.replace("$Status", "false");
            response = response.replace("$Message", "Error when creating account");
            response = response.replace("$errorCode", "");
        }
        LOG.log(Level.SEVERE, "response" + response);
        return response;
    }
    
    private Event getEvent(String eventXml) {
        XStream xstream = new XStream();
        xstream.processAnnotations(Payload.class); // special case of list item
        xstream.alias("event", Event.class);
        xstream.registerConverter(new MapEntryConverter());
        Event result = (Event)xstream.fromXML(eventXml);
        return result;
    }
    
    public String reset() throws Exception {
        new DbUtil().reset();
        
        return "DONE";
    }
    
    public static class MapEntryConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if ( null != val ) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new HashMap<String, String>();

            while(reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }
}
