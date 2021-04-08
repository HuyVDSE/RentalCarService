/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.servlet;

import huyvd.tblCar.TblCarDAO;
import huyvd.tblCar.TblCarDTO;
import huyvd.tblCategory.TblCategoryDAO;
import huyvd.tblCategory.TblCategoryDTO;
import huyvd.util.PageCalculate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
@WebServlet(name = "SearchCarServlet", urlPatterns = {"/SearchCarServlet"})
public class SearchCarServlet extends HttpServlet {

    private final String HOME_CONTROLLER = "homePage";

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
	String url = HOME_CONTROLLER;
	String searchValue = request.getParameter("txtSearch");
	String categoryId = request.getParameter("cmbCategory");
	String rentalDate = request.getParameter("txtRentalDate");
	String returnDate = request.getParameter("txtReturnDate");
	String amountString = request.getParameter("txtAmount");
	String pageString = request.getParameter("page");

	int amount = 1; //default value of amount search
	int page = 1; //default page number

	try {
	    if (pageString != null) {
		page = Integer.parseInt(pageString);
	    }

	    if (!amountString.trim().isEmpty()) {
		amount = Integer.parseInt(amountString);
	    }

	    //do search
	    doSearch(searchValue, categoryId, rentalDate, returnDate, amount, page, request);
	} catch (SQLException e) {
	    log("SearchCarServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("SearchCarServlet _ NamingException: " + e.getMessage());
	} finally {
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTIONS_MAP");
	    url = functionsMap.get(url);

	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    private void doSearch(String searchValue, String categoryId, String rentalDate,
	    String returnDate, int amount, int page, HttpServletRequest request)
	    throws SQLException, NamingException {
	//call DAO
	TblCarDAO dao = new TblCarDAO();
	Map<String, TblCarDTO> searchResult = dao.searchCar(searchValue, categoryId, amount, page);
	Map<String, Integer> rentalResult = dao.loadCarRental(rentalDate, returnDate);
	List<String> listCarRental = dao.getListCarID();

	//go through the list to get real quantity
	if (listCarRental != null) {
	    listCarRental.forEach((carID) -> {
		TblCarDTO dto = searchResult.get(carID);
		//end if cur car exist
		if (dto != null) {
		    int rentalQuantity = rentalResult.get(carID);

		    int currentQuantity = dto.getQuantity() - rentalQuantity;

		    if (currentQuantity < amount) {
			searchResult.remove(carID);
		    } else {
			dto.setQuantity(currentQuantity);
		    }
		}
	    }); //end for
	}//end if list car rental exist

	//import from map to list
	List<TblCarDTO> listCar = new ArrayList<>();
	searchResult.forEach((k, v) -> {
	    listCar.add(v);
	});
	
	//sort list car order by date
	Collections.sort(listCar, (TblCarDTO c1, TblCarDTO c2) -> c2.getYear().compareTo(c1.getYear()));
	request.setAttribute("LIST_CAR", listCar);

	//load category cmb
	TblCategoryDAO categoryDAO = new TblCategoryDAO();
	categoryDAO.loadCategory();
	List<TblCategoryDTO> listCategory = categoryDAO.getListCategory();
	request.setAttribute("CATEGORY_LIST", listCategory);

	//calculate total page for paging
	int totalItems = dao.totalSearchResult(searchValue, categoryId, amount);
	int totalPages = PageCalculate.calculateNumOfPage(totalItems, 10);
	request.setAttribute("TOTAL_PAGES", totalPages);
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
