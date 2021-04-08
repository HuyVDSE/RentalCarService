/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblDiscount;

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
public class TblDiscountDAO implements Serializable {

    public TblDiscountDTO getDiscount(String code) throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT discountCode, expiryDate, discount "
			+ "FROM tblDiscount "
			+ "WHERE discountCode = ? ";
		pst = cn.prepareStatement(sql);
		pst.setString(1, code);
		
		rs = pst.executeQuery();
		if (rs.next()) {
		    code = rs.getString("discountCode");
		    Date expiryDate = DateHandle.maintainDate(rs.getDate("expiryDate"));
		    int value = rs.getInt("discount");
		    
		    TblDiscountDTO dto = new TblDiscountDTO(code, expiryDate, value);
		    
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
