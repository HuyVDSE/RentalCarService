/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrderDetail;

import huyvd.cart.cartItem;
import huyvd.tblCar.TblCarDTO;
import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
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
public class TblOrderDetailDAO implements Serializable {

    public boolean createOrder(String orderId, List<cartItem> listItems) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "INSERT tblOrderDetail(orderID, carID, quantity, total) "
			+ "VALUES(?,?,?,?) ";
		pst = cn.prepareStatement(sql);

		cn.setAutoCommit(false);

		int totalRow = 0;
		for (cartItem item : listItems) {
		    pst.setString(1, orderId);
		    pst.setString(2, item.getCar().getCarID());
		    pst.setInt(3, item.getQuantity());
		    pst.setDouble(4, item.getTotal());
		    pst.addBatch();
		    totalRow++;
		}

		int[] result = pst.executeBatch();
		cn.commit();

		if (result.length == totalRow) {
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

    public List<cartItem> getOrderDetail(String orderId)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<cartItem> result = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT od.carID, carName, image, price, od.quantity "
			+ "FROM tblCar c join tblOrderDetail od on c.carID = od.carID "
			+ "WHERE orderID = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, orderId);

		rs = pst.executeQuery();
		while (rs.next()) {
		    TblCarDTO car = new TblCarDTO();
		    car.setCarID(rs.getString("carID"));
		    car.setName(rs.getString("carName"));
		    car.setImage(rs.getString("image"));
		    car.setPrice(rs.getDouble("price"));
		    int quantity = rs.getInt("quantity");
		    
		    cartItem item = new cartItem(car, quantity);
		    
		    if (result == null) {
			result = new ArrayList<>();
		    }

		    result.add(item);
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
}
