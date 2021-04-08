/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblUser.TblUserDAO;
import huyvd.tblUser.TblUserDTO;
import huyvd.tblUser.UserErrors;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author BlankSpace
 */
@WebServlet(name = "ActiveAccountServlet", urlPatterns = {"/ActiveAccountServlet"})
public class ActiveAccountServlet extends HttpServlet {
    private final String ERROR_PAGE = "active-account-page";
    private final String SUCCESS_PAGE = "home";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	
	String url = ERROR_PAGE;
	String confirmCode = request.getParameter("txtConfirmCode");
	
	UserErrors errors = new UserErrors();
	boolean foundErr = false;
	
	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		String activeCode = (String) session.getAttribute("ACTIVATION_CODE");
		
		if (!confirmCode.equals(activeCode)) {
		    foundErr = true;
		    errors.setActiveCodeError("Incorrect active code! Please try again!");
		}
		
		if (foundErr) {
		    request.setAttribute("ERRORS", errors);
		} else {
		    TblUserDTO curUser = (TblUserDTO) session.getAttribute("ACCOUNT");
		    String email = curUser.getEmail();
		    
		    TblUserDAO dao = new TblUserDAO();
		    dao.activeAccount(email);
		    
		    url = SUCCESS_PAGE;
		}
	    }
	} catch (SQLException e) {
	    log("ActiveAccountServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("ActiveAccountServlet _ NamingException: " + e.getMessage());
	} finally {
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTIONS_MAP");
	    url = functionsMap.get(url);
	    
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
	
	
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
