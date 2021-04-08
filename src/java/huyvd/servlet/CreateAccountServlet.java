/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblUser.TblUserDAO;
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

/**
 *
 * @author BlankSpace
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    private final String ERROR_PAGE = "create-account-page";
//    private final String SUCCESS_PAGE = "login-page";
    private final String SUCCESS_PAGE = "create-account-page";
    
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
	
	String email = request.getParameter("txtEmail");
	String password = request.getParameter("txtPassword");
	String confirmPassword = request.getParameter("txtConfirm");
	String name = request.getParameter("txtFullname");
	String phone = request.getParameter("txtPhone");
	String address = request.getParameter("txtAddress");
	
	UserErrors errors = new UserErrors();
	boolean foundErr = false;
	
	try {
	    //check user error
	    //check email format
	    if (!email.matches("(\\w*\\d*)+@(\\w+\\.\\w+){1}(\\.\\w+)?")) {
		foundErr = true;
		errors.setEmailError("Email format Error!");
	    }
	    
	    //check password length
	    if (password.length() < 3 || password.length() > 50) {
		foundErr = true;
		errors.setPasswordLengthError("Password must from 3 - 50 chars!");
	    } else if (!confirmPassword.equals(password)) {
		foundErr = true;
		errors.setPasswordConfirmNotMatched("Confirm not matched!");
	    }
	    
	    //check name length
	    if (name.length() < 6 || name.length() > 50) {
		foundErr = true;
		errors.setNameLengthError("Name must from 6 - 50 chars!");
	    }
	    
	    //check phone format
	    if (phone.length() < 6 || phone.length() > 20) {
		foundErr = true;
		errors.setPhoneFormatError("Phone must from 6 to 20 digits!");
	    } else if (!phone.matches("\\d+")) {
		foundErr = true;
		errors.setPhoneFormatError("Phone Only accept digits!");
	    }
	    
	    if (address.length() < 6 || address.length() > 150) {
		foundErr = true;
		errors.setAddressLengthError("Address must from 6 to 150");
	    }
	    
	    //found error -> notify for user
	    if (foundErr) {
		request.setAttribute("ERRORS", errors);
	    } else {
		TblUserDAO dao = new TblUserDAO();
		
		//default status is "new"
		boolean result = dao.createAccount(email, password, name, phone, address);
		
		if (result) {
		    request.setAttribute("SUCCESS", result);
		    url = SUCCESS_PAGE;
//		    url = ERROR_PAGE;
		}
	    }
	} catch (SQLException e) {
	    log("CreateAccountServlet _ SQLException: " + e.getMessage());
	    
	    //check if email is existed in DB
	    if (e.getMessage().contains("duplicate")) {
		errors.setEmailExisted(email + " is existed!");
		request.setAttribute("ERRORS", errors);
	    }
	} catch (NamingException e) {
	    log("CreateAccountServlet _ NamingException: " + e.getMessage());
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
