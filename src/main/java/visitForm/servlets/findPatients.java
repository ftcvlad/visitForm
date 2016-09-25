package visitForm.servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import visitForm.models.User;
import visitForm.models.Patient;
import visitForm.models.PatientsTable;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import org.json.simple.JSONObject;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
/**
 *
 * @author Vlad
 */
@WebServlet(urlPatterns = {"/findPatients"})
public class findPatients extends HttpServlet {

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
       
       
        String name = request.getParameter("name");
       
        
        Connection conn = null ;
        HttpSession session = request.getSession(false);
        User us = (User) session.getAttribute("user");
        String activeUserEmail = us.getUsername();
        PatientsTable pt = new PatientsTable();
        ObjectMapper mapper = new ObjectMapper();
        try {

            conn= dataSource.getConnection();
            ArrayList<Patient> allPatients = pt.findPatients(activeUserEmail,name,conn);
            
            
            final OutputStream out = new ByteArrayOutputStream();
            mapper.writeValue(out, allPatients);

               
          //  response.setContentType("application/json");
            PrintWriter out2 = response.getWriter();
            out2.print(out.toString());//"[{},{},{}...]"

            out2.flush(); 
           
           
          
            
        }
        catch (SQLException sqle){
                sqle.printStackTrace();
            
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
