/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.servlets;

import visitForm.models.User;

import visitForm.models.Visit;
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
import com.google.gson.Gson;



/**
 *
 * @author Vlad
 */
@WebServlet(name = "getVisits", urlPatterns = {"/getVisits"})
public class getVisits extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
   
        Connection conn = null ;
        HttpSession session = request.getSession(false);
        User us = (User) session.getAttribute("user");
        String activeUserEmail = us.getUsername();
        VisitTable vt = new VisitTable();
      
        try {
           int id = Integer.parseInt(request.getParameter("id"));
           conn= dataSource.getConnection();
            
            
         
           ArrayList<Visit> retrievedVisits = vt.getVisits(activeUserEmail, id, conn);
 
            String jsonString = new Gson().toJson(retrievedVisits);
            
//            Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
//            String prettyJson = gson.toJson(retrievedVisits);
//            System.out.println(prettyJson);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(jsonString);
            
        }
        catch (java.lang.NumberFormatException e){
                response.setStatus(400);
                response.setContentType("text/plain");
                response.getWriter().write("request should contain proper id");
               
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

}
