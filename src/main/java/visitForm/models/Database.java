/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.models;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import javax.naming.NamingException;
import javax.naming.InitialContext;
/**
 *
 * @author Vlad
 */
public class Database {
    
        private DataSource dataSource;

        public Database(String jndiname) {
            try {
                dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
            } catch (NamingException e) {
                // Handle error that it's not configured in JNDI.
                throw new IllegalStateException(jndiname + " is missing in JNDI!", e);
            }
        }

        public Connection getConnection() {
            try{
                 return dataSource.getConnection();
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
            return null;
        }
    
    
    
}
