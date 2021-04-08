/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblUser;

import huyvd.util.DateHandle;
import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class TblUserDAO implements Serializable {

    public TblUserDTO checkLogin(String email, String password)
	    throws SQLException, NamingException {
	TblUserDTO dto = null;
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT email, password, fullname, phone, address, createDate, status, role "
			+ "FROM tblUser "
			+ "WHERE email=? AND password=? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, email);
		pst.setString(2, password);

		rs = pst.executeQuery();

		if (rs.next()) {
		    String curUserId = rs.getString("email");
		    String curPassword = rs.getString("password");
		    String fullname = rs.getString("fullname");
		    String phone = rs.getString("phone");
		    String address = rs.getString("address");
		    Date createDate = rs.getDate("createDate");
		    String status = rs.getString("status");
		    String role = rs.getString("role");
		    if (curUserId.equals(email) && curPassword.equals(password)) {
			dto = new TblUserDTO(email, password, fullname, phone, address, createDate, status, role);
		    }
		}//end if rs
	    }//end if connection
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
	return dto;
    }

    public boolean createAccount(String email, String password, String fullname,
	    String phone, String address) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "INSERT INTO tblUser(email, password, fullname, phone, address, createDate, status, role) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		pst = cn.prepareStatement(sql);

		//create default properties
		String role = "MEMBER";
		String createDate = DateHandle.createCurrentDate();
		String status = "new";

		//set param
		pst.setString(1, email);
		pst.setString(2, password);
		pst.setString(3, fullname);
		pst.setString(4, phone);
		pst.setString(5, address);
		pst.setString(6, createDate);
		pst.setString(7, status);
		pst.setString(8, role);

		//execute query
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

    public void activeAccount(String email) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "UPDATE tblUser\n"
			+ "SET status = 'active'\n"
			+ "WHERE email = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, email);
		
		pst.executeUpdate();
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
}
