package com.gigaspaces.examples.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A servlet which updates the session with the content of the request parameters and 
 * forwards to the SessionContents.jsp which displays the contents of the session 
 * @author Uri Cohen
 */
public class UpdateSessionServlet extends HttpServlet {       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("********** Got Request **********");
        HttpSession session = request.getSession(true);  
        // set session info if needed
        String dataName = request.getParameter("dataname");
        String dataValue = request.getParameter("datavalue");
        if (isStringNotEmpty(dataName) && isStringNotEmpty(dataValue)) {            
            session.setAttribute(dataName, dataValue);
        }              
        request.getRequestDispatcher("SessionContents.jsp").forward(request, response);
	}

	private boolean isStringNotEmpty(String str) {
		return (str != null && str.trim().length() > 0);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
