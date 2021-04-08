/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.cart.cartItem;
import huyvd.tblOrder.TblOrderDAO;
import huyvd.tblOrder.TblOrderDTO;
import huyvd.tblOrderDetail.TblOrderDetailDAO;
import huyvd.tblUser.TblUserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
@WebServlet(name = "TrackRentalOrderServlet", urlPatterns = {"/TrackRentalOrderServlet"})
public class TrackRentalOrderServlet extends HttpServlet {

    private final String TRACK_RENTAL_PAGE = "track-rental-page";

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
	String url = TRACK_RENTAL_PAGE;
	String userId = "";
	String role = "";
	
	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		TblUserDTO curUser = (TblUserDTO) session.getAttribute("ACCOUNT");
		if (curUser != null) {
		    userId = curUser.getEmail();
		    role = curUser.getRole();
		}
	    }//end if session exist

	    //get order list
	    TblOrderDAO orderDAO = new TblOrderDAO();
	    if (role.equals("ADMIN")) {
		orderDAO.getListOrderByAdmin();
	    } else {
		orderDAO.getListOrderByUserId(userId);
	    }
	    List<TblOrderDTO> listOrder = orderDAO.getListRentalOrder();

	    //use map to store order detail list with orderId is key
	    Map<String, List<cartItem>> orderDetailMap = null;
	    
	    if (!listOrder.isEmpty()) {
		orderDetailMap = new HashMap<>();
		TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
		
		for (TblOrderDTO order : listOrder) {
		    String orderId = order.getOrderId();
		    List<cartItem> listOrderDetail = orderDetailDAO.getOrderDetail(orderId);
		    orderDetailMap.put(orderId, listOrderDetail);
		}
	    }
	    
	    request.setAttribute("ORDER_LIST", listOrder);
	    
	    request.setAttribute("ORDER_DETAIL_MAP", orderDetailMap);
	    
	} catch (SQLException e) {
	    log("TrackRentalOrderServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("TrackRentalOrderServlet _ NamingException: " + e.getMessage());
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
