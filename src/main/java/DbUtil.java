
import com.heroku.sdk.jdbc.DatabaseUrl;
import entity.Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ilham
 */
public class DbUtil {
    
    private static final Logger LOG = Logger.getLogger(DbUtil.class.getName());
    
    public int insertAccount(Account account) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        
      try {
        connection = DatabaseUrl.extract().getConnection();

        stmt = connection.createStatement();
        
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS account ");
        sb.append("(firstName varchar(255), lastName varchar(255),  ");
        sb.append("email varchar(255), uuid varchar(255), ");
        sb.append("subscriptionStatus varchar(255), ");
        sb.append("editionCode varchar(255), pricingDuration varchar(255))");
        
        LOG.log(Level.SEVERE, sb.toString());
        stmt.executeUpdate(sb.toString());
        
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO account VALUES ( '");
        sb2.append(account.getFirstName()).append("', '").append(account.getLastName()).append("', '");
        sb2.append(account.getEmail()).append("', '").append(account.getUuid()).append("', '");
        sb2.append(account.getSubscriptionStatus()).append("', '").append(account.getEditionCode()).append("', '");
        sb2.append(account.getPricingDuration()).append("') ");
        LOG.log(Level.SEVERE, sb2.toString());
        return stmt.executeUpdate(sb2.toString(), Statement.RETURN_GENERATED_KEYS);
        
      } catch (Exception e) {
         LOG.log(Level.SEVERE, "error when insertAccount", e);
         throw e;
      } finally {
        if (connection != null) 
            try{
                connection.close();
                stmt.close();
            } catch(SQLException e){
            }
      }
    }
    
    public Account getAccount(String uuid) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        Account acc = null;
      try {
        connection = DatabaseUrl.extract().getConnection();

        stmt = connection.createStatement();
        
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS account ");
        sb.append("(firstName varchar(255), lastName varchar(255),  ");
        sb.append("email varchar(255), uuid varchar(255), ");
        sb.append("subscriptionStatus varchar(255), ");
        sb.append("editionCode varchar(255), pricingDuration varchar(255))");
        
        LOG.log(Level.SEVERE, sb.toString());
        stmt.executeUpdate(sb.toString());
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM account WHERE uuid = '" + uuid + "'");
        
        if(rs.next()) {
            acc = new Account();
            acc.setEditionCode(rs.getString("editionCode"));
            acc.setLastName(rs.getString("lastName"));
            acc.setFirstName(rs.getString("firstName"));
            acc.setEmail(rs.getString("email"));
            acc.setUuid(rs.getString("uuid"));
            acc.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            acc.setPricingDuration(rs.getString("pricingDuration"));
            
        }
        return acc;
      } catch (Exception e) {
         LOG.log(Level.SEVERE, "error when insertAccount", e);
         throw e;
      } finally {
        if (connection != null) 
            try{
                connection.close();
                stmt.close();
            } catch(SQLException e){
            }
      }
    }
    
    
    public void updateAccount(Account account) throws Exception {
        Connection connection = null;
        Statement stmt = null;
      try {
        connection = DatabaseUrl.extract().getConnection();
        stmt = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS account ");
        sb.append("(firstName varchar(255), lastName varchar(255),  ");
        sb.append("email varchar(255), uuid varchar(255), ");
        sb.append("subscriptionStatus varchar(255), ");
        sb.append("editionCode varchar(255), pricingDuration varchar(255))");
        
        LOG.log(Level.SEVERE, sb.toString());
        stmt.executeUpdate(sb.toString());
        
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Update account set ");
        sb2.append("firstName = '").append(account.getFirstName());
        sb2.append("', lastName = '").append(account.getLastName());
        sb2.append("', email = '").append(account.getEmail());
        sb2.append("', subscriptionStatus = '").append(account.getSubscriptionStatus());
        sb2.append("', editionCode = '").append(account.getEditionCode());
        sb2.append("' where uuid = '").append(account.getUuid()).append("'");
        
        LOG.log(Level.SEVERE, sb2.toString());
        stmt.executeUpdate(sb2.toString(), Statement.RETURN_GENERATED_KEYS);
        
      } catch (Exception e) {
         LOG.log(Level.SEVERE, "error when updateAccount", e);
         throw e;
      } finally {
        if (connection != null) 
            try{
                connection.close();
                stmt.close();
            } catch(SQLException e){
            }
      }
    }
    
    
    public List<Account> listAccounts() throws Exception {
        Connection connection = null;
        Statement stmt  = null;
        ResultSet rs = null;
      List<Account> result = new ArrayList<>();
      try {
        connection = DatabaseUrl.extract().getConnection();

        stmt = connection.createStatement();
        
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS account ");
        sb.append("(firstName varchar(255), lastName varchar(255),  ");
        sb.append("email varchar(255), uuid varchar(255) ,");
        sb.append("subscriptionStatus varchar(255),");
        sb.append("editionCode varchar(255), pricingDuration varchar(255))");
        
        
        LOG.log(Level.SEVERE, sb.toString());
        stmt.executeUpdate(sb.toString());
        
        rs = stmt.executeQuery("SELECT * FROM account");

        while (rs.next()) {
            Account acc = new Account();
            acc.setEditionCode(rs.getString("editionCode"));
            acc.setLastName(rs.getString("lastName"));
            acc.setFirstName(rs.getString("firstName"));
            acc.setEmail(rs.getString("email"));
            acc.setUuid(rs.getString("uuid"));
            acc.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            acc.setPricingDuration(rs.getString("pricingDuration"));
            
            result.add(acc);
        }

      } catch (Exception e) {
         LOG.log(Level.SEVERE, "error when updateAccount", e);
         throw e;
      } finally {
        if (connection != null) 
            try{
                connection.close();
                stmt.close();
                rs.close();
            } catch(SQLException e){
            }
      }
      
      return result;
    }
    
    public void reset() throws Exception {
        Connection connection = null;
      try {
        connection = DatabaseUrl.extract().getConnection();

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE account");
        
        
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }
}
