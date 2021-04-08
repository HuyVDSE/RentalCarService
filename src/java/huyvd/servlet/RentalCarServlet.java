/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.CartObject;
import huyvd.tblUser.TblUserDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
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
@WebServlet(name = "RentalCarServlet", urlPatterns = {"/RentalCarServlet"})
public class RentalCarServlet extends HttpServlet {

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
	String carID = request.getParameter("carID");
	String url = null;
	String role = "GUEST";

	try {
	    //get car object
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		TblUserDTO curUser = (TblUserDTO) session.getAttribute("ACCOUNT");
		if (curUser != null) {
		    role = curUser.getRole();
		}
		
		//check if user isn't login -> redirect to login page
		if (role.equals("GUEST")) {
		    url = "login-page";
		} else {
		    CartObject cart = (CartObject) session.getAttribute("CUS_CART");

		    if (cart == null) {
			cart = new CartObject();
		    }//end if cart object is exist

		    cart.addItemToCart(carID);
		    session.setAttribute("CUS_CART", cart);

		    //using url rewriting to keep detail page
		    url = "view-detail?carId=" + carID;
		}
	    }//end if session is exist

	} catch (SQLException e) {
	    log("RentalCarServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("RentalCarServlet _ NamingException: " + e.getMessage());
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
