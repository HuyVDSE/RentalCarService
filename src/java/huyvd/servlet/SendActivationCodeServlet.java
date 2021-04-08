/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblUser.TblUserDTO;
import huyvd.util.MailUtil;
import java.io.IOException;
import java.util.Random;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "SendActivationCodeServlet", urlPatterns = {"/SendActivationCodeServlet"})
public class SendActivationCodeServlet extends HttpServlet {

    private final String CONFIRM_PAGE = "active-account.jsp";

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
	String url = CONFIRM_PAGE;

	try {
	    //get current user email
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		TblUserDTO curUser = (TblUserDTO) session.getAttribute("ACCOUNT");
		String userMail = curUser.getEmail();

		//generate random code with 6 digits
		String activationCode = generateRandomCode(6);

		//set to request scope to use for confirm again
		session.setAttribute("ACTIVATION_CODE", activationCode);

		//send activation mail
		MailUtil.sendActivationCode(activationCode, userMail);
	    }//end if session exist
	} catch (MessagingException e) {
	    log("SendActivationCodeServlet _ MessagingException: " + e.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private String generateRandomCode(int codeLength) {
	String code = "";
	Random rd = new Random();

	for (int i = 0; i < codeLength; i++) {
	    code += rd.nextInt(10);
	}
	return code;
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
