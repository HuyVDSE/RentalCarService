/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.CartError;
import huyvd.cart.CartObject;
import huyvd.tblDiscount.TblDiscountDAO;
import huyvd.tblDiscount.TblDiscountDTO;
import huyvd.util.DateHandle;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
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
@WebServlet(name = "CalculateTotalServlet", urlPatterns = {"/CalculateTotalServlet"})
public class CalculateTotalServlet extends HttpServlet {

    private final String VIEW_CART_CONTROLLER = "view-cart";

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
	String url = VIEW_CART_CONTROLLER;
	String rentalDate = request.getParameter("rentalDate");
	String returnDate = request.getParameter("returnDate");
	String discoutCode = request.getParameter("discountCode"); //need to add discount function

	Date rentalDay = null;
	Date returnDay = null;
	int totalDays = 0;
	int discountValue = 0;

	CartError errors = new CartError();
	boolean foundErr = false;

	try {
	    String curDate = DateHandle.createCurrentDate();
	    Date today = Date.valueOf(curDate);

	    //handle input rental date of user
	    if (rentalDate.isEmpty()) {
		foundErr = true;
		errors.setInputRentalDateErr("Please choose rental date!!");
	    } else {
		rentalDay = Date.valueOf(rentalDate);
	    }

	    //handle input rental date of user
	    if (returnDate.isEmpty()) {
		foundErr = true;
		errors.setInputRentalDateErr("Please choose return date!!");
	    } else {
		returnDay = Date.valueOf(returnDate);
	    }

	    //check if user apply voucher
	    if (!discoutCode.isEmpty()) {
		TblDiscountDAO dao = new TblDiscountDAO();
		TblDiscountDTO dto = dao.getDiscount(discoutCode);

		if (dto == null) {
		    foundErr = true;
		    errors.setNotValidDiscountCodeErr("Not valid voucher!!");
		} else {
		    Date expiryDate = dto.getExpiredDate();
		    totalDays = calculateTotalDay(today, expiryDate);

		    if (totalDays < 0) {
			foundErr = true;
			errors.setExpiredDiscountCodeErr("Voucher is expired!! " + expiryDate);
		    }//check if voucher is expired or not
		    else {
			discountValue = dto.getValue();
		    }
		}//end if dto exist
	    }//end if discount code exist

	    if (foundErr == false) {
		//calculate number of day between rental and today
		totalDays = calculateTotalDay(today, rentalDay);
		if (totalDays < 0) {
		    foundErr = true;
		    errors.setRentalDateErr("Rental date must be greater than or start at today!!");
		}

		//calculate number of day between rental and return day
		totalDays = calculateTotalDay(rentalDay, returnDay);
		if (totalDays <= 0) {
		    foundErr = true;
		    errors.setReturnDateErr("Return date must be greater than rental date!!");
		}
	    }//this case use to prevent null pointer exception

	    if (foundErr) {
		request.setAttribute("ERRORS", errors);
	    } else {
		HttpSession session = request.getSession(false);
		if (session != null) {
		    CartObject cart = (CartObject) session.getAttribute("CUS_CART");
		    if (cart != null) {
			cart.setRentalDate(rentalDay);
			cart.setReturnDate(returnDay);
			cart.setTotalDays(totalDays);
			cart.setDiscountCode(discoutCode);
			cart.setDiscount(discountValue);
		    }
		}
	    }
	} catch (Exception e) {
	    log("CalculateTotalServlet _ Exception: " + e.getMessage());
	} finally {
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTIONS_MAP");
	    url = functionsMap.get(url);

	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private int calculateTotalDay(Date in, Date out) {
	long days = out.getTime() - in.getTime();
	return (int) (days / 86400000);
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
