/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.feedback.FeedbackDAO;
import huyvd.feedback.FeedbackDTO;
import huyvd.tblCar.TblCarDAO;
import huyvd.tblCar.TblCarDTO;
import huyvd.tblCategory.TblCategoryDAO;
import huyvd.tblCategory.TblCategoryDTO;
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

/**
 *
 * @author BlankSpace
 */
@WebServlet(name = "ViewCarDetailServlet", urlPatterns = {"/ViewCarDetailServlet"})
public class ViewCarDetailServlet extends HttpServlet {

    private final String DETAIL_PAGE = "detailPage";

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
	String url = DETAIL_PAGE;
	String carId = request.getParameter("carId");
	String rentalDate = request.getParameter("txtRentalDate");
	String returnDate = request.getParameter("txtReturnDate");
	
	try {
	    TblCarDAO dao = new TblCarDAO();
	    TblCarDTO dto = dao.getCar(carId);

	    //get rental car List
	    Map<String, Integer> rentalResult = dao.loadCarRental(rentalDate, returnDate);
	    List<String> listCarIdRental = dao.getListCarID();
	    
	    //if list car rental exist and carId in this list => the car quantity will be check
	    if (listCarIdRental != null && listCarIdRental.contains(dto.getCarID())) {
		int rentalQuantity = rentalResult.get(dto.getCarID());
		
		//set remaining quantity if this car has been rental
		if (rentalQuantity != 0) {
		    dto.setQuantity(dto.getQuantity() - rentalQuantity);
		}
	    }

	    request.setAttribute("CAR", dto);

	    //load feedback of car
	    FeedbackDAO fbDAO = new FeedbackDAO();
	    fbDAO.loadFeedbackList(carId);
	    List<FeedbackDTO> feedbackList = fbDAO.getListFeedback();
	    if (feedbackList != null) {
		request.setAttribute("FEEDBACK_LIST", feedbackList);
	    }
	    
	    //load category for menu
	    TblCategoryDAO cateDao = new TblCategoryDAO();
	    cateDao.loadCategory();
	    List<TblCategoryDTO> listCategory = cateDao.getListCategory();
	    request.setAttribute("CATEGORY_LIST", listCategory);
	} catch (SQLException e) {
	    log("ViewCarDetailServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("ViewCarDetailServlet _ NamingException: " + e.getMessage());
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
