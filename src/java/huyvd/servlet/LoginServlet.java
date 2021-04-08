/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblUser.TblUserDAO;
import huyvd.tblUser.TblUserDTO;
import huyvd.tblUser.UserErrors;
import huyvd.util.CaptchaVerify;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

//    private final String INVALID_PAGE = "invalid";
    private final String CAPTCHA_ERROR_PAGE = "login-page";
    private final String INVALID_PAGE = "login-page";
    private final String HOME = "home";
    private final String ACTIVE_PAGE = "active-required-page";

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
	String url = INVALID_PAGE;

	String email = request.getParameter("txtEmail");
	String password = request.getParameter("txtPassword");

	boolean loginSuccess = false;
	UserErrors errors = new UserErrors();

	try {
	    TblUserDAO dao = new TblUserDAO();
	    TblUserDTO user = dao.checkLogin(email, password);
	    if (user != null) {
		String gRecaptchaREsponse = request.getParameter("g-recaptcha-response");

		//verify CAPTCHA
		loginSuccess = CaptchaVerify.verify(gRecaptchaREsponse);
		if (!loginSuccess) {
//		    request.setAttribute("CAPTCHA_ERROR", "Captcha invalid!");
		    errors.setCaptchaError("Captcha invalid!");
		    url = CAPTCHA_ERROR_PAGE;
		} else {
		    HttpSession session = request.getSession();
		    session.setAttribute("ACCOUNT", user);

		    //redirect user to active page
		    String status = user.getStatus();
		    if (status.equalsIgnoreCase("active")) {
			url = HOME;
		    } else {
			url = ACTIVE_PAGE;
		    }
		}
	    } else {
		errors.setInvalidUserError("Invalid Account!");
	    }
	    request.setAttribute("ERRORS", errors);
	} catch (IOException e) {
	    log("LoginServlet _ IOException: " + e.getMessage());
	} catch (SQLException e) {
	    log("LoginServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("LoginServlet _ NamingException: " + e.getMessage());
	} finally {
	    if (loginSuccess) {
		response.sendRedirect(url);
	    } else {
		ServletContext context = request.getServletContext();
		Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTIONS_MAP");
		url = functionsMap.get(url);

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	    }
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
