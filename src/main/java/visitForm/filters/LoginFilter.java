package visitForm.filters;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;

/**
 *http://stackoverflow.com/questions/13274279/authentication-filter-and-servlet-for-login
 * http://stackoverflow.com/questions/20371220/what-is-the-difference-between-response-sendredirect-and-request-getrequestdis
 * @author Vlad
 */

//@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})

@WebFilter("/*")
public class LoginFilter implements Filter {

    public LoginFilter(){
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
         System.out.println("i HAVE DONE THE FCKING FILER!");
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
      
        String contextPath = request.getContextPath();
        String loginURI = contextPath + "/Login";
        String registerURI = contextPath + "/Register";
        String requestURI = request.getRequestURI();
        
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        
       
        boolean loginRequest = requestURI.equals(loginURI);
        boolean registerRequest = requestURI.equals(registerURI);
        
        System.out.println(request.getRequestURI()+" "+loggedIn+" "+loginRequest+" "+registerRequest+" "+System.currentTimeMillis());
      
       
        if (requestURI.startsWith("/visitFormMaven/static/")) {
            chain.doFilter(request, response);
        } 
        else if (loggedIn){
            if (loginRequest || registerRequest){
                response.sendRedirect(contextPath);//+++
            }
            else{
                
                chain.doFilter(request, response);
            }
        }
        else{
            if ( loginRequest || registerRequest){
              
                 chain.doFilter(request, response);
            }
            else{
                response.sendRedirect(loginURI);//+++
            }
        }
        
       //sendRedirect -- full path
       //forward -- relative!
    }
  
    
    
    
    @Override
    public void destroy() {   
    }       
    
    
    @Override
    public void init(FilterConfig fc) {   //called when server starts!    
       System.out.println("--------------------------------------------------------");
    }
}
