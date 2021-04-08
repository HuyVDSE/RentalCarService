/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblCar;

import huyvd.util.DateHandle;
import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class TblCarDAO implements Serializable {

    private List<TblCarDTO> listCar;
    private List<String> listCarID;

    public List<TblCarDTO> getListCar() {
	return listCar;
    }

    public List<String> getListCarID() {
	return listCarID;
    }

    public void getAllCar(String role) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	boolean isAdmin = false;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		//check role
		if (role.equals("ADMIN")) {
		    isAdmin = true;
		}

		String sql = "SELECT carID, carName, image, color, year, price, quantity, ca.categoryName, description "
			+ "FROM tblCar c join tblCategory ca ON c.categoryID = ca.categoryID "
			+ "WHERE quantity > ? "
			+ "GROUP BY carID, carName, image, color, year, price, quantity, ca.categoryName, description "
			+ "ORDER BY year DESC "
			+ "OFFSET 0 ROWS "
			+ "FETCH NEXT 10 ROWS ONLY ";
		pst = cn.prepareStatement(sql);

		if (isAdmin) {
		    pst.setInt(1, -1);
		} else {
		    pst.setInt(1, 0);
		}

		rs = pst.executeQuery();
		while (rs.next()) {
		    String carID = rs.getString("carID");
		    String carName = rs.getString("carName");
		    String image = rs.getString("image");
		    String color = rs.getString("color");
		    Date year = DateHandle.maintainDate(rs.getDate("year"));
		    double price = rs.getDouble("price");
		    int quantity = rs.getInt("quantity");
		    String categoryID = rs.getString("categoryName");
		    String description = rs.getString("description");

		    TblCarDTO dto = new TblCarDTO(carID, carName, image, color, year, price, quantity, categoryID, description);

		    if (this.listCar == null) {
			this.listCar = new ArrayList<>();
		    }

		    this.listCar.add(dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
    }

    public int getTotalCar(String role) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	boolean isAdmin = false;
	int totalCar = 0;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		//check role
		if (role.equals("ADMIN")) {
		    isAdmin = true;
		}

		String sql = "SELECT count(carID) as totalCar "
			+ "FROM tblCar "
			+ "WHERE quantity > ? ";
		pst = cn.prepareStatement(sql);

		if (isAdmin) {
		    pst.setInt(1, -1);
		} else {
		    pst.setInt(1, 0);
		}

		rs = pst.executeQuery();
		if (rs.next()) {
		    totalCar = rs.getInt("totalCar");
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return totalCar;
    }

    public Map<String, TblCarDTO> searchCar(String searchValue, String categoryID, int amount, int page)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Map<String, TblCarDTO> result = new HashMap<>();

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT carID, carName, image, color, year, price, quantity, ca.categoryName, description "
			+ "FROM tblCar c join tblCategory ca ON c.categoryID = ca.categoryID "
			+ "WHERE quantity >= ? ";

		//checking searchValue to create sql String
		if (!searchValue.trim().isEmpty()) {
		    sql += "AND carName like '%" + searchValue + "%'";
		}

		//checking category to create sql String
		if (!categoryID.trim().isEmpty()) {
		    sql += "AND ca.categoryID = " + categoryID;
		}

		sql += "GROUP BY carID, carName, image, color, year, price, quantity, ca.categoryName, description "
			+ "ORDER BY year DESC "
			+ "OFFSET ? ROWS "
			+ "FETCH NEXT 10 ROWS ONLY ";

		pst = cn.prepareStatement(sql);

		pst.setInt(1, amount);
		pst.setInt(2, (page - 1) * 10);

		//execute query
		rs = pst.executeQuery();
		while (rs.next()) {
		    //carID, carName, image, color, year, price, quantity, ca.categoryName
		    String carId = rs.getString("carID");
		    String carName = rs.getString("carName");
		    String image = rs.getString("image");
		    String color = rs.getString("color");
		    Date year = DateHandle.maintainDate(rs.getDate("year"));
		    Double price = rs.getDouble("price");
		    int quantity = rs.getInt("quantity");
		    String categoryName = rs.getString("categoryName");
		    String description = rs.getString("description");

		    TblCarDTO dto = new TblCarDTO(carId, carName, image, color, year, price, quantity, categoryName, description);

		    result.put(carId, dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return result;
    }

    public int totalSearchResult(String searchValue, String categoryID, int amount)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT COUNT(carID) as totalCar "
			+ "FROM tblCar c join tblCategory ca ON c.categoryID = ca.categoryID "
			+ "WHERE quantity >= ? ";

		//checking searchValue to create sql String
		if (!searchValue.trim().isEmpty()) {
		    sql += "AND carName like '%" + searchValue + "%'";
		}

		//checking category to create sql String
		if (!categoryID.trim().isEmpty()) {
		    sql += "AND ca.categoryID = " + categoryID;
		}

		pst = cn.prepareStatement(sql);

		pst.setInt(1, amount);

		//execute query
		rs = pst.executeQuery();
		if (rs.next()) {
		    return rs.getInt("totalCar");
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return 0;
    }

    public Map<String, Integer> loadCarRental(String rentalDate, String returnDate)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Map<String, Integer> result = new HashMap<>();

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT c.carID, sum(od.quantity) as quantity "
			+ "FROM tblCar c join tblOrderDetail od on c.carID = od.carID join tblOrder o on od.orderID = o.orderID "
			+ "WHERE ((bookingDate BETWEEN ? AND ?) OR (returnDate BETWEEN ? AND ?)) "
			+ "GROUP BY c.carID ";

		pst = cn.prepareStatement(sql);
		pst.setString(1, rentalDate);
		pst.setString(2, returnDate);
		pst.setString(3, rentalDate);
		pst.setString(4, returnDate);

		rs = pst.executeQuery();
		while (rs.next()) {
		    String carId = rs.getString("carID");
		    int quantity = rs.getInt("quantity");

		    result.put(carId, quantity);

		    if (this.listCarID == null) {
			this.listCarID = new ArrayList<>();
		    }

		    this.listCarID.add(carId);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return result;
    }

    public void loadCarByCategory(String categoryId) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT carID, image, carName, color, year, price, quantity, ca.categoryName, description "
			+ "FROM tblCar c JOIN tblCategory ca ON c.categoryId = ca.categoryId "
			+ "WHERE ca.categoryId = ? ";

		pst = cn.prepareStatement(sql);
		pst.setString(1, categoryId);
		rs = pst.executeQuery();

		while (rs.next()) {
		    //carID, carName, image, color, year, price, quantity, ca.categoryName
		    String carId = rs.getString("carID");
		    String carName = rs.getString("carName");
		    String image = rs.getString("image");
		    String color = rs.getString("color");
		    Date year = DateHandle.maintainDate(rs.getDate("year"));
		    Double price = rs.getDouble("price");
		    int quantity = rs.getInt("quantity");
		    String categoryName = rs.getString("categoryName");
		    String description = rs.getString("description");

		    TblCarDTO dto = new TblCarDTO(carId, carName, image, color, year, price, quantity, categoryName, description);

		    if (this.listCar == null) {
			this.listCar = new ArrayList<>();
		    }

		    this.listCar.add(dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
    }

    public int getTotalCarByCategory(String categoryId) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	int totalCar = 0;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {

		String sql = "SELECT count(carID) as totalCar "
			+ "FROM tblCar c join tblCategory ca on c.categoryID = ca.categoryID "
			+ "WHERE ca.categoryID = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, categoryId);
		rs = pst.executeQuery();
		if (rs.next()) {
		    totalCar = rs.getInt("totalCar");
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return totalCar;
    }

    public TblCarDTO getCar(String carId) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT carID, image, carName, color, year, price, quantity, ca.categoryName, description "
			+ "FROM tblCar c JOIN tblCategory ca ON c.categoryId = ca.categoryId "
			+ "WHERE carID = ? ";

		pst = cn.prepareStatement(sql);
		pst.setString(1, carId);
		rs = pst.executeQuery();

		if (rs.next()) {
		    //carID, carName, image, color, year, price, quantity, ca.categoryName
		    String carName = rs.getString("carName");
		    String image = rs.getString("image");
		    String color = rs.getString("color");
		    Date year = DateHandle.maintainDate(rs.getDate("year"));
		    Double price = rs.getDouble("price");
		    int quantity = rs.getInt("quantity");
		    String categoryName = rs.getString("categoryName");
		    String description = rs.getString("description");

		    TblCarDTO dto = new TblCarDTO(carId, carName, image, color, year, price, quantity, categoryName, description);
		    return dto;
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
	return null;
    }
}
