/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.CartObject;
import huyvd.cart.cartItem;
import java.io.IOException;
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
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "view-cart-page";

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
	String url = VIEW_CART_PAGE;

	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		CartObject cart = (CartObject) session.getAttribute("CUS_CART");
		if (cart != null) {
		    //update total price of item in cart
		    for (cartItem item : cart.getItemList()) {
			item.updateTotal();
		    }

		    session.setAttribute("CUS_CART", cart);
		    //get cart from sesion and put to request
		    request.setAttribute("CUS_CART", cart);
		    double totalPrice = cart.getTotalPriceOfCart();
		    request.setAttribute("TOTAL_PRICE", totalPrice);
		    
		    if (cart.getDiscount() != 0) {
			double priceAfterDiscount = cart.getTotalPriceOfCartAfterDiscount();
			request.setAttribute("PRICE_AFTER_DISCOUNT", priceAfterDiscount);
		    } else {
			request.setAttribute("PRICE_AFTER_DISCOUNT", totalPrice);
		    }
		}
	    }//end if session exist
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
