/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.feedback.FeedbackDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author BlankSpace
 */
@WebServlet(name = "SendFeedbackServlet", urlPatterns = {"/SendFeedbackServlet"})
public class SendFeedbackServlet extends HttpServlet {

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
	String url = "";
	//properties for url rewriting to call search function again
	String searchValue = request.getParameter("txtSearchValue");
	String fromDate = request.getParameter("txtFromDate");
	String toDate = request.getParameter("txtToDate");
	
	//properties for insert feedback
	String orderId = request.getParameter("orderId");
	String carId = request.getParameter("carId");
	String content = request.getParameter("txtContent");
	String point = request.getParameter("cmbPoint");
	
	try {
	    FeedbackDAO dao = new FeedbackDAO();
	    boolean success = dao.updateFeedback(orderId, carId, content, point);
	    if (success) {
		url = "search-order?txtSearchValue=" + searchValue
			+ "&txtFromDate=" + fromDate
			+ "&txtToDate=" + toDate;
		
		request.setAttribute("SUCCESS_NOTIFICATION", "Send Feedback success!!");
	    }
	} catch (SQLException e) {
	    log("SendFeedbackServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("SendFeedbackServlet _ NamingException: " + e.getMessage());
	} finally {
	    response.sendRedirect(url);
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