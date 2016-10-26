/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.servlets;

import visitForm.models.User;

import visitForm.stores.Visit;
import visitForm.models.VisitTable;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.util.ArrayList;

//import visitForm.models.Visit.Plan;

import com.google.gson.Gson;


/**
 *
 * @author Vlad
 */
@WebServlet(name = "saveVisit", urlPatterns = {"/saveVisit"})
public class saveVisit extends HttpServlet {

  
     private DataSource dataSource;
    
    @Override
    public void init() throws ServletException {
		try {
                        dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + "jdbc/db");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
    

 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
       
        String jsonString = request.getParameter("data");
       
        
        Connection conn = null ;
        HttpSession session = request.getSession(false);
        User us = (User) session.getAttribute("user");
        String activeUserEmail = us.getUsername();
        VisitTable vt = new VisitTable();
      
        try {

           conn= dataSource.getConnection();
            
            //System.out.println(jsonString);
            
            // https://futurestud.io/tutorials/gson-mapping-of-nested-objects
           Visit visitToSave = new Gson().fromJson(jsonString, Visit.class);
           
 
           boolean success = vt.saveVisit(activeUserEmail, visitToSave, conn);
           if (!success){//this is just if user modified javascript and tried to add visit to not his patient 
               response.setStatus(400);
               response.setContentType("text/plain");
               response.getWriter().write("Visit not saved");
           }
            
        }
        catch (SQLException sqle){
                sqle.printStackTrace();
                response.setContentType("text/plain");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Database error");
        }
        finally{
                if (conn != null){
                    try {conn.close();} 
                    catch (SQLException ignore) { }
                }
        }
         


    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
