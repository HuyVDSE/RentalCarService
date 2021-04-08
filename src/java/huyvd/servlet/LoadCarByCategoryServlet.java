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
@WebServlet(name = "LoadCarByCategoryServlet", urlPatterns = {"/LoadCarByCategoryServlet"})
public class LoadCarByCategoryServlet extends HttpServlet {

    private final String HOME_PAGE = "homePage";

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
	String url = HOME_PAGE;
	String categoryId = request.getParameter("cid");

	try {
	    loadAllCar(request, categoryId);
	    request.setAttribute("DEFAULT_TOTAL_PAGES", 1);

	    //load category list
	    TblCategoryDAO dao = new TblCategoryDAO();
	    dao.loadCategory();
	    List<TblCategoryDTO> listCategory = dao.getListCategory();
	    request.setAttribute("CATEGORY_LIST", listCategory);
	} catch (SQLException e) {
	    log("LoadCarByCategoryServlet _ SQLException: " + e.getMessage());
	} catch (NamingException e) {
	    log("LoadCarByCategoryServlet _ NamingException: " + e.getMessage());
	}finally {
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTIONS_MAP");
	    url = functionsMap.get(url);
	    
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
    }

    public void loadAllCar(HttpServletRequest request, String categoryID)
	    throws SQLException, NamingException {
	TblCarDAO carDAO = new TblCarDAO();
	carDAO.loadCarByCategory(categoryID);
	List<TblCarDTO> listCar = carDAO.getListCar();
	request.setAttribute("LIST_CAR", listCar);

	int totalCar = carDAO.getTotalCarByCategory(categoryID);
	int totalPages = PageCalculate.calculateNumOfPage(totalCar, 10);
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
