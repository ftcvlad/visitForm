/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.servlets;

import visitForm.models.PatientsTable;
import visitForm.models.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;




/**
 *
 * @author Vlad
 */
@WebServlet(name = "deletePatient", urlPatterns = {"/deletePatient"})
public class deletePatient extends HttpServlet {

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
        
        
        	
        int patientId = Integer.parseInt(request.getParameter("patientId"));
        
        Connection conn = null ;
        HttpSession session = request.getSession(false);
        User us = (User) session.getAttribute("user");
        String activeUserEmail = us.getUsername();
        PatientsTable pt = new PatientsTable();

        try {

            conn= dataSource.getConnection();

            int nDeleted = pt.deletePatient(activeUserEmail, patientId,conn);
            if (nDeleted==0){
                    response.setContentType("text/plain");
                    response.setStatus(400);
                    response.getWriter().write("No rows updated");
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
