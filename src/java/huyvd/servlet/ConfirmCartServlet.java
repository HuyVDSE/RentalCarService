/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.CartError;
import huyvd.cart.CartObject;
import huyvd.cart.cartItem;
import huyvd.tblCar.TblCarDAO;
import huyvd.tblCar.TblCarDTO;
import huyvd.tblOrder.TblOrderDAO;
import huyvd.tblOrderDetail.TblOrderDetailDAO;
import huyvd.tblUser.TblUserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "ConfirmCartServlet", urlPatterns = {"/ConfirmCartServlet"})
public class ConfirmCartServlet extends HttpServlet {

    private final String CHECKOUT_SUCCESS_PAGE = "confirm-success-page";
    private final String ERROR_PAGE = "view-cart";

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
	String userId = "";

	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		//get current user
		TblUserDTO user = (TblUserDTO) session.getAttribute("ACCOUNT");
		if (user != null) {
		    userId = user.getEmail();
		}//end if current user exist

		CartObject cart = (CartObject) session.getAttribute("CUS_CART");
		if (cart != null) {
		    if (!cart.getItemList().isEmpty()) {
			//check error
			CartError errors = new CartError();
			boolean founfErr = false;
			
			String rentalQuantityError = checkCarQuantity(cart);
			if (!rentalQuantityError.isEmpty()) {
			    errors.setQuantityErr(rentalQuantityError + "Please change quantity to checkout!!");
			    founfErr = true;
			}
			
			if (founfErr) {
			    request.setAttribute("ERRORS", errors);
			} else {
			    String orderId = cart.generateCartId();
			    String orderStatus = "active";
			    
			    TblOrderDAO orderDAO = new TblOrderDAO();
			    boolean createOrderSuccessful = orderDAO.createOrderRental(orderId, userId, orderStatus, cart);
			    if (createOrderSuccessful) {
				TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
				boolean success = orderDetailDAO.createOrder(orderId, cart.getItemList());
				
				if (success) {
				    //remove cart
				    session.setAttribute("CUS_CART", null);
				    
				    url = CHECKOUT_SUCCESS_PAGE;
				}
			    }//end if order detail insert success
			}
		    }//end if cart has item
		}//end if cart exist
	    }//end if session exist
	} catch (SQLException e) {
	    log("ConfirmCartServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("ConfirmCartServlet _ NamingException: " + e.getMessage());
	} finally {
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTIONS_MAP");
	    url = functionsMap.get(url);

	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private String checkCarQuantity(CartObject cart) throws SQLException, NamingException {
	String errorString = "";
	for (cartItem item : cart.getItemList()) {
	    TblCarDTO car = item.getCar();
	    int rentalQuantity = item.getQuantity();

	    int newQuantity = calculateNewQuantity(car, rentalQuantity, cart);
	    if (newQuantity < 0) {
		//set error String
		int quantityLeft = newQuantity + rentalQuantity;
		errorString += car.getName() + " just have " + quantityLeft + " left!\n";
	    }
	}
	return errorString;
    }

    private int calculateNewQuantity(TblCarDTO car, int rentalQuantity, CartObject cart)
	    throws SQLException, NamingException {
	TblCarDAO dao = new TblCarDAO();
	//get rental car List
	Map<String, Integer> rentalResult = dao.loadCarRental(cart.getRentalDate().toString(), cart.getReturnDate().toString());
	List<String> listCarIdRental = dao.getListCarID();

	//if list car rental not null and carId in this list => the car quantity will be check
	if (listCarIdRental != null && listCarIdRental.contains(car.getCarID())) {
	    //get rental quantity of this car in database
	    int rentalQuantityDB = rentalResult.get(car.getCarID());

	    //return number of car left if this car has been rental
	    if (rentalQuantityDB != 0) {
		return car.getQuantity() - rentalQuantityDB - rentalQuantity;
	    }
	}
	return car.getQuantity() - rentalQuantity;
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
