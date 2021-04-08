/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrder;

import huyvd.cart.CartObject;
import huyvd.tblUser.TblUserDTO;
import huyvd.util.DateHandle;
import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class TblOrderDAO implements Serializable {

    private List<TblOrderDTO> listRentalOrder;

    public List<TblOrderDTO> getListRentalOrder() {
	return listRentalOrder;
    }

    public boolean createOrderRental(String orderId, String userId, String status, CartObject cart)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "INSERT tblOrder(orderID, userID, status, bookingDate, returnDate, totalAmount, totalAfterDiscount) "
			+ "VALUES(?,?,?,?,?,?,?) ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, orderId);
		pst.setString(2, userId);
		pst.setString(3, status);
		pst.setDate(4, cart.getRentalDate());
		pst.setDate(5, cart.getReturnDate());
		pst.setDouble(6, cart.getTotalPriceOfCart());
		pst.setDouble(7, cart.getTotalPriceOfCartAfterDiscount());

		int row = pst.executeUpdate();
		if (row > 0) {
		    return true;
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
	return false;
    }

    public void getListOrderByUserId(String userId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT orderID, userID, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount "
			+ "FROM tblOrder o join tblUser u on o.userID = u.email "
			+ "WHERE o.userID = ? "
			+ "GROUP BY o.orderID, userID, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount\n"
			+ "ORDER BY bookingDate DESC";
		
		pst = cn.prepareStatement(sql);
		pst.setString(1, userId);

		rs = pst.executeQuery();
		while (rs.next()) {
		    String orderId = rs.getString("orderID");
		    userId = rs.getString("userID");
		    Date rentalDate = DateHandle.maintainDate(rs.getDate("bookingDate"));
		    Date returnDate = DateHandle.maintainDate(rs.getDate("returnDate"));
		    String status = rs.getString("status");
		    double totalAmount = rs.getDouble("totalAmount");
		    double totalAfterDiscount = rs.getDouble("totalAfterDiscount");

		    TblOrderDTO dto = new TblOrderDTO(orderId, userId, rentalDate, returnDate, status, totalAmount, totalAfterDiscount);

		    if (listRentalOrder == null) {
			listRentalOrder = new ArrayList<>();
		    }

		    listRentalOrder.add(dto);
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

    public void getListOrderByAdmin() throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount "
			+ "FROM tblOrder "
			+ "GROUP BY orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount "
			+ "ORDER BY bookingDate DESC";
		
		pst = cn.prepareStatement(sql);

		rs = pst.executeQuery();
		while (rs.next()) {
		    String orderId = rs.getString("orderID");
		    String userId = rs.getString("userID");
		    Date rentalDate = DateHandle.maintainDate(rs.getDate("bookingDate"));
		    Date returnDate = DateHandle.maintainDate(rs.getDate("returnDate"));
		    String status = rs.getString("status");
		    double totalAmount = rs.getDouble("totalAmount");
		    double totalAfterDiscount = rs.getDouble("totalAfterDiscount");

		    TblOrderDTO dto = new TblOrderDTO(orderId, userId, rentalDate, returnDate, status, totalAmount, totalAfterDiscount);

		    if (listRentalOrder == null) {
			listRentalOrder = new ArrayList<>();
		    }

		    listRentalOrder.add(dto);
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

    public void searchOrder(String searchValue, String fromDate, String toDate, TblUserDTO user)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String role = user.getRole();

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT o.orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount "
			+ "FROM tblOrder o, tblOrderDetail od,  tblCar c "
			+ "WHERE o.orderID = od.orderID AND od.carID = c.carID ";

		//check role
		if (!role.equals("ADMIN")) {
		    sql += "AND userID = '" + user.getEmail() + "'";
		}

		if (!searchValue.trim().isEmpty()) {
		    sql += "AND carName like '%" + searchValue + "%'";
		}

		if (!fromDate.trim().isEmpty() && !toDate.trim().isEmpty()) {
		    sql += "AND (bookingDate BETWEEN '" + fromDate + "' AND '" + toDate + "') ";
		}

		sql += "GROUP BY o.orderID, userID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount "
			+ "ORDER BY bookingDate DESC ";
		
		pst = cn.prepareStatement(sql);

		rs = pst.executeQuery();
		while (rs.next()) {
		    String orderId = rs.getString("orderID");
		    String userId = rs.getString("userID");
		    Date rentalDate = DateHandle.maintainDate(rs.getDate("bookingDate"));
		    Date returnDate = DateHandle.maintainDate(rs.getDate("returnDate"));
		    String status = rs.getString("status");
		    double totalAmount = rs.getDouble("totalAmount");
		    double totalAfterDiscount = rs.getDouble("totalAfterDiscount");

		    TblOrderDTO dto = new TblOrderDTO(orderId, userId, rentalDate, returnDate, status, totalAmount, totalAfterDiscount);

		    if (listRentalOrder == null) {
			listRentalOrder = new ArrayList<>();
		    }

		    listRentalOrder.add(dto);
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

    public boolean deleteOrder(String orderId) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "UPDATE tblOrder "
			+ "SET status = 'inactive' "
			+ "WHERE orderID = ? ";

		pst = cn.prepareStatement(sql);
		pst.setString(1, orderId);

		int row = pst.executeUpdate();
		if (row > 0) {
		    return true;
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
	return false;
    }
}
