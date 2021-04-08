/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.feedback;

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
public class FeedbackDAO implements Serializable {

    private List<FeedbackDTO> listFeedback;

    public List<FeedbackDTO> getListFeedback() {
	return listFeedback;
    }

    public void loadFeedbackList(String carId) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT u.fullname, feedback, point "
			+ "FROM tblCar c, tblOrderDetail od, tblOrder o, tblUser u "
			+ "WHERE c.carID = od.carID and od.orderID = o.orderID and o.userID = u.email "
			+ "AND c.carID = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, carId);
		rs = pst.executeQuery();

		while (rs.next()) {
		    String username = rs.getString("fullname");
		    String content = rs.getString("feedback");
		    int point = rs.getInt("point");

		    FeedbackDTO dto = new FeedbackDTO(username, content, point);

		    if (this.listFeedback == null) {
			this.listFeedback = new ArrayList<>();
		    }

		    this.listFeedback.add(dto);
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

    public boolean updateFeedback(String orderId, String carId, String content, String point)
	    throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "UPDATE tblOrderDetail "
			+ "SET feedback = ?, point = ? "
			+ "FROM tblCar c join tblOrderDetail od on c.carID = od.carID join tblOrder o on od.orderID = o.orderID "
			+ "WHERE od.carID = ? and od.orderID = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, content);
		pst.setString(2, point);
		pst.setString(3, carId);
		pst.setString(4, orderId);
		
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
